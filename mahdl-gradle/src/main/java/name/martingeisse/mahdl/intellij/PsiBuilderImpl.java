/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.intellij;

import com.google.common.collect.ImmutableList;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import name.martingeisse.mahdl.common.input.psi.Token;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PsiBuilderImpl implements PsiBuilder {

	private final ImmutableList<Token> tokens;
	private int tokenIndex = 0;
	private final List<Marker> markerStack = new ArrayList<>();

	public PsiBuilderImpl(ImmutableList<Token> tokens) {
		this.tokens = tokens;
	}

	@Override
	public void advanceLexer() {
		if (tokenIndex < tokens.size()) {
			tokenIndex++;
		}
	}

	@Override
	public IElementType getTokenType() {
		return (tokenIndex < tokens.size() ? tokens.get(tokenIndex).getElementType() : null;
	}

	@Override
	public Marker mark() {
		TODO
	}

	@Override
	public void error(String messageText) {
		TODO
	}

	@Override
	public boolean eof() {
		return (tokenIndex == tokens.size());
	}

	@Override
	public ASTNode getTreeBuilt() {
		TODO
	}

}
