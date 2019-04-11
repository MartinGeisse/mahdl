package name.martingeisse.mahdl.input.cm.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import name.martingeisse.mahdl.intellij.input.PsiUtil;
import org.jetbrains.annotations.NotNull;
import com.intellij.psi.PsiReference;

import name.martingeisse.mahdl.input.cm.*;

public final class Expression_IdentifierImpl extends ExpressionImpl implements Expression_Identifier,PsiCm {

    public Expression_IdentifierImpl(@NotNull ASTNode node) {
        super(node);
    }

    	    public CmToken getIdentifier() {
            return (CmToken)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 0));
        }

    	public LeafPsiElement getIdentifierPsi() {
            return (LeafPsiElement)InternalPsiUtil.getChild(this, 0);
        }
    
        
            public PsiReference getReference() {
            return PsiUtil.getReference(this);
        }
    
    
    
}
