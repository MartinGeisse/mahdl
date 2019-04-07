package com.intellij.psi;

/**
 *
 */
public interface PsiReference {

	PsiElement resolve();

	String getCanonicalText();

}