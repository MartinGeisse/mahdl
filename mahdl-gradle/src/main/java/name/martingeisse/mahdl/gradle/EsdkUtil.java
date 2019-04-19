package name.martingeisse.mahdl.gradle;

import name.martingeisse.mahdl.common.processor.expression.ConstantValue;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;

/**
 *
 */
public final class EsdkUtil {

	// prevent instantiation
	private EsdkUtil() {
	}

	public static String typeToString(ProcessedDataType type) {
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

	public static String valueToString(ConstantValue value) {
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
