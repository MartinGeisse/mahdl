/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.gradle;

/**
 *
 */
public class CompilationErrors {

	private static final Object dummy = new Object();
	private static final ThreadLocal<Object> hasErrors = new ThreadLocal<>();

	public static boolean isHasErrors() {
		return hasErrors.get() != null;
	}

	public static void reportError(String path, int row, int column, String message) {
		hasErrors.set(dummy);
		System.err.println(path + ':' + row + ": error: " + message);
	}

	public static void failOnErrors() {
		if (isHasErrors()) {
			throw new CompilationFailedException("Compilation failed; see the compiler error output for details.");
		}
	}

	public static void clearErrors() {
		hasErrors.remove();
	}

}
