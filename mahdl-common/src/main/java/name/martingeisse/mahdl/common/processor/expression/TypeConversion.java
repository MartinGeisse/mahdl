package name.martingeisse.mahdl.common.processor.expression;

import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

/**
 * Represents a conversion from one type to another. In processed expressions, these conversions are made explicit.
 * <p>
 * To-text conversion is not represented by this class because it is not needed: Text values can only occur in
 * formally constant (sub-)expressions, and constant evaluation can convert to text implicitly since all implementations
 * of {@link ConstantValue} implement {@link ConstantValue#convertToString()}.
 * <p>
 * Special case: Using 0 or 1 as a bit literal is NOT handled by treating it as an integer literal with a conversion.
 * Instead, these are recognized by the {@link ExpressionProcessor} and turned into a bit literal. Actual bit
 * literals therefore only exist in processed expressions, not on syntax level. The reason is that we generally do
 * NOT want an implicit conversion from integer to bit -- these conversions only make sense for 0 or 1, and we still
 * don't want to allow them for any integer expression that turns out to be 0 or 1. We only want to allow using 0
 * or 1 directly as literals. To make this rule useful, such bit literals are not allowed in any ambiguous or
 * non-obvious case.
 */
public abstract class TypeConversion extends ProcessedExpression {

	private final ProcessedExpression operand;

	private TypeConversion(@NotNull ProcessedDataType dataType, @NotNull ProcessedExpression operand) {
		super(operand.getErrorSource(), dataType);
		this.operand = operand;
	}

	@NotNull
	public ProcessedExpression getOperand() {
		return operand;
	}

	@NotNull
	protected abstract ConstantValue perform(@NotNull FormallyConstantEvaluationContext context, @NotNull ConstantValue operandValue);

	@Override
	@NotNull
	protected ConstantValue evaluateFormallyConstantInternal(@NotNull FormallyConstantEvaluationContext context) {
		return perform(context, operand.evaluateFormallyConstant(context));
	}

	@NotNull
	@Override
	protected ProcessedExpression performSubFolding(@NotNull ErrorHandler errorHandler) {
		ProcessedExpression operand = this.operand.performFolding(errorHandler);
		if (operand == this.operand) {
			return this;
		}
		try {
			return createEquivalentConversion(operand);
		} catch (TypeErrorException e) {
			errorHandler.onError(getErrorSource(), "internal type error during folding of type conversion");
			return this;
		}
	}

	// creates a type conversion of the same class as this, using the specified operand
	protected abstract TypeConversion createEquivalentConversion(ProcessedExpression operand) throws TypeErrorException;

	public static final class BitToVector extends TypeConversion {

		public BitToVector(@NotNull ProcessedExpression operand) throws TypeErrorException {
			super(new ProcessedDataType.Vector(1), operand);
			if (!(operand.getDataType() instanceof ProcessedDataType.Bit)) {
				throw new TypeErrorException();
			}
		}

		@NotNull
		public ProcessedDataType.Vector getVectorDataType() {
			return (ProcessedDataType.Vector) super.getDataType();
		}

		@Override
		@NotNull
		protected ConstantValue perform(@NotNull FormallyConstantEvaluationContext context, @NotNull ConstantValue operandValue) {
			if (operandValue instanceof ConstantValue.Bit) {
				BitSet bits = new BitSet();
				bits.set(0, ((ConstantValue.Bit) operandValue).isSet());
				return new ConstantValue.Vector(1, bits);
			} else {
				return context.evaluationInconsistency(this, "got wrong operand value: " + operandValue);
			}
		}

		@Override
		protected TypeConversion createEquivalentConversion(ProcessedExpression operand) throws TypeErrorException {
			return new BitToVector(operand);
		}

	}

}
