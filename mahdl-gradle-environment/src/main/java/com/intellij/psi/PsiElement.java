/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package com.intellij.psi;

import com.intellij.lang.ASTNode;

/**
 *
 */
public interface PsiElement {

	PsiElement[] EMPTY_ARRAY = new PsiElement[0];

	PsiElement[] getChildren();
	PsiElement getParent();
	PsiElement getFirstChild();
	PsiElement getLastChild();
	PsiElement getNextSibling();
	PsiElement getPrevSibling();
	String getText();
	PsiReference getReference();
	ASTNode getNode();

}
