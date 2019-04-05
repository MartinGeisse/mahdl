/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.plugin.input;

import com.intellij.lexer.FlexAdapter;
import name.martingeisse.mahdl.plugin.input.FlexGeneratedMahdlLexer;

/**
 *
 */
public class MahdlLexer extends FlexAdapter {

	public MahdlLexer() {
		super(new FlexGeneratedMahdlLexer(null));
	}

}
