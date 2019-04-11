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

public final class ImplementationItem_SignalLikeDefinitionGroupImpl extends ImplementationItemImpl implements ImplementationItem_SignalLikeDefinitionGroup,PsiCm {

    public ImplementationItem_SignalLikeDefinitionGroupImpl(@NotNull ASTNode node) {
        super(node);
    }

    	    public SignalLikeKind getKind() {
            return (SignalLikeKind)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 0));
        }

    	public SignalLikeKindImpl getKindPsi() {
            return (SignalLikeKindImpl)InternalPsiUtil.getChild(this, 0);
        }
    	    public DataType getDataType() {
            return (DataType)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 1));
        }

    	public DataTypeImpl getDataTypePsi() {
            return (DataTypeImpl)InternalPsiUtil.getChild(this, 1);
        }
    	    public CmList<SignalLikeDefinition> getDefinitions() {
            return (CmList<SignalLikeDefinition>)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 2));
        }

    	public CmListImpl<SignalLikeDefinition, SignalLikeDefinitionImpl> getDefinitionsPsi() {
            return (CmListImpl<SignalLikeDefinition, SignalLikeDefinitionImpl>)InternalPsiUtil.getChild(this, 2);
        }
    
        
    
    
    
}
