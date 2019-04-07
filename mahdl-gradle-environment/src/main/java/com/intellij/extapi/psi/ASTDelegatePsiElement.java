/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package com.intellij.extapi.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;

public abstract class ASTDelegatePsiElement implements PsiElement {

	@Override
	public abstract ASTNode getNode();

	public PsiElement getParent() {
		return getNode().getParentPsi();
	}

	public IElementType getElementType() {
		return getNode().getElementType();
	}

	public int getChildCount() {
		return getNode().getChildCount();
	}

	public PsiElement getChild(int index) {
		return getNode().getChildPsi(index);
	}

	@Override
	public PsiElement getNextSibling() {
		return getNode().getNextSiblingPsi();
	}

	@Override
	public PsiElement getPrevSibling() {
		return getNode().getPreviousSiblingPsi();
	}

	@Override
	public PsiElement getFirstChild() {
		return getNode().getFirstChildPsi();
	}

	@Override
	public PsiElement getLastChild() {
		return getNode().getLastChildPsi();
	}

	@Override
	public PsiElement[] getChildren() {
		int childCount = getChildCount();
		if (childCount == 0) {
			return PsiElement.EMPTY_ARRAY;
		}
		PsiElement[] result = new PsiElement[childCount];
		for (int i = 0; i < result.length; i++) {
			result[i] = getNode().getChild(i).getPsi();
		}
		return result;
	}

	@Override
	public String getText() {
		String tokenText = getNode().getTokenText();
		if (tokenText == null) {
			throw new UnsupportedOperationException("getText() is only supported for leaf (token) PSI nodes");
		}
		return tokenText;
	}

	@Override
	public PsiReference getReference() {
		return null;
	}

}
