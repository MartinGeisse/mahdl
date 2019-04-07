package com.intellij.extapi.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author max
 */
public class ASTWrapperPsiElement extends ASTDelegatePsiElement {

	private final ASTNode astNode;

	public ASTWrapperPsiElement(final ASTNode astNode) {
		this.astNode = astNode;
	}

	@Override
	public PsiElement getParent() {
		return SharedImplUtil.getParent(getNode());
	}

	@Override
	public ASTNode getNode() {
		return astNode;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + myNode.getElementType().toString() + ")";
	}
}
