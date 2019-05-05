/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.expression;

import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import name.martingeisse.mahdl.input.cm.CmLinked;
import name.martingeisse.mahdl.input.cm.CmNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public abstract class ProcessedExpression implements CmLinked {

	@NotNull
	private final CmNode errorSource;

	@NotNull
	private final ProcessedDataType dataType;

	public ProcessedExpression(@NotNull CmNode errorSource, @NotNull ProcessedDataType dataType) {
		this.errorSource = errorSource;
		this.dataType = dataType;
	}

	@NotNull
	public final CmNode getErrorSource() {
		return errorSource;
	}

	@NotNull
	public final ProcessedDataType getDataType() {
		return dataType;
	}

	public final boolean isUnknownType() {
		return isType(ProcessedDataType.Family.UNKNOWN);
	}

	public final boolean isType(ProcessedDataType.Family family) {
		return dataType.getFamily() == family;
	}

	public final ProcessedExpression expectType(ProcessedDataType.Family family, ErrorHandler errorHandler) {
		if (isType(family) || isType(ProcessedDataType.Family.UNKNOWN)) {
			return this;
		}
		errorHandler.onError(errorSource, "expected type " + family + ", found " + dataType);
		return new UnknownExpression(errorSource);
	}

	public final boolean isType(ProcessedDataType type) {
		return dataType.equals(type);
	}

	public final ProcessedExpression expectType(ProcessedDataType type, ErrorHandler errorHandler) {
		if (isType(type) || isType(ProcessedDataType.Family.UNKNOWN)) {
			return this;
		}
		errorHandler.onError(errorSource, "expected type " + type + ", found " + dataType);
		return new UnknownExpression(errorSource);
	}

	/**
	 * If this expression can be implicitly bit-typed by modification, not conversion, then this method will do so
	 * and return the modified (explicitly bit-typed) expression that should be used instead.
	 * <p>
	 * The default implementation will just check if this expression is already bit-typed, and if so, returns this.
	 */
	@Nullable
	public ProcessedExpression makeBitCompatible() throws TypeErrorException {
		return (dataType.getFamily() == ProcessedDataType.Family.BIT ? this : null);
	}

	public final ConstantValue evaluateFormallyConstant(FormallyConstantEvaluationContext context) {
		ConstantValue value = evaluateFormallyConstantInternal(context);
		if (value == null) {
			return context.evaluationInconsistency(this, "evaluating this expression as formally constant returned null");
		} else if (value instanceof ConstantValue.Unknown) {
			return value;
		} else if (!value.getDataType().equals(dataType)) {
			return context.evaluationInconsistency(this, "evaluating this expression as formally constant returned a value of type " +
				value.getDataType() + ", but the expression type was " + dataType);
		} else {
			return value;
		}
	}

	@NotNull
	protected abstract ConstantValue evaluateFormallyConstantInternal(@NotNull FormallyConstantEvaluationContext context);

	/**
	 * Folding is similar to formally-constant evaluation. If a non-constant (sub-)expression is found, folding the
	 * whole expression fails but other sub-expressions may still be folded. On the other hand, constant evaluation
	 * cannot just try to fold and return the result because if a non-constant sub-expression is found, we want to
	 * report the location of that error as exact as possible.
	 */
	@NotNull
	protected ProcessedExpression performFolding(@NotNull ErrorHandler errorHandler) {
		ProcessedExpression.FormallyConstantEvaluationContext context = new ProcessedExpression.FormallyConstantEvaluationContext(errorHandler) {

			@Override
			@NotNull
			public ConstantValue.Unknown notConstant(@NotNull CmNode errorSource) {
				throw new NotConstantException();
			}

			@Override
			@NotNull
			public ConstantValue.Unknown notConstant(@NotNull ProcessedExpression errorSource) {
				throw new NotConstantException();

			}
		};
		ConstantValue value;
		try {
			value = evaluateFormallyConstant(context);
		} catch (NotConstantException e) {
			return performSubFolding(errorHandler);
		}
		return new ProcessedConstantExpression(errorSource, value);
	}

	@NotNull
	protected abstract ProcessedExpression performSubFolding(@NotNull ErrorHandler errorHandler);

	private static class NotConstantException extends RuntimeException {
	}

	public static class FormallyConstantEvaluationContext {

		private final ErrorHandler errorHandler;

		public FormallyConstantEvaluationContext(@NotNull ErrorHandler errorHandler) {
			this.errorHandler = errorHandler;
		}

		@NotNull
		public ErrorHandler getErrorHandler() {
			return errorHandler;
		}

		@NotNull
		public ConstantValue.Unknown error(@NotNull CmNode errorSource, @NotNull String message) {
			errorHandler.onError(errorSource, message);
			return ConstantValue.Unknown.INSTANCE;
		}

		@NotNull
		public ConstantValue.Unknown error(@NotNull ProcessedExpression errorSource, @NotNull String message) {
			return error(errorSource.getErrorSource(), message);
		}

		@NotNull
		public ConstantValue.Unknown notConstant(@NotNull CmNode errorSource) {
			return error(errorSource, "expected a formally constant expression");
		}

		@NotNull
		public ConstantValue.Unknown notConstant(@NotNull ProcessedExpression errorSource) {
			return notConstant(errorSource.getErrorSource());
		}

		@NotNull
		public ConstantValue.Unknown evaluationInconsistency(@NotNull CmNode errorSource, @NotNull String message) {
			return error(errorSource, "internal error: detected an inconsistency between static type check and constant evaluation" +
				(message == null ? "" : (": " + message)));
		}

		@NotNull
		public ConstantValue.Unknown evaluationInconsistency(@NotNull ProcessedExpression errorSource, @NotNull String message) {
			return evaluationInconsistency(errorSource.getErrorSource(), message);
		}

		@NotNull
		public ConstantValue.Unknown evaluationInconsistency(@NotNull CmNode errorSource) {
			return evaluationInconsistency(errorSource, null);
		}

		@NotNull
		public ConstantValue.Unknown evaluationInconsistency(@NotNull ProcessedExpression errorSource) {
			return evaluationInconsistency(errorSource.getErrorSource());
		}

	}

}
