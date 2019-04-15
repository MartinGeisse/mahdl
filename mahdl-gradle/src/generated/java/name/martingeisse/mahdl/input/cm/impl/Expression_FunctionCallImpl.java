package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.input.cm.CmList;
import name.martingeisse.mahdl.input.cm.CmToken;
import name.martingeisse.mahdl.input.cm.Expression;
import name.martingeisse.mahdl.input.cm.Expression_FunctionCall;

public final class Expression_FunctionCallImpl extends ExpressionImpl implements Expression_FunctionCall {

	private final CmToken functionName;
	private final CmList<Expression> arguments;

	public Expression_FunctionCallImpl(int row, int column, Object[] childNodes) {
		super(row, column);
		this.functionName = (CmToken) childNodes[0];
		this.arguments = (CmList<Expression>) childNodes[2];
	}

	public CmToken getFunctionName() {
		return functionName;
	}

	public CmList<Expression> getArguments() {
		return arguments;
	}

}
