/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.expression;

import name.martingeisse.mahdl.common.processor.ProcessingSidekick;
import name.martingeisse.mahdl.common.processor.type.ProcessedDataType;
import name.martingeisse.mahdl.input.cm.CmNode;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 *
 */
public abstract class ProcessedIndexSelection extends ProcessedExpression {

	private static final BigInteger MAX_INDEX_VALUE = BigInteger.valueOf(Integer.MAX_VALUE);

	private final ProcessedExpression container;
	private final ProcessedExpression index;

	private ProcessedIndexSelection(@NotNull CmNode errorSource,
									@NotNull ProcessedDataType dataType,
									@NotNull ProcessedExpression container,
									@NotNull ProcessedExpression index) {
		super(errorSource, dataType);
		this.container = container;
		this.index = index;
	}

	@NotNull
	public ProcessedExpression getContainer() {
		return container;
	}

	@NotNull
	public ProcessedExpression getIndex() {
		return index;
	}

	@Override
	@NotNull
	public ConstantValue evaluateFormallyConstantInternal(@NotNull FormallyConstantEvaluationContext context) {
		ConstantValue containerValue = container.evaluateFormallyConstant(context);
		ConstantValue indexValue = index.evaluateFormallyConstant(context);
		int containerSize = handleContainerValue(context, containerValue);
		int intIndexValue = handleIndexValue(context, indexValue, containerSize);
		if (containerSize < 0 || intIndexValue < 0) {
			return ConstantValue.Unknown.INSTANCE;
		} else {
			// all error cases should be handled above and should have reported an error already, so we don't have to do that here
			return containerValue.selectIndex(intIndexValue);
		}
	}

	private int handleContainerValue(@NotNull FormallyConstantEvaluationContext context, @NotNull ConstantValue containerValue) {
		if (containerValue instanceof ConstantValue.Unknown) {
			return -1;
		} else if (containerValue instanceof ConstantValue.Vector) {
			return ((ConstantValue.Vector) containerValue).getSize();
		} else if (containerValue instanceof ConstantValue.Matrix) {
			return ((ConstantValue.Matrix) containerValue).getFirstSize();
		} else {
			context.evaluationInconsistency(container.getErrorSource(), "index selection found container value " + containerValue);
			return -1;
		}
	}

	private int handleIndexValue(@NotNull FormallyConstantEvaluationContext context, @NotNull ConstantValue indexValue, int containerSize) {
		BigInteger numericIndexValue = indexValue.convertToInteger();
		if (numericIndexValue == null) {
			context.evaluationInconsistency(index, "value " + indexValue + " cannot be converted to integer");
			return -1;
		}
		if (numericIndexValue.compareTo(BigInteger.ZERO) < 0) {
			context.error(index, "index is negative: " + numericIndexValue);
			return -1;
		}
		if (numericIndexValue.compareTo(MAX_INDEX_VALUE) > 0) {
			context.error(index, "index too large: " + numericIndexValue);
			return -1;
		}
		if (containerSize < 0) {
			// could not determine container type -- stop here
			return -1;
		}
		int intValue = numericIndexValue.intValue();
		if (intValue >= containerSize) {
			context.error(index, "index " + numericIndexValue + " is out of bounds for type " + container.getDataType());
			return -1;
		}
		return intValue;
	}

	@NotNull
	@Override
	protected ProcessedExpression performSubFolding(@NotNull ProcessingSidekick sidekick) {
		ProcessedExpression container = this.container.performFolding(sidekick);
		ProcessedExpression index = this.index.performFolding(sidekick);
		if (container != this.container || index != this.index) {
			try {
				return createEquivalentIndexSelection(getErrorSource(), container, index);
			} catch (TypeErrorException e) {
				sidekick.onError(getErrorSource(), "internal type error during folding of index selection");
				return this;
			}
		} else {
			return this;
		}
	}

	// creates an index selection of the same class as this, using the specified container and index
	protected abstract ProcessedIndexSelection createEquivalentIndexSelection(CmNode errorSource,
																			  ProcessedExpression container,
																			  ProcessedExpression index) throws TypeErrorException;

	public static final class BitFromVector extends ProcessedIndexSelection {

		public BitFromVector(@NotNull CmNode errorSource, @NotNull ProcessedExpression container, @NotNull ProcessedExpression index) throws TypeErrorException {
			super(errorSource, ProcessedDataType.Bit.INSTANCE, container, index);
			if (!(container.getDataType() instanceof ProcessedDataType.Vector)) {
				throw new TypeErrorException("BitFromVector selection needs vector-typed container, got " + container.getDataType());
			}
			ProcessedDataType.Vector containerType = (ProcessedDataType.Vector) container.getDataType();
			if (index.getDataType() instanceof ProcessedDataType.Vector) {
				ProcessedDataType.Vector indexType = (ProcessedDataType.Vector) index.getDataType();
				if (containerType.getSize() < (1 << indexType.getSize())) {
					throw new TypeErrorException("container size " + containerType.getSize() + " too small for index size " + indexType.getSize());
				}
			} else if (!(index.getDataType() instanceof ProcessedDataType.Integer)) {
				throw new TypeErrorException("cannot select from vector by index type " + index.getDataType());
			}
		}

		@Override
		protected ProcessedIndexSelection createEquivalentIndexSelection(CmNode errorSource, ProcessedExpression container, ProcessedExpression index) throws TypeErrorException {
			return new BitFromVector(errorSource, container, index);
		}

	}

	public static final class VectorFromMatrix extends ProcessedIndexSelection {

		public VectorFromMatrix(@NotNull CmNode errorSource, @NotNull ProcessedExpression container, @NotNull ProcessedExpression index) throws TypeErrorException {
			super(errorSource, typeCheck(container, index), container, index);
		}

		@NotNull
		private static ProcessedDataType typeCheck(@NotNull ProcessedExpression container, @NotNull ProcessedExpression index) throws TypeErrorException {
			if (!(container.getDataType() instanceof ProcessedDataType.Matrix)) {
				throw new TypeErrorException("VectorFromMatrix selection needs matrix-typed container, got " + container.getDataType());
			}
			ProcessedDataType.Matrix containerType = (ProcessedDataType.Matrix) container.getDataType();
			if (index.getDataType() instanceof ProcessedDataType.Vector) {
				ProcessedDataType.Vector indexType = (ProcessedDataType.Vector) index.getDataType();
				if (containerType.getFirstSize() < (1 << indexType.getSize())) {
					throw new TypeErrorException("first matrix size " + containerType.getFirstSize() + " is too small for index size " + indexType.getSize());
				}
			} else if (!(index.getDataType() instanceof ProcessedDataType.Integer)) {
				throw new TypeErrorException("cannot select from matrix by index type " + index.getDataType());
			}
			return new ProcessedDataType.Vector(containerType.getSecondSize());
		}

		@Override
		protected ProcessedIndexSelection createEquivalentIndexSelection(CmNode errorSource, ProcessedExpression container, ProcessedExpression index) throws TypeErrorException {
			return new VectorFromMatrix(errorSource, container, index);
		}

	}

}
