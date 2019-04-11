package name.martingeisse.mahdl.input.cm.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import name.martingeisse.mahdl.input.cm.CmList;
import name.martingeisse.mahdl.input.cm.CmToken;
import name.martingeisse.mahdl.input.cm.Expression;
import name.martingeisse.mahdl.input.cm.Expression_FunctionCall;
import org.jetbrains.annotations.NotNull;

public final class Expression_FunctionCallImpl extends ExpressionImpl implements Expression_FunctionCall, PsiCm {

	public Expression_FunctionCallImpl(@NotNull ASTNode node) {
		super(node);
	}

	public CmToken getFunctionName() {
		return (CmToken) InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 0));
	}

	public LeafPsiElement getFunctionNamePsi() {
		return (LeafPsiElement) InternalPsiUtil.getChild(this, 0);
	}

	public CmList<Expression> getArguments() {
		return (CmList<Expression>) InternalPsiUtil.getCmFromPsi(InternalPsiUtil.getChild(this, 2));
	}

	public CmListImpl<Expression, ExpressionImpl> getArgumentsPsi() {
		return (CmListImpl<Expression, ExpressionImpl>) InternalPsiUtil.getChild(this, 2);
	}

}
