/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.statement;

import name.martingeisse.mahdl.input.cm.CmNode;

/**
 * This statement indicates that a processing error has occurred that makes it impossible to treat it as any specific
 * statement anymore. No further errors should be generated for this statement.
 */
public final class UnknownStatement extends ProcessedStatement {

	public UnknownStatement(CmNode errorSource) {
		super(errorSource);
	}

	public <R> R visitBranches(BranchVisitor<R> visitor) {
		return visitor.getEmptyResult();
	}

}
