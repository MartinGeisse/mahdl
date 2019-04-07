package com.intellij.psi.impl.source.tree;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;

public class LeafPsiElement extends ASTWrapperPsiElement {

	public LeafPsiElement(ASTNode astNode) {
		super(astNode);
	}

}
