/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.definition;

import name.martingeisse.mahdl.common.cm.CmToken;
import name.martingeisse.mahdl.common.cm.DataType;
import name.martingeisse.mahdl.common.cm.ExtendedExpression;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public final class Signal extends SignalLike {

	public Signal(@NotNull CmToken nameElement,
				  @NotNull DataType dataTypeElement,
				  @NotNull ProcessedDataType processedDataType,
				  @Nullable ExtendedExpression initializer) {
		super(nameElement, dataTypeElement, processedDataType, initializer);
	}

}
