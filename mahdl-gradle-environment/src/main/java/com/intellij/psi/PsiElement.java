/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package com.intellij.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.ArrayFactory;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The common base interface for all elements of the PSI tree.
 * <p/>
 * Please see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/architectural_overview.html">IntelliJ Platform Architectural Overview</a>
 * for high-level overview.
 */
public interface PsiElement {

	PsiElement[] getChildren();
	PsiElement getParent();
	PsiElement getFirstChild();
	PsiElement getLastChild();
	PsiElement getNextSibling();
	PsiElement getPrevSibling();
	TextRange getTextRange();
	int getStartOffsetInParent();
	int getTextLength();
	PsiElement findElementAt(int offset);
	PsiReference findReferenceAt(int offset);
	int getTextOffset();
	String getText();
	char[] textToCharArray();
	PsiReference getReference();
	PsiReference[] getReferences();
	ASTNode getNode();

}
