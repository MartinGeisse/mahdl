/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.intellij;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import name.martingeisse.mahdl.common.processor.ModuleProcessor;
import name.martingeisse.mahdl.input.cm.impl.InternalPsiUtil;
import name.martingeisse.mahdl.input.cm.impl.ModuleImpl;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class MahdlAnnotator implements Annotator {

	/**
	 * This method gets called on ALL PsiElements, post-order.
	 */
	@Override
	public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
		if (psiElement instanceof ModuleImpl) {
			annotate((ModuleImpl) psiElement, annotationHolder);
		}
	}

	private void annotate(@NotNull ModuleImpl module, @NotNull AnnotationHolder annotationHolder) {
		new ModuleProcessor(module, (errorSource, message) -> {
			annotationHolder.createErrorAnnotation(InternalPsiUtil.getPsiFromCm(errorSource), message);
		}).process();
	}

}
