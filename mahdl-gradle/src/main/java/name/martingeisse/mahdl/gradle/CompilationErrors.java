/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.gradle;

import name.martingeisse.mahdl.input.cm.CmLinked;
import name.martingeisse.mahdl.input.cm.CmNode;
import name.martingeisse.mahdl.input.cm.impl.CmNodeImpl;
import name.martingeisse.mahdl.input.cm.impl.ModuleWrapper;

/**
 *
 */
public class CompilationErrors {

	private static final Object dummy = new Object();
	private static final ThreadLocal<Object> hasErrors = new ThreadLocal<>();

	public static boolean isHasErrors() {
		return hasErrors.get() != null;
	}

	public static void reportError(CmLinked source, String message) {
		if (source == null) {
			reportError("unknown file", 0, 0, message);
		} else {
			reportError(source.getErrorSource(), message);
		}
	}

	public static void reportError(CmNode node, String message) {
		reportError(node, message, null);
	}

	public static void reportError(CmNode node, String message, Throwable exception) {
		CmNodeImpl nodeImpl = (CmNodeImpl) node;
		ModuleWrapper moduleWrapper = ModuleWrapper.get(node);
		String path = (moduleWrapper != null && moduleWrapper.getFile() != null) ? moduleWrapper.getFile().getPath() : "???";
		CompilationErrors.reportError(path, nodeImpl.getRow(), nodeImpl.getColumn(), message, exception);
	}

	public static void reportError(String path, int row, int column, String message) {
		reportError(path, row, column, message, null);
	}

	public static void reportError(String path, int row, int column, String message, Throwable exception) {
		hasErrors.set(dummy);
		System.err.println(path + ':' + row + ": error: " + message);
		exception.printStackTrace(System.err);
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
