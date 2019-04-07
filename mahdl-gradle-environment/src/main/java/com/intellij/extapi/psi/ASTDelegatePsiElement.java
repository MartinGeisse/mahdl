/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package com.intellij.extapi.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectCoreUtil;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiInvalidElementAccessException;
import com.intellij.psi.impl.CheckUtil;
import com.intellij.psi.impl.PsiElementBase;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.tree.ChangeUtil;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.psi.impl.source.tree.TreeElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public PsiElement getNextSibling() {
		return getNode().getNextSiblingPsi();
	}

	public PsiElement getPreviousSibling() {
		return getNode().getPreviousSiblingPsi();
	}

	@Override
	public PsiElement getFirstChild() {
		return (getNode().getChildCount() == 0 ? null : getNode().getChild(0).getPsi());
	}

	@Override
	public PsiElement getLastChild() {
		return (getNode().getChildCount() == 0 ? null : getNode().getChild(getNode().getChildCount() - 1).getPsi());
	}

	// --- TODO --------------------------------------------------------------------------------



	@Override
	public PsiElement[] getChildren() {
		PsiElement psiChild = getFirstChild();
		if (psiChild == null) return PsiElement.EMPTY_ARRAY;

		List<PsiElement> result = new ArrayList<>();
		while (psiChild != null) {
			if (psiChild.getNode() instanceof CompositeElement) {
				result.add(psiChild);
			}
			psiChild = psiChild.getNextSibling();
		}
		return PsiUtilCore.toPsiElementArray(result);
	}


	@Override
	public PsiElement getNextSibling() {
		return SharedImplUtil.getNextSibling(getNode());
	}

	@Override
	public PsiElement getPrevSibling() {
		return SharedImplUtil.getPrevSibling(getNode());
	}

	@Override
	public TextRange getTextRange() {
		return getNode().getTextRange();
	}

	@Override
	public int getStartOffsetInParent() {
		return getNode().getStartOffset() - getNode().getTreeParent().getStartOffset();
	}

	@Override
	public int getTextLength() {
		return getNode().getTextLength();
	}

	@Override
	public int getTextOffset() {
		return getNode().getStartOffset();
	}

	@Override
	public String getText() {
		return getNode().getText();
	}

	@Override
	public char[] textToCharArray() {
		return getNode().getText().toCharArray();
	}





}
