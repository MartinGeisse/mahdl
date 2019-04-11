package name.martingeisse.mahdl.input.cm.impl;

import com.intellij.lang.ASTNode;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import name.martingeisse.mahdl.intellij.input.PsiUtil;
import org.jetbrains.annotations.NotNull;
import com.intellij.psi.PsiReference;

import name.martingeisse.mahdl.input.cm.*;

public final class InstanceReferenceNameImpl extends ASTWrapperPsiElement implements InstanceReferenceName,PsiCm {

    public InstanceReferenceNameImpl(@NotNull ASTNode node) {
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
