/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.expression;

import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import name.martingeisse.mahdl.input.cm.Expression;
import name.martingeisse.mahdl.input.cm.ExtendedExpression;

/**
 *
 */
public interface ExpressionProcessor {

	ProcessedExpression process(ExtendedExpression expression);

	ProcessedExpression process(Expression expression);

	ProcessedExpression convertImplicitly(ProcessedExpression sourceExpression, ProcessedDataType targetType);

	default ProcessedExpression process(ExtendedExpression expression, ProcessedDataType targetType) {
		return convertImplicitly(process(expression), targetType);
	}

	default ProcessedExpression process(Expression expression, ProcessedDataType targetType) {
		return convertImplicitly(process(expression), targetType);
	}

	ErrorHandler getErrorHandler();

	// returns null on failure
	ConstantValue.Vector processCaseSelectorValue(Expression expression, ProcessedDataType selectorDataType);

}