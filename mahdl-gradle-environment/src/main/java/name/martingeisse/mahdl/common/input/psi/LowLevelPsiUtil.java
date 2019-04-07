/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.input.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

import java.util.Comparator;

/**
 *
 */
public final class LowLevelPsiUtil {

	public static final Comparator<PsiElement> PSI_ELEMENT_START_POSITION_COMPARATOR = (x, y) -> {
		ASTNode a = x.getNode();
		ASTNode b = y.getNode();
		return (a.getRow() != b.getRow()) ? (a.getRow() - b.getRow()) : (a.getColumn() - b.getColumn());
	};

	// prevent instantiation
	private LowLevelPsiUtil() {
	}

}
