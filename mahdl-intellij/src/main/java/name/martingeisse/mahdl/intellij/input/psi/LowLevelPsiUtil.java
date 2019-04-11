/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.intellij.input.psi;

import com.intellij.psi.PsiElement;

import java.util.Comparator;

/**
 *
 */
public final class LowLevelPsiUtil {

	public static final Comparator<PsiElement> PSI_ELEMENT_START_POSITION_COMPARATOR =
		Comparator.comparing(element -> element.getTextRange().getStartOffset());

	// prevent instantiation
	private LowLevelPsiUtil() {
	}

}
