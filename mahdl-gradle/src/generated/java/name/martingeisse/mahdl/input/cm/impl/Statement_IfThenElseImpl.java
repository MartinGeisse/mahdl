package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.input.cm.Expression;
import name.martingeisse.mahdl.input.cm.Statement;
import name.martingeisse.mahdl.input.cm.Statement_IfThenElse;

public final class Statement_IfThenElseImpl extends StatementImpl implements Statement_IfThenElse {

	private final Expression condition;
	private final Statement thenBranch;
	private final Statement elseBranch;

	public Statement_IfThenElseImpl(int row, int column, Object[] childNodes) {
		super(row, column);
		this.condition = (Expression) childNodes[2];
		this.thenBranch = (Statement) childNodes[4];
		this.elseBranch = (Statement) childNodes[6];
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getThenBranch() {
		return thenBranch;
	}

	public Statement getElseBranch() {
		return elseBranch;
	}

}
