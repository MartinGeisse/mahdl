package name.martingeisse.mahdl.gradle.codegen;

import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;

/**
 *
 */
final class Util {

	// prevent instantiation
	private Util() {
	}

	static String valueTypeToString(ProcessedDataType type) {
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

	static String signalTypeToString(ProcessedDataType type) {
		switch (type.getFamily()) {

			case BIT:
				return "RtlBitSignal";

			case VECTOR:
				return "RtlVectorSignal";

			case CLOCK:
				return "RtlClockNetwork";

			default:
				return "Object";

		}
	}

	static String buildJavaStringLiteral(String value) {
		return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
	}

}
