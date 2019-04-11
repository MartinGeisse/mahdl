/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.statement;

import name.martingeisse.mahdl.common.cm.CmNode;

/**
 *
 */
public final class Nop extends ProcessedStatement {

	public Nop(CmNode errorSource) {
		super(errorSource);
	}

}
