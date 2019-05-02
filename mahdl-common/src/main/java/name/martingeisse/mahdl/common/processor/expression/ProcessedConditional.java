/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.expression;

import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import name.martingeisse.mahdl.input.cm.CmNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public final class ProcessedConditional extends ProcessedExpression {

	private final ProcessedExpression condition;
	private final ProcessedExpression thenBranch;
	private final ProcessedExpression elseBranch;

	public ProcessedConditional(@NotNull CmNode errorSource,
								@NotNull ProcessedExpression condition,
								@NotNull ProcessedExpression thenBranch,
								@NotNull ProcessedExpression elseBranch) throws TypeErrorException {
		super(errorSource, thenBranch.getDataType());

		if (!(condition.getDataType() instanceof ProcessedDataType.Bit)) {
			throw new TypeErrorException();
		}
		if (!thenBranch.getDataType().equals(elseBranch.getDataType())) {
			throw new TypeErrorException();
		}

		this.condition = condition;
		this.thenBranch = thenBranch;
		this.elseBranch = elseBranch;
	}

	@NotNull
	public ProcessedExpression getCondition() {
		return condition;
	}

	@NotNull
	public ProcessedExpression getThenBranch() {
		return thenBranch;
	}

	@NotNull
	public ProcessedExpression getElseBranch() {
		return elseBranch;
	}

	@Override
	public @Nullable ProcessedExpression makeBitCompatible() throws TypeErrorException {
		ProcessedExpression superResult = super.makeBitCompatible();
		if (superResult != null) {
			return superResult;
		}
		ProcessedExpression bitCompatibleThenBranch = thenBranch.makeBitCompatible();
		ProcessedExpression bitCompatibleElseBranch = elseBranch.makeBitCompatible();
		if (bitCompatibleThenBranch == null || bitCompatibleElseBranch == null) {
			return null;
		}
		return new ProcessedConditional(getErrorSource(), condition, bitCompatibleThenBranch, bitCompatibleElseBranch);
	}

	@Override
	protected ConstantValue evaluateFormallyConstantInternal(FormallyConstantEvaluationContext context) {
		// evaluate both branches to detect errors even in the not-taken branch
		Boolean conditionBoolean = condition.evaluateFormallyConstant(context).convertToBoolean();
		ConstantValue thenValue = thenBranch.evaluateFormallyConstant(context);
		ConstantValue elseValue = elseBranch.evaluateFormallyConstant(context);
		if (conditionBoolean == null) {
			return context.evaluationInconsistency(this, "cannot convert condition to boolean");
		} else if (conditionBoolean) {
			return thenValue;
		} else {
			return elseValue;
		}
	}

	@NotNull
	@Override
	protected ProcessedExpression performSubFolding(@NotNull ErrorHandler errorHandler) {
		ProcessedExpression condition = this.condition.performFolding(errorHandler);
		ProcessedExpression thenBranch = this.thenBranch.performFolding(errorHandler);
		ProcessedExpression elseBranch = this.elseBranch.performFolding(errorHandler);
		if (condition != this.condition || thenBranch != this.thenBranch || elseBranch != this.elseBranch) {
			try {
				return new ProcessedConditional(getErrorSource(), condition, thenBranch, elseBranch);
			} catch (TypeErrorException e) {
				errorHandler.onError(getErrorSource(), "internal type error during folding of conditional expression");
				return this;
			}
		} else {
			return this;
		}
	}

}
