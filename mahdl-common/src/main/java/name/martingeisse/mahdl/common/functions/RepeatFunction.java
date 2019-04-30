/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.functions;

import com.google.common.collect.ImmutableList;
import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.common.processor.expression.ConstantValue;
import name.martingeisse.mahdl.common.processor.expression.ProcessedExpression;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import name.martingeisse.mahdl.input.cm.CmNode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;

/**
 *
 */
public final class RepeatFunction extends FixedSignatureFunction {

	public RepeatFunction() {
		super(ImmutableList.of(
			ProcessedDataType.Integer.INSTANCE,
			ProcessedDataType.Bit.INSTANCE
		));
	}

	@Override
	public @NotNull String getName() {
		return "repeat";
	}

	@NotNull
	@Override
	protected ProcessedDataType internalCheckType(@NotNull List<ProcessedExpression> arguments, ErrorHandler errorHandler) {
		ConstantValue value = arguments.get(0).evaluateFormallyConstant(new ProcessedExpression.FormallyConstantEvaluationContext(errorHandler));
		BigInteger integerValue = value.convertToInteger();
		if (integerValue == null) {
			return ProcessedDataType.Unknown.INSTANCE;
		}
		int size;
		try {
			size = integerValue.intValueExact();
		} catch (ArithmeticException e) {
			errorHandler.onError(arguments.get(0).getErrorSource(), "size too large");
			return ProcessedDataType.Unknown.INSTANCE;
		}
		return new ProcessedDataType.Vector(size);
	}

	@NotNull
	@Override
	public ConstantValue applyToConstantValues(@NotNull CmNode errorSource, @NotNull List<ConstantValue> arguments, @NotNull ProcessedExpression.FormallyConstantEvaluationContext context) {

		// determine size
		BigInteger integerValue = arguments.get(0).convertToInteger();
		if (integerValue == null) {
			return ConstantValue.Unknown.INSTANCE;
		}
		int size;
		try {
			size = integerValue.intValueExact();
		} catch (ArithmeticException e) {
			return ConstantValue.Unknown.INSTANCE;
		}

		// determine bit value
		Boolean bitValue = arguments.get(1).convertToBoolean();
		if (bitValue == null) {
			return ConstantValue.Unknown.INSTANCE;
		}
		return new ConstantValue.Vector(size, bitValue ? BigInteger.ONE.negate() : BigInteger.ZERO, true);
	}

}
