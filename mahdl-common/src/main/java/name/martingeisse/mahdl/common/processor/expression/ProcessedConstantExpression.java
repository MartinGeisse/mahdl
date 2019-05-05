/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.expression;

import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.input.cm.CmNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

/**
 *
 */
public final class ProcessedConstantExpression extends ProcessedExpression {

	private final ConstantValue value;

	public ProcessedConstantExpression(@NotNull CmNode errorSource, @NotNull ConstantValue value) {
		super(errorSource, value.getDataType());
		this.value = value;
	}

	@NotNull
	public ConstantValue getValue() {
		return value;
	}

	@NotNull
	@Override
	public ConstantValue evaluateFormallyConstantInternal(FormallyConstantEvaluationContext context) {
		return value;
	}

	@NotNull
	@Override
	protected ProcessedExpression performFolding(@NotNull ErrorHandler errorHandler) {
		return this;
	}

	@NotNull
	@Override
	protected ProcessedExpression performSubFolding(@NotNull ErrorHandler errorHandler) {
		return this;
	}

}
