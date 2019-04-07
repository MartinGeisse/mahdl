/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.gradle;

/**
 *
 */
public class CompilationErrors {

	private static boolean hasErrors = false;

	public static boolean isHasErrors() {
		return hasErrors;
	}

	public static void reportError(String path, int row, String message) {
		hasErrors = true;
		System.err.println(path + ':' + row + ": " + message);
	}

	public static void failOnErrors() {
		if (hasErrors) {
			throw new CompilationFailedException("Compilation failed; see the compiler error output for details.");
		}
	}

}
