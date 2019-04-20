package name.martingeisse.mahdl.gradle.esdk;

import name.martingeisse.mahdl.common.processor.definition.*;
import name.martingeisse.mahdl.common.processor.expression.ProcessedExpression;
import name.martingeisse.mahdl.common.processor.expression.SignalLikeReference;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import name.martingeisse.mahdl.gradle.CompilationErrors;
import org.apache.commons.lang3.StringUtils;

import static name.martingeisse.mahdl.gradle.esdk.Util.typeToString;
import static name.martingeisse.mahdl.gradle.esdk.Util.valueToString;

/**
 * TODO: MODULE INSTANCES
 *
 * For each module instance, a field is created (fields part) and the module instance created (definition part). The
 * constructor only needs the RtlRealm (implicit first constructor parameter of all generated modules) and clocks.
 * For the latter, we scan the ports of the module instance for all clock ports (Which must be input ports), sort them
 * by name, then pass them. It is currently not possible to use anything beyond a simple reference to a clock input port
 * of the enclosing module here; if it were, we would have to refine the ordering (esp.: currently the order with
 * signal definitions is undefined, so other signals cannot be used; using data ouputs of other module instances is
 * especially tricks. Probably the only way to handle this would be to make the clock a settable field in ESDK like
 * data fields).
 *
 * Note that after creating the module instances, their output ports are ready to use (since they are pre-created
 * signal connectors, too).
 *
 * -------
 *
 *
 */
public final class CodeGenerator {

	private final GenerationModel model;
	private final StringBuilder builder;
	private final ExpressionGenerator expressionGenerator;
	private final ContinuousStatementExpressionGenerator continuousStatementExpressionGenerator;
	private final ClockedStatementGenerator clockedStatementGenerator;

	public CodeGenerator(GenerationModel model) {
		this.model = model;
		this.builder = new StringBuilder();
		this.expressionGenerator = new ExpressionGenerator(model, builder);
		this.continuousStatementExpressionGenerator = new ContinuousStatementExpressionGenerator(model, builder);
		this.clockedStatementGenerator = new ClockedStatementGenerator(model, builder, expressionGenerator);
	}

