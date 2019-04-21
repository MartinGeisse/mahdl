package name.martingeisse.mahdl.gradle.codegen;

import name.martingeisse.mahdl.common.processor.expression.ConstantValue;
import name.martingeisse.mahdl.gradle.model.GenerationModel;

/**
 *
 */
public class ValueGenerator {

	private final GenerationModel model;
	private final StringBuilder builder;

	public ValueGenerator(GenerationModel model, StringBuilder builder) {
		this.model = model;
		this.builder = builder;
	}

	/**
	 * Like buildValue(), but handles multiple values and returns them as a comma-separated string.
	 */
	public String buildValues(Iterable<? extends ConstantValue> values) {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (ConstantValue value : values) {
			if (first) {
				first = false;
			} else {
				builder.append(", ");
			}
			builder.append(buildValue(value));
		}
		return builder.toString();
	}

	/**
	 * For a MaHDL constant value, builds a Java expression to be used in an ESDK module constructor.
	 * The expression evaluates to a value of the corresponding ESDK type.
	 * <p>
	 * This method may use the string builder provided in the constructor to build helper values as Java
	 * constructor statements; therefore, this method must not be called while building a Java statement is in progress.
	 */
	public String buildValue(ConstantValue value) {
		if (value instanceof ConstantValue.Bit) {
			return ((ConstantValue.Bit) value).isSet() ? "true" : "false";
		} else if (value instanceof ConstantValue.Vector) {
			ConstantValue.Vector vector = (ConstantValue.Vector) value;
			if (vector.getSize() > 64) {
				throw new UnsupportedOperationException("constant vector size >64 not yet supported");
			}
			return "VectorValue.ofUnsigned(" + vector.getSize() + ", " + vector.convertToInteger().longValue() + ")";
		} else if (value instanceof ConstantValue.Matrix) {
			StringBuilder builder = new StringBuilder();

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
