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

public final class ImplementationItem_DoBlockImpl extends ImplementationItemImpl implements ImplementationItem_DoBlock,PsiCm {

    public ImplementationItem_DoBlockImpl(@NotNull ASTNode node) {
        super(node);
    }

    	    public DoBlockTrigger getTrigger() {
            return (DoBlockTrigger)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 2));
        }

    	public DoBlockTriggerImpl getTriggerPsi() {
            return (DoBlockTriggerImpl)InternalPsiUtil.getChild(this, 2);
        }
    	    public Statement getStatement() {
            return (Statement)InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 4));
        }

    	public StatementImpl getStatementPsi() {
            return (StatementImpl)InternalPsiUtil.getChild(this, 4);
        }
    
        
    
    
    
}
