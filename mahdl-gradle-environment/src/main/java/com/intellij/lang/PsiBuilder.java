/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package com.intellij.lang;

import com.intellij.psi.tree.IElementType;

public interface PsiBuilder {

	void advanceLexer();

	IElementType getTokenType();

	Marker mark();

	void error(String messageText);

	boolean eof();

	ASTNode getTreeBuilt();

	interface Marker {
		void rollbackTo();

		void done(IElementType type);
	}

}
