package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.input.cm.CmList;
import name.martingeisse.mahdl.input.cm.Expression;
import name.martingeisse.mahdl.input.cm.ExpressionCaseItem_Value;
import name.martingeisse.mahdl.input.cm.ExtendedExpression;

public final class ExpressionCaseItem_ValueImpl extends ExpressionCaseItemImpl implements ExpressionCaseItem_Value {

	private final CmList<Expression> selectorValues;
	private final ExtendedExpression resultValue;

	public ExpressionCaseItem_ValueImpl(int row, int column, Object[] childNodes) {
		super(row, column);
		this.selectorValues = (CmList<Expression>) childNodes[1];
		this.resultValue = (ExtendedExpression) childNodes[3];
	}

	public CmList<Expression> getSelectorValues() {
		return selectorValues;
	}

	public ExtendedExpression getResultValue() {
		return resultValue;
	}

}
