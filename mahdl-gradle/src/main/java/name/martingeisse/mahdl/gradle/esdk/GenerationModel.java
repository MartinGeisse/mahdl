package name.martingeisse.mahdl.gradle.esdk;

import name.martingeisse.mahdl.common.processor.definition.*;
import name.martingeisse.mahdl.common.processor.expression.*;
import name.martingeisse.mahdl.common.processor.statement.*;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import name.martingeisse.mahdl.gradle.CompilationErrors;

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

	private final List<ModulePort> clocks;
	private final List<ModulePort> dataPorts;

	private final List<SignalLike> signalConnectors;

	private final List<DoBlockInfo<SignalLike>> continuousDoBlockInfos;
	private final List<DoBlockInfo<Register>> clockedDoBlockInfos;

	private int syntheticConstructCounter = 0;

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
				continuousDoBlockInfos.add(handleDoBlock(doBlock, SignalLike.class, "___continuousBlock"));
			} else {
				DoBlockInfo<Register> info = handleDoBlock(doBlock, Register.class, "___clockedBlock");
				clockedDoBlockInfos.add(info);
				remainingRegisters.removeAll(info.getAssignmentTargets());
			}
		}
		for (Register unassignedRegister : remainingRegisters) {
			CompilationErrors.reportError(unassignedRegister.getNameElement(), "registers that are never assigned to are not yet supported");
		}

	}

	private <T extends SignalLike> DoBlockInfo<T> handleDoBlock(ProcessedDoBlock doBlock, Class<T> signalLikeClass, String namePrefix) {
		SortedSet<T> targets = new TreeSet<>();
		collectAssignmentTargets(doBlock.getBody(), signalLikeClass, targets);
		DoBlockInfo<T> info = new DoBlockInfo<>(namePrefix + newSyntheticConstruct(), doBlock, targets);
		return info;
	}

	private <T extends SignalLike> void collectAssignmentTargets(ProcessedStatement statement, Class<T> targetClass, Set<T> targetCollector) {
		if (statement instanceof ProcessedAssignment) {
			collectAssignmentTargets(((ProcessedAssignment) statement).getLeftHandSide(), targetClass, targetCollector);
		} else if (statement instanceof ProcessedBlock) {
			for (ProcessedStatement childStatement : ((ProcessedBlock) statement).getStatements()) {
				collectAssignmentTargets(childStatement, targetClass, targetCollector);
			}
		} else if (statement instanceof ProcessedIf) {
			ProcessedIf processedIf = (ProcessedIf) statement;
			collectAssignmentTargets(processedIf.getThenBranch(), targetClass, targetCollector);
			collectAssignmentTargets(processedIf.getElseBranch(), targetClass, targetCollector);
		} else if (statement instanceof ProcessedSwitchStatement) {
			ProcessedSwitchStatement processedSwitch = (ProcessedSwitchStatement) statement;
			for (ProcessedSwitchStatement.Case aCase : processedSwitch.getCases()) {
				collectAssignmentTargets(aCase.getBranch(), targetClass, targetCollector);
			}
			collectAssignmentTargets(processedSwitch.getDefaultBranch(), targetClass, targetCollector);
		}
	}

	private <T extends SignalLike> void collectAssignmentTargets(ProcessedExpression destination, Class<T> targetClass, Set<T> targetCollector) {
		if (destination instanceof SignalLikeReference) {
			SignalLike signalLike = ((SignalLikeReference) destination).getDefinition();
			if (targetClass.isInstance(signalLike)) {
				targetCollector.add(targetClass.cast(signalLike));
			}
		} else if (destination instanceof ProcessedIndexSelection) {
			collectAssignmentTargets(((ProcessedIndexSelection) destination).getContainer(), targetClass, targetCollector);
		} else if (destination instanceof ProcessedRangeSelection) {
			collectAssignmentTargets(((ProcessedRangeSelection) destination).getContainer(), targetClass, targetCollector);
		} else if (destination instanceof ProcessedBinaryOperation) {
			ProcessedBinaryOperation binaryOperation = (ProcessedBinaryOperation) destination;
			if (binaryOperation.getOperator() == ProcessedBinaryOperator.VECTOR_CONCAT) {
				collectAssignmentTargets(binaryOperation.getLeftOperand(), targetClass, targetCollector);
				collectAssignmentTargets(binaryOperation.getRightOperand(), targetClass, targetCollector);
			}
		}
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

	public List<DoBlockInfo<SignalLike>> getContinuousDoBlockInfos() {
		return continuousDoBlockInfos;
	}

	public List<DoBlockInfo<Register>> getClockedDoBlockInfos() {
		return clockedDoBlockInfos;
	}

	public static final class DoBlockInfo<T extends SignalLike> {

		private final String name;
		private final ProcessedDoBlock doBlock;
		private final SortedSet<T> assignmentTargets;

		public DoBlockInfo(String name, ProcessedDoBlock doBlock, SortedSet<T> assignmentTargets) {
			this.name = name;
			this.doBlock = doBlock;
			this.assignmentTargets = assignmentTargets;
		}

		public String getName() {
			return name;
		}

		public ProcessedDoBlock getDoBlock() {
			return doBlock;
		}

		public SortedSet<T> getAssignmentTargets() {
			return assignmentTargets;
		}

	}

	public int newSyntheticConstruct() {
		syntheticConstructCounter++;
		return syntheticConstructCounter;
	}
}
