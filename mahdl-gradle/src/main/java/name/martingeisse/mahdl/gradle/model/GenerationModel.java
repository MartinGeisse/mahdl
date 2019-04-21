package name.martingeisse.mahdl.gradle.model;

import name.martingeisse.mahdl.common.processor.definition.*;
import name.martingeisse.mahdl.common.processor.expression.InstancePortReference;
import name.martingeisse.mahdl.common.processor.statement.ProcessedDoBlock;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import name.martingeisse.mahdl.gradle.CompilationErrors;
import name.martingeisse.mahdl.input.cm.CmUtil;

import java.util.*;

/**
 * Takes a processed {@link ModuleDefinition} and organizes the data in it in a way that is needed for generating
 * ESDK / Java code from it. No errors should show up here since they should have been caught during the processing
 * stage already. We may have to deal with inconsistent output from previous stages in case of errors, though, and
 * we do so by working around them silently and generating code as good as we can (assuming that the processing stage
 * reports those errors to the user).
 * <p>
 * The only exception to this rule, i.e. places where errors can still be generated here, is cases of valid MaHDL
 * that is not supported by the code generator, such as not-yet-implemented features.
 * <p>
 * <p>
 * TODO turn sorted maps into sorted sets -- we never use them as maps
 */
public final class GenerationModel {

	private final ModuleDefinition moduleDefinition;
	private final String packageName;
	private final String localName;
	private final Map<String, ModulePort> ports;
	private final Map<String, Constant> constants;
	private final Map<String, Signal> signals;
	private final Map<String, Register> registers;
	private final Map<String, ModuleInstance> moduleInstances;

	private int syntheticConstructCounter = 0;

	private final List<ModulePort> clocks;
	private final List<ModulePort> dataPorts;

	private final List<SignalLike> signalConnectors;

	private final List<ContinuousDoBlockInfo> continuousDoBlockInfos;
	private final List<ClockedDoBlockInfo> clockedDoBlockInfos;

	private final List<ModuleInstanceInfo> moduleInstanceInfos;

	public GenerationModel(ModuleDefinition moduleDefinition, String packageName, String localName) {
		this.moduleDefinition = moduleDefinition;
		this.packageName = packageName;
		this.localName = localName;

		// Group named definitions by type. Use a sorted map to make tracking down problems easier and more reproducible.
		// Also, sorting makes the order of clock constructor parameters stable.
		ports = new TreeMap<>();
		constants = new TreeMap<>();
		signals = new TreeMap<>();
		registers = new TreeMap<>();
		moduleInstances = new TreeMap<>();
		for (Named named : moduleDefinition.getDefinitions().values()) {
			if (named instanceof ModulePort) {
				ports.put(named.getName(), (ModulePort) named);
			} else if (named instanceof Constant) {
				constants.put(named.getName(), (Constant) named);
			} else if (named instanceof Signal) {
				signals.put(named.getName(), (Signal) named);
			} else if (named instanceof Register) {
				registers.put(named.getName(), (Register) named);
			} else if (named instanceof ModuleInstance) {
				moduleInstances.put(named.getName(), (ModuleInstance) named);
			}
		}

		// derived: separate clocks from other ports
		clocks = new ArrayList<>();
		dataPorts = new ArrayList<>();
		for (ModulePort port : ports.values()) {
			if (port.getProcessedDataType().getFamily() == ProcessedDataType.Family.CLOCK) {
				if (port.getDirection() == PortDirection.IN) {
					clocks.add(port);
				} else {
					CompilationErrors.reportError(port.getDataTypeElement(), "clock output ports are not yet supported");
				}
			} else {
				dataPorts.add(port);
			}
		}
		for (Named named : moduleDefinition.getDefinitions().values()) {
			if (named instanceof SignalLike) {
				SignalLike signalLike = (SignalLike) named;
				if (signalLike.getProcessedDataType().getFamily() == ProcessedDataType.Family.CLOCK) {
					if (!(signalLike instanceof ModulePort)) {
						CompilationErrors.reportError(signalLike.getDataTypeElement(), "clocks must be input ports");
					}
				}
			}
		}

		// derived: use signal connectors for data ports and local signals
		signalConnectors = new ArrayList<>();
		signalConnectors.addAll(dataPorts);
		signalConnectors.addAll(signals.values());
		signalConnectors.removeIf(signalLike -> {
			ProcessedDataType type = signalLike.getProcessedDataType();
			ProcessedDataType.Family family = type.getFamily();
			if (family != ProcessedDataType.Family.BIT && family != ProcessedDataType.Family.VECTOR) {
				CompilationErrors.reportError(signalLike.getDataTypeElement(), "data type not supported here");
				return true;
			}
			return false;
		});

		// derived: associate do-blocks with the registers they assign to
		continuousDoBlockInfos = new ArrayList<>();
		clockedDoBlockInfos = new ArrayList<>();
		SortedSet<Register> remainingRegisters = new TreeSet<>(registers.values());
		for (ProcessedDoBlock doBlock : moduleDefinition.getDoBlocks()) {
			if (doBlock.getClock() == null) {
				String name = "___continuousBlock" + newSyntheticConstruct();
				continuousDoBlockInfos.add(new ContinuousDoBlockInfo(name, doBlock));
			} else {
				String name = "___clockedBlock" + newSyntheticConstruct();
				ClockedDoBlockInfo info = new ClockedDoBlockInfo(name, doBlock);
				clockedDoBlockInfos.add(info);
				remainingRegisters.removeAll(info.getRegisters());
			}
		}
		for (Register unassignedRegister : remainingRegisters) {
			CompilationErrors.reportError(unassignedRegister.getNameElement(), "registers that are never assigned to are not yet supported");
		}

		// derived: module instances and which clocks to use for them
		moduleInstanceInfos = new ArrayList<>();
		for (ModuleInstance moduleInstance : moduleInstances.values()) {
			String canonicalModuleName = CmUtil.canonicalizeQualifiedModuleName(moduleInstance.getModuleElement().getModuleName());

			// collect clock ports, sorted by name
			SortedSet<String> clockPortNames = new TreeSet<>();
			for (InstancePort port : moduleInstance.getPorts().values()) {
				if (port.getDataType().getFamily() == ProcessedDataType.Family.CLOCK) {
					clockPortNames.add(port.getName());
				}
			}

			// collect assignments to them and remember the local source signal names (no complex expressions allowed for now)
			Map<String, String> clockPortToLocalClock = new HashMap<>();
			for (ContinuousDoBlockInfo info : continuousDoBlockInfos) {
				for (InstancePortReference instancePortReference : info.getInstancePortReferences()) {
					if (instancePortReference.getModuleInstance().getName().equals(moduleInstance.getName())) {
						String portName = instancePortReference.getPort().getName();
						if (clockPortNames.contains(portName)) {
							String localClock = info.findLocalClockSource(instancePortReference);
							clockPortToLocalClock.put(portName, localClock);
						}
					}
				}
			}

			// build a list of local clocks, sorted by name of the clock port
			List<String> localClockSignalsToPass = new ArrayList<>();
			for (String clockPort : clockPortNames) {
				localClockSignalsToPass.add(clockPortToLocalClock.get(clockPort));
			}

			moduleInstanceInfos.add(new ModuleInstanceInfo(moduleInstance, canonicalModuleName, localClockSignalsToPass));
		}

	}

