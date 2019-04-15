package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.input.cm.Expression;
import name.martingeisse.mahdl.input.cm.Expression_BinaryShiftRight;

public final class Expression_BinaryShiftRightImpl extends ExpressionImpl implements Expression_BinaryShiftRight {

	private final Expression leftOperand;
	private final Expression rightOperand;

	public Expression_BinaryShiftRightImpl(int row, int column, Object[] childNodes) {
		super(row, column);
		this.leftOperand = (Expression) childNodes[0];
		this.rightOperand = (Expression) childNodes[2];
	}

	public Expression getLeftOperand() {
		return leftOperand;
	}

	public Expression getRightOperand() {
		return rightOperand;
	}

}
