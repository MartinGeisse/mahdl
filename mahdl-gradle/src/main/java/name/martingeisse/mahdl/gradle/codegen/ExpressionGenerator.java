package name.martingeisse.mahdl.gradle.codegen;

import name.martingeisse.mahdl.common.processor.expression.*;
import name.martingeisse.mahdl.gradle.model.GenerationModel;
import org.apache.commons.lang3.StringUtils;

import static name.martingeisse.mahdl.gradle.codegen.Util.valueToString;

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
	 * <p>
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

			InstancePortReference reference = (InstancePortReference) expression;
			return reference.getModuleInstance().getName() + ".get" + StringUtils.capitalize(reference.getPort().getName()) + "()";

		} else if (expression instanceof ProcessedUnaryOperation) {

			ProcessedUnaryOperation operation = (ProcessedUnaryOperation) expression;
			String operand = buildExpression(operation.getOperand());
			switch (operation.getOperator()) {

				case PLUS:
					return operand;

				case MINUS:
					return operand + ".negate()";

				case NOT:
					return operand + ".not()";

				default:
					return "null";

			}

		} else if (expression instanceof ProcessedBinaryOperation) {

			ProcessedBinaryOperation operation = (ProcessedBinaryOperation) expression;
			String left = buildExpression(operation.getLeftOperand());
			String right = buildExpression(operation.getRightOperand());
			switch (operation.getOperator()) {

				case AND:
					return left + ".and(" + right + ")";

				case OR:
					return left + ".or(" + right + ")";

				case XOR:
					return left + ".xor(" + right + ")";

				case PLUS:
					return left + ".add(" + right + ")";

				case MINUS:
					return left + ".subtract(" + right + ")";

				case TIMES:
					return left + ".multiply(" + right + ")";

				// these are only allowed in constant sub-expressions and so should have been folded
				case DIVIDED_BY:
				case REMAINDER:
					return "null";

				case VECTOR_CONCAT:
					return left + ".concat(" + right + ")";

				case SHIFT_LEFT:
					return "new RtlShiftOperation(realm, RtlShiftOperation.Direction.LEFT, " + left + ", " + right + ")";

				case SHIFT_RIGHT:
					return "new RtlShiftOperation(realm, RtlShiftOperation.Direction.RIGHT, " + left + ", " + right + ")";

				case EQUAL:
					return left + ".compareEqual(" + right + ")";

				case NOT_EQUAL:
					return left + ".compareNotEqual(" + right + ")";

				case LESS_THAN:
					return left + ".compareUnsignedLessThan(" + right + ")";

				case LESS_THAN_OR_EQUAL:
					return left + ".compareUnsignedLessThanOrEqual(" + right + ")";

				case GREATER_THAN:
					return left + ".compareUnsignedGreaterThan(" + right + ")";

				case GREATER_THAN_OR_EQUAL:
					return left + ".compareUnsignedGreaterThanOrEqual(" + right + ")";

				default:
					return "null";

			}

		} else if (expression instanceof ProcessedConditional) {

			ProcessedConditional conditional = (ProcessedConditional) expression;
			String condition = buildExpression(conditional.getCondition());
			String thenBranch = buildExpression(conditional.getThenBranch());
			String elseBranch = buildExpression(conditional.getElseBranch());
			return condition + ".conditional(" + thenBranch + ", " + elseBranch + ")";

		} else if (expression instanceof ProcessedIndexSelection) {

			ProcessedIndexSelection indexSelection = (ProcessedIndexSelection) expression;
			String container = buildExpression(indexSelection.getContainer());
			String index = buildExpression(indexSelection.getIndex());
			return container + ".select(" + index + ")";

		} else if (expression instanceof ProcessedRangeSelection) {

			ProcessedRangeSelection rangeSelection = (ProcessedRangeSelection) expression;
			String container = buildExpression(rangeSelection.getContainer());
			return container + ".select(" + rangeSelection.getFromIndex() + ", " + rangeSelection.getToIndex() + ")";

		} else if (expression instanceof ProcessedSwitchExpression) {

			ProcessedSwitchExpression switchExpression = (ProcessedSwitchExpression) expression;
			String helperName = "___switchExpression" + model.newSyntheticConstruct();
			builder.append("		").append("RtlSwitchSignal ").append(helperName)
				.append(" = new RtlSwitchSignal(realm, ").append(buildExpression(switchExpression.getSelector()))
				.append(");\n");
			for (ProcessedSwitchExpression.Case aCase : switchExpression.getCases()) {
				builder.append("			").append(helperName).append(".addCase(ImmutableList.of(")
					.append(Util.valuesToString(aCase.getSelectorValues())).append("), ")
					.append(buildExpression(aCase.getResultValue())).append(");\n");
			}
			builder.append("			").append(helperName).append(".setDefaultSignal(")
				.append(buildExpression(switchExpression.getDefaultBranch())).append(");\n");
			return helperName;

		} else if (expression instanceof ProcessedFunctionCall) {

			// there are currently no run-time functions
			return "null";

		} else if (expression instanceof TypeConversion.BitToVector) {

			TypeConversion.BitToVector typeConversion = (TypeConversion.BitToVector) expression;
			String operand = buildExpression(typeConversion.getOperand());
			return "new RtlConcatenation(realm, " + operand + ")";

		} else {

			return "null";

		}
	}

}
