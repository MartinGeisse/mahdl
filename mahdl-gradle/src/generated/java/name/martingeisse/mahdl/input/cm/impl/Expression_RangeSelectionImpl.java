package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.input.cm.Expression;
import name.martingeisse.mahdl.input.cm.Expression_RangeSelection;

public final class Expression_RangeSelectionImpl extends ExpressionImpl implements Expression_RangeSelection {

	private final Expression container;
	private final Expression from;
	private final Expression to;

	public Expression_RangeSelectionImpl(int row, int column, Object[] childNodes) {
		super(row, column);
		this.container = (Expression) childNodes[0];
		this.from = (Expression) childNodes[2];
		this.to = (Expression) childNodes[4];
	}

	public Expression getContainer() {
		return container;
	}

	public Expression getFrom() {
		return from;
	}

	public Expression getTo() {
		return to;
	}

}
