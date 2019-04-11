package name.martingeisse.mahdl.input.cm.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import name.martingeisse.mahdl.intellij.input.PsiUtil;
import org.jetbrains.annotations.NotNull;
import com.intellij.util.IncorrectOperationException;

import name.martingeisse.mahdl.input.cm.*;

public final class SignalLikeDefinition_ErrorImpl extends SignalLikeDefinitionImpl implements SignalLikeDefinition_Error,PsiCm {

    public SignalLikeDefinition_ErrorImpl(@NotNull ASTNode node) {
        super(node);
    }

    	    public CmToken getIdentifier() {
            return (CmToken)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 0));
        }

    	public LeafPsiElement getIdentifierPsi() {
            return (LeafPsiElement)InternalPsiUtil.getChild(this, 0);
        }
    
        
        
            public LeafPsiElement getNameIdentifier() {
                return PsiUtil.getNameIdentifier(this);
            }

            public String getName() {
                LeafPsiElement nameIdentifier = getNameIdentifier();
                return (nameIdentifier == null ? null : nameIdentifier.getText());
            }

            public PsiElement setName(String newName) throws IncorrectOperationException {
                LeafPsiElement nameIdentifier = getNameIdentifier();
                if (nameIdentifier == null) {
                    throw new IncorrectOperationException("name identifier not found");
                }
                return (LeafPsiElement) nameIdentifier.replaceWithText(newName);
            }

        
    
    
    
            public void delete() throws IncorrectOperationException {
            PsiUtil.delete(this);
        }
    
}
