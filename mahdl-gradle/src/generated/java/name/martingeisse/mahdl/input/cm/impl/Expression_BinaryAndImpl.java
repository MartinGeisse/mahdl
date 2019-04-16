package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.input.cm.Expression;
import name.martingeisse.mahdl.input.cm.Expression_BinaryAnd;

public final class Expression_BinaryAndImpl extends ExpressionImpl implements Expression_BinaryAnd {

	private final Expression leftOperand;
	private final Expression rightOperand;

	public Expression_BinaryAndImpl(int row, int column, Object[] childNodes) {
		super(row, column);
		this.leftOperand = (Expression) childNodes[0];
		((CmNodeImpl) this.leftOperand).setParent(this);
		this.rightOperand = (Expression) childNodes[2];
		((CmNodeImpl) this.rightOperand).setParent(this);
	}

	public Expression getLeftOperand() {
		return leftOperand;
	}

	public Expression getRightOperand() {
		return rightOperand;
	}

}