	public int newSyntheticConstruct() {
		syntheticConstructCounter++;
		return syntheticConstructCounter;
	}

	public ModuleDefinition getModuleDefinition() {
		return moduleDefinition;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getLocalName() {
		return localName;
	}

	public Map<String, ModulePort> getPorts() {
		return ports;
	}

	public Map<String, Constant> getConstants() {
		return constants;
	}

	public Map<String, Signal> getSignals() {
		return signals;
	}

	public Map<String, Register> getRegisters() {
		return registers;
	}

	public Map<String, ModuleInstance> getModuleInstances() {
		return moduleInstances;
	}

	public List<ModulePort> getClocks() {
		return clocks;
	}

	public List<ModulePort> getDataPorts() {
		return dataPorts;
	}

	public List<SignalLike> getSignalConnectors() {
		return signalConnectors;
	}

	public List<ContinuousDoBlockInfo> getContinuousDoBlockInfos() {
		return continuousDoBlockInfos;
	}

	public List<ClockedDoBlockInfo> getClockedDoBlockInfos() {
		return clockedDoBlockInfos;
	}

	public List<ModuleInstanceInfo> getModuleInstanceInfos() {
		return moduleInstanceInfos;
	}

	// TODO move to toplevel

	/**
	 * Note: "local clocks to pass" are sorted by name of the clock port, NOT by name of the local clock signal
	 * (that's why it is a list and not a sorted set).
	 */
	public static final class ModuleInstanceInfo {

		private final ModuleInstance moduleInstance;
		private final String canonicalModuleName;
		private final List<String> localClocksToPass;

		public ModuleInstanceInfo(ModuleInstance moduleInstance, String canonicalModuleName, List<String> localClocksToPass) {
			this.moduleInstance = moduleInstance;
			this.canonicalModuleName = canonicalModuleName;
			this.localClocksToPass = localClocksToPass;
		}

		public ModuleInstance getModuleInstance() {
			return moduleInstance;
		}

		public String getCanonicalModuleName() {
			return canonicalModuleName;
		}

		public List<String> getLocalClocksToPass() {
			return localClocksToPass;
		}

	}

}
