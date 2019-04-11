/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.functions;

import name.martingeisse.mahdl.input.cm.CmNode;
import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.common.processor.expression.ConstantValue;
import name.martingeisse.mahdl.common.processor.expression.ProcessedExpression;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 *
 */
public interface BuiltinFunction {

	@NotNull
	String getName();

	@NotNull
	ProcessedDataType checkType(@NotNull CmNode errorSource,
								@NotNull List<ProcessedExpression> arguments,
								@NotNull ErrorHandler errorHandler);

	@NotNull
	ConstantValue applyToConstantValues(@NotNull CmNode errorSource,
										@NotNull List<ConstantValue> arguments,
										@NotNull ProcessedExpression.FormallyConstantEvaluationContext context);

}
