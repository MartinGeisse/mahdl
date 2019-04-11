package name.martingeisse.mahdl.input.cm.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.util.IncorrectOperationException;
import name.martingeisse.mahdl.input.cm.CmToken;
import name.martingeisse.mahdl.input.cm.SignalLikeDefinition_WithoutInitializer;
import org.jetbrains.annotations.NotNull;

public final class SignalLikeDefinition_WithoutInitializerImpl extends SignalLikeDefinitionImpl implements SignalLikeDefinition_WithoutInitializer, PsiCm {

	public SignalLikeDefinition_WithoutInitializerImpl(@NotNull ASTNode node) {
		super(node);
	}

	public CmToken getIdentifier() {
		return (CmToken) InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 0));
	}

	public LeafPsiElement getIdentifierPsi() {
		return (LeafPsiElement) InternalPsiUtil.getChild(this, 0);
	}

	public LeafPsiElement getNameIdentifier() {
		return name.martingeisse.mahdl.intellij.input.psi.PsiUtil.getNameIdentifier(this);
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
		name.martingeisse.mahdl.intellij.input.psi.PsiUtil.delete(this);
	}

}
