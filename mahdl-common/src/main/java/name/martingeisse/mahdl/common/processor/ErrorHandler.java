/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor;

import name.martingeisse.mahdl.input.cm.CmNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This interface is called when an error was detected. It might, for example, add an error annotation, throw an
 * exception, or ignore the error.
 */
public interface ErrorHandler {

	default void onError(@NotNull CmNode errorSource, @NotNull String message) {
		onError(errorSource, message, null);
	}

	void onError(@NotNull CmNode errorSource, @NotNull String message, @Nullable Throwable t);

}
