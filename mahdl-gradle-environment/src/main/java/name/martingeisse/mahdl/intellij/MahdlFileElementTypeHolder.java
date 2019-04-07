/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.intellij;

import com.intellij.psi.tree.IElementType;

/**
 *
 */
public final class MahdlFileElementTypeHolder {

	public static final IElementType FILE_ELEMENT_TYPE = new IElementType("MaHDL", null);

	// prevent instantiation
	private MahdlFileElementTypeHolder() {
	}

}
