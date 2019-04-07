package com.intellij.extapi.psi;

import com.intellij.lang.ASTNode;

/**
 *
 */
public class ASTWrapperPsiElement extends ASTDelegatePsiElement {

	private final ASTNode astNode;

	public ASTWrapperPsiElement(final ASTNode astNode) {
		this.astNode = astNode;
	}

	@Override
	public ASTNode getNode() {
		return astNode;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + getElementType().toString() + ")";
	}
}
