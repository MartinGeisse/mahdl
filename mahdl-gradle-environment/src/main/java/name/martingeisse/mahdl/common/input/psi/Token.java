/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.input.psi;

import com.intellij.psi.tree.IElementType;

/**
 *
 */
public final class Token {

	private final int row;
	private final int column;
	private final String text;
	private final IElementType elementType;

	public Token(int row, int column, String text, IElementType elementType) {
		this.row = row;
		this.column = column;
		this.text = text;
		this.elementType = elementType;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String getText() {
		return text;
	}

	public IElementType getElementType() {
		return elementType;
	}

}
