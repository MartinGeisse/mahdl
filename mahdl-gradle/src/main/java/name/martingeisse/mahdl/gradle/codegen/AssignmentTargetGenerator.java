package name.martingeisse.mahdl.gradle.codegen;

import name.martingeisse.mahdl.common.processor.expression.*;
import name.martingeisse.mahdl.gradle.model.GenerationModel;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class AssignmentTargetGenerator {

	private final GenerationModel model;
	private final StringBuilder builder;
	private final ValueGenerator valueGenerator;
	private final ExpressionGenerator expressionGenerator;

	public AssignmentTargetGenerator(GenerationModel model, StringBuilder builder, ValueGenerator valueGenerator, ExpressionGenerator expressionGenerator) {
		this.model = model;
		this.builder = builder;
		this.valueGenerator = valueGenerator;
		this.expressionGenerator = expressionGenerator;
	}

	/**
	 * For a MaHDL left-hand-side expression, builds a Java expression to be used in an ESDK module constructor.
	 * The expression evaluates to an RtlBitAssignmentTarget or RtlVectorAssignmentTarget corresponding to the MaHDL
	 * expression.
	 * <p>
	 * This method may use the string builder provided in the constructor to build helper expressions as Java
	 * constructor statements; therefore, this method must not be called while building a Java statement is in progress.
	 * <p>
	 * Assignment targets are only used in clocked do-blocks, not in continuous do-blocks. This implies that an instance
	 * port cannot be an assignment target since instance ports can only be assigned to continuously.
	 */
	public String buildAssignmentTarget(ProcessedExpression expression) {
		if (expression instanceof SignalLikeReference) {

			return ((SignalLikeReference) expression).getDefinition().getName();

		} else if (expression instanceof ProcessedBinaryOperation) {

			throw new UnsupportedOperationException("assignment to concat target not yet implemented");

		} else if (expression instanceof ProcessedIndexSelection) {

			ProcessedIndexSelection indexSelection = (ProcessedIndexSelection) expression;
			if (!(indexSelection.getContainer() instanceof SignalLikeReference)) {
				throw new UnsupportedOperationException("unsupported assignment target for code generation");
			}
			String container = ((SignalLikeReference) indexSelection.getContainer()).getDefinition().getName();
			String index = expressionGenerator.buildExpression(indexSelection.getIndex());
			switch (indexSelection.getContainer().getDataType().getFamily()) {

				case VECTOR:
					return "new RtlVectorTargetIndexSelection(realm, " + container + ", " + index + ");\n";

				case MATRIX:
					return "new RtlMemoryTargetIndexSelection(realm, " + container + ", " + index + ");\n";

			}

		} else if (expression instanceof ProcessedRangeSelection) {

			ProcessedRangeSelection rangeSelection = (ProcessedRangeSelection) expression;
			if (!(rangeSelection.getContainer() instanceof SignalLikeReference)) {
				throw new UnsupportedOperationException("unsupported assignment target for code generation");
			}
			String container = ((SignalLikeReference) rangeSelection.getContainer()).getDefinition().getName();
			return "new RtlVectorTargetRangeSelection(realm, " + container + ", " + rangeSelection.getFromIndex() +
				", " + rangeSelection.getToIndex() + ");\n";

		}
		return "null";
	}

}
