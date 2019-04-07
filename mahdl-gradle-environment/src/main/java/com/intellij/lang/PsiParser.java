/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package com.intellij.lang;

import com.intellij.psi.tree.IElementType;

public interface PsiParser {

	ASTNode parse(IElementType rootType, PsiBuilder builder);

}
