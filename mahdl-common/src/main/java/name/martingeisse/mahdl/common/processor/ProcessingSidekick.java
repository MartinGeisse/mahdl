package name.martingeisse.mahdl.common.processor;

import name.martingeisse.mahdl.input.cm.CmLinked;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public final class ProcessingSidekick {

	private final ErrorHandler errorHandler;

	public ProcessingSidekick(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public void onError(@NotNull CmLinked errorSource, @NotNull String message) {
		errorHandler.onError(errorSource, message);
	}

	public void onError(@NotNull CmLinked errorSource, @NotNull String message, @Nullable Throwable t) {
		errorHandler.onError(errorSource, message, t);
	}

	public void onDiagnostic(@NotNull CmLinked errorSource, @NotNull String message) {
		errorHandler.onDiagnostic(errorSource, message);
	}

	public void onDiagnostic(@NotNull CmLinked errorSource, @NotNull String message, @Nullable Throwable t) {
		errorHandler.onDiagnostic(errorSource, message, t);
	}

}
