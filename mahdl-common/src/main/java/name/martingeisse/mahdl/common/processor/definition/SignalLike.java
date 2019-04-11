/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.definition;

import name.martingeisse.mahdl.common.cm.CmToken;
import name.martingeisse.mahdl.common.cm.DataType;
import name.martingeisse.mahdl.common.cm.ExtendedExpression;
import name.martingeisse.mahdl.common.processor.expression.ExpressionProcessor;
import name.martingeisse.mahdl.common.processor.expression.ProcessedExpression;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public abstract class SignalLike extends Named {

	@NotNull
	private final DataType dataTypeElement;

	@NotNull
	private final ProcessedDataType processedDataType;

	@Nullable
	private final ExtendedExpression initializer;

	@Nullable
	private ProcessedExpression processedInitializer;

	public SignalLike(@NotNull CmToken nameElement,
					  @NotNull DataType dataTypeElement,
					  @NotNull ProcessedDataType processedDataType,
					  @Nullable ExtendedExpression initializer) {
		super(nameElement);
		this.dataTypeElement = dataTypeElement;
		this.processedDataType = processedDataType;
		this.initializer = initializer;
	}

	@NotNull
	public DataType getDataTypeElement() {
		return dataTypeElement;
	}

	@NotNull
	public ProcessedDataType getProcessedDataType() {
		return processedDataType;
	}

	@Nullable
	public ExtendedExpression getInitializer() {
		return initializer;
	}

	@Override
	public void processExpressions(@NotNull ExpressionProcessor expressionProcessor) {
		if (initializer != null) {
			processedInitializer = expressionProcessor.process(initializer, processedDataType);
		}
	}

	@Nullable
	public ProcessedExpression getProcessedInitializer() {
		return processedInitializer;
	}

}
