package name.martingeisse.mahdl.input.cm.impl;

import com.intellij.lang.ASTNode;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import name.martingeisse.mahdl.intellij.input.PsiUtil;
import org.jetbrains.annotations.NotNull;
import com.intellij.psi.PsiReference;

import name.martingeisse.mahdl.input.cm.*;

public final class QualifiedModuleNameImpl extends ASTWrapperPsiElement implements QualifiedModuleName,PsiCm {

    public QualifiedModuleNameImpl(@NotNull ASTNode node) {
        super(node);
    }

    	    public CmList<CmToken> getSegments() {
            return (CmList<CmToken>)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 0));
        }

    	public CmListImpl<CmToken, LeafPsiElement> getSegmentsPsi() {
            return (CmListImpl<CmToken, LeafPsiElement>)InternalPsiUtil.getChild(this, 0);
        }
    
        
            public PsiReference getReference() {
            return PsiUtil.getReference(this);
        }
    
    
    
}
