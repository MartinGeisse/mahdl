package name.martingeisse.mahdl.input.cm.impl;

import com.intellij.lang.ASTNode;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.TokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.annotations.NotNull;
import com.intellij.util.IncorrectOperationException;
import com.intellij.psi.PsiReference;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import name.martingeisse.mahdl.input.cm.*;

public final class ExpressionCaseItem_ValueImpl extends ExpressionCaseItemImpl implements ExpressionCaseItem_Value,PsiCm {

    public ExpressionCaseItem_ValueImpl(@NotNull ASTNode node) {
        super(node);
    }

    	    public CmList<Expression> getSelectorValues() {
            return (CmList<Expression>)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 1));
        }

    	public CmListImpl<Expression, ExpressionImpl> getSelectorValuesPsi() {
            return (CmListImpl<Expression, ExpressionImpl>)InternalPsiUtil.getChild(this, 1);
        }
    	    public ExtendedExpression getResultValue() {
            return (ExtendedExpression)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 3));
        }

    	public ExtendedExpressionImpl getResultValuePsi() {
            return (ExtendedExpressionImpl)InternalPsiUtil.getChild(this, 3);
        }
    
        
    
    
    
}
