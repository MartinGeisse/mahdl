/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.gradle;

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

	public static void reportError(CmNode node, String message) {
		CmNodeImpl nodeImpl = (CmNodeImpl) node;
		ModuleWrapper moduleWrapper = ModuleWrapper.get(node);
		String path = (moduleWrapper != null && moduleWrapper.getFile() != null) ? moduleWrapper.getFile().getPath() : "???";
		CompilationErrors.reportError(path, nodeImpl.getRow(), nodeImpl.getColumn(), message);
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