	public void run() {

		// package declaration
		if (!model.getPackageName().isEmpty()) {
			builder.append("package ").append(model.getPackageName()).append(";\n");
			builder.append("\n");
		}

		// imports
		builder.append("import name.martingeisse.esdk.core.rtl.*;\n");
		builder.append("import name.martingeisse.esdk.core.rtl.synthesis.verilog.*;\n");
		builder.append("\n");

		// class intro
		builder.append("public class ").append(model.getLocalName()).append(" extends RtlItem {\n");

		// constants
		for (Constant constant : model.getConstants().values()) {
			builder.append("\n");
			builder.append("	public static final ").append(typeToString(constant.getProcessedDataType()))
				.append(" = ").append(valueToString(constant.getValue())).append(";\n");
		}

		// fields part: clocks
		for (ModulePort clock : model.getClocks()) {
			builder.append("\n");
			builder.append("private final RtlClockNetwork ").append(clock.getName()).append(";\n");
		}

		// fields part: signal connectors for data ports and local signals (not including registers)
		for (SignalLike signalLike : model.getSignalConnectors()) {
			builder.append("\n");
			if (signalLike.getProcessedDataType().getFamily() == ProcessedDataType.Family.BIT) {
				builder.append("	private final RtlBitSignalConnector ").append(signalLike.getName()).append(";\n");
			} else {
				builder.append("	private final RtlVectorSignalConnector ").append(signalLike.getName()).append(";\n");
			}
		}

		// fields part: register variables
		for (GenerationModel.DoBlockInfo<Register> doBlockInfo : model.getClockedDoBlockInfos()) {
			for (Register register : doBlockInfo.getAssignmentTargets()) {
				builder.append("\n");
				if (register.getProcessedDataType().getFamily() == ProcessedDataType.Family.BIT) {
					builder.append("	private final RtlProceduralBitSignal ").append(register.getName()).append(";\n");
				} else {
					builder.append("	private final RtlProceduralVectorSignal ").append(register.getName()).append(";\n");
				}
			}
		}

		// constructor intro
		builder.append("\n");
		builder.append("	public ").append(model.getLocalName()).append("(RtlRealm realm");
		for (ModulePort port : model.getClocks()) {
			builder.append(", RtlClockNetwork ").append(port.getName());
		}
		builder.append(") {\n");
		builder.append("		super(realm);\n");

		// assign clock networks (final variables)
		for (ModulePort port : model.getClocks()) {
			builder.append("		this.").append(port.getName()).append(" = ").append(port.getName()).append(";\n");
		}

		// definition part: create signal connectors
		for (SignalLike signalLike : model.getSignalConnectors()) {
			builder.append("		this.").append(signalLike.getName()).append(" = new ");
			if (signalLike.getProcessedDataType().getFamily() == ProcessedDataType.Family.BIT) {
				builder.append("RtlBitSignalConnector(realm);\n");
			} else {
				int width = ((ProcessedDataType.Vector) signalLike.getProcessedDataType()).getSize();
				builder.append("RtlVectorSignalConnector(realm, ").append(width).append(");\n");
			}
		}

		// definition part: create clocked do-blocks and registers
		for (GenerationModel.DoBlockInfo<Register> doBlockInfo : model.getClockedDoBlockInfos()) {
			ProcessedExpression clock = doBlockInfo.getDoBlock().getClock();
			if (!(clock instanceof SignalLikeReference)) {
				CompilationErrors.reportError(clock.getErrorSource(), "this compiler currently only supports clocks that are input ports");
				continue;
			}
			String clockName = ((SignalLikeReference) clock).getDefinition().getName();

			builder.append("		{\n");
			builder.append("			RtlClockedBlock ").append(doBlockInfo.getName()).append(" = new RtlClockedBlock(");
			builder.append(clockName);
			builder.append(");\n");
			for (Register register : doBlockInfo.getAssignmentTargets()) {
				builder.append("			").append(register.getName());
				if (register.getProcessedDataType().getFamily() == ProcessedDataType.Family.BIT) {
					builder.append(" = ").append(doBlockInfo.getName()).append(".createBit(");
				} else {
					ProcessedDataType.Vector vectorType = (ProcessedDataType.Vector) register.getProcessedDataType();
					builder.append(" = ").append(doBlockInfo.getName()).append(".createVector(").append(vectorType.getSize());
					if (register.getInitializerValue() != null) {
						builder.append(", ");
					}
				}
				if (register.getInitializerValue() == null) {
					builder.append(");\n");
				} else {
					builder.append(valueToString(register.getInitializerValue()));
					builder.append(");\n");
				}
			}
			builder.append("		}\n");
		}

		// implementation part: generate signal connector inputs from signal initializers
		for (Signal signal : model.getSignals().values()) {
			ProcessedExpression initializer = signal.getProcessedInitializer();
			if (initializer != null) {
				String expression = expressionGenerator.buildExpression(initializer);
				builder.append("		").append(signal.getName()).append(".setConnected(").append(expression).append(");\n");
			}
		}

		// implementation part: generate signal connector inputs from continuous do-blocks
		for (GenerationModel.DoBlockInfo<SignalLike> doBlockInfo : model.getContinuousDoBlockInfos()) {
			for (SignalLike target : doBlockInfo.getAssignmentTargets()) {
				ProcessedExpression equivalentExpression = continuousStatementExpressionGenerator.buildEquivalentExpression(doBlockInfo, target);
				String expressionText = expressionGenerator.buildExpression(equivalentExpression);
				// TODO target.getName() won't work for module instance ports. Where else was this error made?
				builder.append("		").append(target.getName()).append(".setConnected(").append(expressionText).append(");\n");
			}
		}

		// implementation part: generate clocked do-block statements
		for (GenerationModel.DoBlockInfo<Register> doBlockInfo : model.getClockedDoBlockInfos()) {
			clockedStatementGenerator.generateStatements(doBlockInfo.getName() + ".getStatements()", doBlockInfo.getDoBlock().getBody());
		}

		// end of constructor
		builder.append("	}\n");
		builder.append("\n");

		// accessors part: generate port accessors
		for (ModulePort port : model.getDataPorts()) {
			builder.append("\n");
			if (port.getDirection() == PortDirection.IN) {
				builder.append("	public void set").append(StringUtils.capitalize(port.getName())).append("(")
					.append(typeToString(port.getProcessedDataType())).append(' ').append(port.getName())
					.append(") {\n");
				builder.append("		this.").append(port.getName()).append(".setConnected(").append(port.getName()).append(");\n");
				builder.append("	}\n");
			} else {
				builder.append("	public ").append(port.getProcessedDataType()).append(" get")
					.append(StringUtils.capitalize(port.getName())).append("() {\n");
				builder.append("		return ").append(port.getName()).append(".getConnected();\n");
				builder.append("	}\n");
			}
			builder.append("\n");
		}

		// custom verilog contribution (not needed since the RTL classes used contribute everything needed)
		builder.append("	@Override\n");
		builder.append("	public VerilogContribution getVerilogContribution() {\n");
		builder.append("		return new EmptyVerilogContribution();\n");
		builder.append("	}\n");
		builder.append("\n");
		builder.append("}\n");

	}

	public String getCode() {
		return builder.toString();
	}

}
