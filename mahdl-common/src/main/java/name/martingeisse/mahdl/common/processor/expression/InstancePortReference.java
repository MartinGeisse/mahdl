/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.common.processor.expression;

import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.common.processor.definition.InstancePort;
import name.martingeisse.mahdl.common.processor.definition.ModuleInstance;
import name.martingeisse.mahdl.input.cm.CmNode;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class InstancePortReference extends ProcessedExpression {

	@NotNull
	private final ModuleInstance moduleInstance;

	@NotNull
	private final InstancePort port;

	public InstancePortReference(@NotNull CmNode errorSource,
								 @NotNull ModuleInstance moduleInstance,
								 @NotNull InstancePort port) {
		super(errorSource, port.getDataType());
		this.moduleInstance = moduleInstance;
		this.port = port;
	}

	@NotNull
	public ModuleInstance getModuleInstance() {
		return moduleInstance;
	}

	@NotNull
	public InstancePort getPort() {
		return port;
	}

	@Override
	@NotNull
	protected ConstantValue evaluateFormallyConstantInternal(@NotNull FormallyConstantEvaluationContext context) {
		return context.notConstant(this);
	}

	@NotNull
	@Override
	protected ProcessedExpression performFolding(@NotNull ErrorHandler errorHandler) {
		return this;
	}

	@NotNull
	@Override
	protected ProcessedExpression performSubFolding(@NotNull ErrorHandler errorHandler) {
		return this;
	}

}
