package com.intellij.lang;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import name.martingeisse.mahdl.common.input.psi.Token;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ASTNode {

	private final ASTNode parent;
	private final int indexInParent;
	private final IElementType elementType;
	private final List<ASTNode> children = new ArrayList<>();
	private final int row;
	private final int column;
	private final String tokenText;
	private PsiElement psi;

	public ASTNode(Token token) {
		this(token.getElementType(), token.getRow(), token.getColumn(), token.getText());
	}

	public ASTNode(IElementType elementType, int row, int column, String tokenText) {
		this(null, -1, elementType, row, column, tokenText);
	}

	private ASTNode(ASTNode parent, int indexInParent, IElementType elementType, int row, int column, String tokenText) {
		this.parent = parent;
		this.indexInParent = indexInParent;
		this.elementType = elementType;
		this.row = row;
		this.column = column;
		this.tokenText = tokenText;
	}

	public ASTNode createChild(IElementType elementType, int row, int column, String tokenText) {
		ASTNode child = new ASTNode(this, children.size(), elementType, row, column, tokenText);
		children.add(child);
		return child;
	}

	public ASTNode createChild(Token token) {
		return createChild(token.getElementType(), token.getRow(), token.getColumn(), token.getText());
	}

	public ASTNode getParent() {
		return parent;
	}

	public PsiElement getParentPsi() {
		return (parent == null ? null : parent.getPsi());
	}

	public int getIndexInParent() {
		return indexInParent;
	}

	public IElementType getElementType() {
		return elementType;
	}

	public int getChildCount() {
		return children.size();
	}

	public ASTNode getChild(int index) {
		return children.get(index);
	}

	public PsiElement getChildPsi(int index) {
		return children.get(index).getPsi();
	}

	public ASTNode getNextSibling() {
		if (parent == null) {
			return null;
		}
		int nextIndex = indexInParent + 1;
		return (parent.getChildCount() == nextIndex) ? null : parent.getChild(nextIndex);
	}

	public PsiElement getNextSiblingPsi() {
		ASTNode siblingNode = getNextSibling();
		return (siblingNode == null ? null : siblingNode.getPsi());
	}

	public ASTNode getPreviousSibling() {
		if (parent == null) {
			return null;
		}
		return (indexInParent == 0 ? null : parent.getChild(indexInParent - 1));
	}

	public PsiElement getPreviousSiblingPsi() {
		ASTNode siblingNode = getPreviousSibling();
		return (siblingNode == null ? null : siblingNode.getPsi());
	}

	public ASTNode getFirstChild() {
		return (children.isEmpty() ? null : children.get(0));
	}

	public PsiElement getFirstChildPsi() {
		return (children.isEmpty() ? null : children.get(0).getPsi());
	}

	public ASTNode getLastChild() {
		return (children.isEmpty() ? null : children.get(children.size() - 1));
	}

	public PsiElement getLastChildPsi() {
		return (children.isEmpty() ? null : children.get(children.size() - 1).getPsi());
	}

	public PsiElement getPsi() {
		return psi;
	}

	public void setPsi(PsiElement psi) {
		this.psi = psi;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String getTokenText() {
		return tokenText;
	}

}
