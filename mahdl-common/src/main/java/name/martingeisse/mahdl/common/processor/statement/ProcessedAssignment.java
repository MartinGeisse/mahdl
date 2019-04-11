/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.statement;

import name.martingeisse.mahdl.common.cm.CmNode;
import name.martingeisse.mahdl.common.processor.expression.ProcessedExpression;
import name.martingeisse.mahdl.common.processor.expression.TypeErrorException;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;

/**
 *
 */
public final class ProcessedAssignment extends ProcessedStatement {

	private final ProcessedExpression leftHandSide;
	private final ProcessedExpression rightHandSide;

	public ProcessedAssignment(CmNode errorSource, ProcessedExpression leftHandSide, ProcessedExpression rightHandSide) throws TypeErrorException {
		super(errorSource);
		if (!(leftHandSide.getDataType() instanceof ProcessedDataType.Unknown)) {
			if (!(rightHandSide.getDataType() instanceof ProcessedDataType.Unknown)) {
				if (!leftHandSide.getDataType().equals(rightHandSide.getDataType())) {
					throw new TypeErrorException();
				}
			}
		}
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	public ProcessedExpression getLeftHandSide() {
		return leftHandSide;
	}

	public ProcessedExpression getRightHandSide() {
		return rightHandSide;
	}

}
