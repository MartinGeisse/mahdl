package name.martingeisse.mahdl.gradle.codegen;

import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;

/**
 *
 */
final class Util {

	// prevent instantiation
	private Util() {
	}

	static String typeToString(ProcessedDataType type) {
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

}
