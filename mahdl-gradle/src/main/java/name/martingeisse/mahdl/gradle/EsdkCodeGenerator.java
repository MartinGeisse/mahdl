package name.martingeisse.mahdl.gradle;

import name.martingeisse.mahdl.common.processor.definition.*;
import name.martingeisse.mahdl.common.processor.expression.ConstantValue;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public final class EsdkCodeGenerator {

	private final EsdkGenerationModel model;
	private final StringBuilder builder;

	public EsdkCodeGenerator(EsdkGenerationModel model) {
		this.model = model;
		this.builder = new StringBuilder();
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

		// clocks
		for (ModulePort clock : model.getClocks()) {
			builder.append("\n");
			builder.append("private final RtlClockNetwork ").append(clock.getName()).append(";\n");
		}

		// generate signal connectors for data ports and local signals (not including registers)
		for (SignalLike signalLike : model.getSignalConnectors()) {
			builder.append("\n");
			if (signalLike.getProcessedDataType().getFamily() == ProcessedDataType.Family.BIT) {
				builder.append("	private final RtlBitSignalConnector ").append(signalLike.getName()).append(";\n");
			} else {
				builder.append("	private final RtlVectorSignalConnector ").append(signalLike.getName()).append(";\n");
			}
		}

		// generate register variables
		for (EsdkGenerationModel.DoBlockInfo doBlockInfo : model.getDoBlockInfos()) {
			for (Register register : doBlockInfo.getRegisters()) {
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

		// create signal connectors
		for (SignalLike signalLike : model.getSignalConnectors()) {
			builder.append("		this.").append(signalLike.getName()).append(" = new ");
			if (signalLike.getProcessedDataType().getFamily() == ProcessedDataType.Family.BIT) {
				builder.append("RtlBitSignalConnector(realm);\n");
			} else {
				int width = ((ProcessedDataType.Vector) signalLike.getProcessedDataType()).getSize();
				builder.append("RtlVectorSignalConnector(realm, ").append(width).append(");\n");
			}
		}

		// create do-blocks and registers
		for (EsdkGenerationModel.DoBlockInfo doBlockInfo : model.getDoBlockInfos()) {
			builder.append("		{\n");
			builder.append("			RtlClockedBlock ").append(doBlockInfo.getName()).append(" = new RtlClockedBlock(");
			builder.append(doBlockInfo.getDoBlock().getClock()); // TODO convert properly
			builder.append(");\n");
			for (Register register : doBlockInfo.getRegisters()) {
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

		// generate signal expressions
		// TODO

		// generate block statements
		// TODO

		// end of constructor
		builder.append("	}\n");
		builder.append("\n");

		// port accessors
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

	private String typeToString(ProcessedDataType type) {
		switch (type.getFamily()) {

			case BIT:
				return "boolean";

			case VECTOR:
				return "VectorValue";

			case MATRIX:
				return "Matrix";

			case INTEGER:
				return "int";

			case TEXT:
				return "String";

			case CLOCK:
				return "RtlClockNetwork";

			default:
				return "Object";

		}
	}

	private String valueToString(ConstantValue value) {
		if (value instanceof ConstantValue.Bit) {
			return ((ConstantValue.Bit) value).isSet() ? "true" : "false";
		} else if (value instanceof ConstantValue.Vector) {
			return "TODO";
		} else if (value instanceof ConstantValue.Matrix) {
			return "TODO";
		} else if (value instanceof ConstantValue.Integer) {
			return ((ConstantValue.Integer) value).getValue().toString();
		} else if (value instanceof ConstantValue.Text) {
			return "\"" + ((ConstantValue.Text) value).getValue().replace("\\", "\\\\").replace("\"", "\\\"");
		} else {
			return "null";
		}
	}

}
