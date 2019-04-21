package name.martingeisse.mahdl.gradle.model;

import name.martingeisse.mahdl.common.processor.definition.InstancePort;
import name.martingeisse.mahdl.common.processor.definition.SignalLike;
import name.martingeisse.mahdl.common.processor.expression.*;
import name.martingeisse.mahdl.common.processor.statement.*;

/**
 *
 */
public abstract class DoBlockInfo {

	private final String name;
	private final ProcessedDoBlock doBlock;

	public DoBlockInfo(String name, ProcessedDoBlock doBlock) {
		this.name = name;
		this.doBlock = doBlock;
	}

	public String getName() {
		return name;
	}

	public ProcessedDoBlock getDoBlock() {
		return doBlock;
	}

	protected void analyze(ProcessedStatement statement) {
		if (statement instanceof ProcessedAssignment) {
			analyzeAssignmentTo(((ProcessedAssignment) statement).getLeftHandSide());
		} else if (statement instanceof ProcessedBlock) {
			for (ProcessedStatement childStatement : ((ProcessedBlock) statement).getStatements()) {
				analyze(childStatement);
			}
		} else if (statement instanceof ProcessedIf) {
			ProcessedIf processedIf = (ProcessedIf) statement;
			analyze(processedIf.getThenBranch());
			analyze(processedIf.getElseBranch());
		} else if (statement instanceof ProcessedSwitchStatement) {
			ProcessedSwitchStatement processedSwitch = (ProcessedSwitchStatement) statement;
			for (ProcessedSwitchStatement.Case aCase : processedSwitch.getCases()) {
				analyze(aCase.getBranch());
			}
			analyze(processedSwitch.getDefaultBranch());
		}
	}

	protected void analyzeAssignmentTo(ProcessedExpression destination) {
		if (destination instanceof SignalLikeReference) {
			analyzeAssignmentTo(((SignalLikeReference) destination).getDefinition());
		} else if (destination instanceof InstancePortReference) {
			analyzeAssignmentTo(((InstancePortReference) destination).getPort());
		} else if (destination instanceof ProcessedIndexSelection) {
			analyzeAssignmentTo(((ProcessedIndexSelection) destination).getContainer());
		} else if (destination instanceof ProcessedRangeSelection) {
			analyzeAssignmentTo(((ProcessedRangeSelection) destination).getContainer());
		} else if (destination instanceof ProcessedBinaryOperation) {
			ProcessedBinaryOperation binaryOperation = (ProcessedBinaryOperation) destination;
			if (binaryOperation.getOperator() == ProcessedBinaryOperator.VECTOR_CONCAT) {
				analyzeAssignmentTo(binaryOperation.getLeftOperand());
				analyzeAssignmentTo(binaryOperation.getRightOperand());
			}
		}
	}

	protected abstract void analyzeAssignmentTo(SignalLike destination);

	protected abstract void analyzeAssignmentTo(InstancePort destination);

}
