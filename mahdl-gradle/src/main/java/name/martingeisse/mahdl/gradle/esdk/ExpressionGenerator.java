package name.martingeisse.mahdl.gradle.esdk;

import name.martingeisse.mahdl.common.processor.expression.*;
import org.apache.commons.lang3.StringUtils;

import static name.martingeisse.mahdl.gradle.esdk.Util.valueToString;

/**
 *
 */
public class ExpressionGenerator {

	private final GenerationModel model;
	private final StringBuilder builder;

	public ExpressionGenerator(GenerationModel model, StringBuilder builder) {
		this.model = model;
		this.builder = builder;
	}

	/**
	 * For a MaHDL right-hand-side expression, builds a Java expression to be used in an ESDK module constructor.
	 * The expression evaluates to an RtlBitSignal or RtlVector signal corresponding to the MaHDL expression.
	 *
	 * This method may use the string builder provided in the constructor to build helper expressions as Java
	 * constructor statements; therefore, this method must not be called while building a Java statement is in progress.
	 */
	public String buildExpression(ProcessedExpression expression) {

		// We rely on the MaHDL processor to have constant (sub-)expressions folded for us. So anything that cannot be
		// compiled to runtime constructs should have been disappeared by now. If not, errors should have been reported
		// already, and we only have to fail gracefully, not generate sensible code. We do that by returning a "null"
		// expression.

		if (expression instanceof ProcessedConstantValue) {

			return valueToString(((ProcessedConstantValue) expression).getValue());

		} else if (expression instanceof SignalLikeReference) {

			return ((SignalLikeReference) expression).getDefinition().getName();

		} else if (expression instanceof InstancePortReference) {

			InstancePortReference reference = (InstancePortReference)expression;
			return reference.getModuleInstance().getName() + ".get" + StringUtils.capitalize(reference.getPort().getName()) + "()";

		} else if (expression instanceof ProcessedUnaryOperation) {

			ProcessedUnaryOperation operation = (ProcessedUnaryOperation)expression;
			return TODO;

		} else if (expression instanceof ProcessedBinaryOperation) {

			ProcessedBinaryOperation operation = (ProcessedBinaryOperation)expression;
			return TODO;

		} else if (expression instanceof ProcessedConditional) {

			ProcessedConditional conditional = (ProcessedConditional)expression;
			return TODO;

		} else if (expression instanceof ProcessedIndexSelection) {

			ProcessedIndexSelection indexSelection = (ProcessedIndexSelection)expression;
			return TODO;

		} else if (expression instanceof ProcessedRangeSelection) {

			ProcessedRangeSelection rangeSelection = (ProcessedRangeSelection)expression;
			return TODO;

		} else if (expression instanceof ProcessedSwitchExpression) {

			ProcessedSwitchExpression switchExpression = (ProcessedSwitchExpression)expression;
			return TODO;

		} else if (expression instanceof ProcessedFunctionCall) {

			ProcessedFunctionCall functionCall = (ProcessedFunctionCall)expression;
			return TODO;

		} else if (expression instanceof TypeConversion) {

			TypeConversion typeConversion = (TypeConversion)expression;
			return TODO;

		} else {

		}
	}

}
