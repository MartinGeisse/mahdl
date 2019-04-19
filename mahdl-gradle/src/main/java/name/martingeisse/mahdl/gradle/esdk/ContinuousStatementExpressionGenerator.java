package name.martingeisse.mahdl.gradle.esdk;

import name.martingeisse.mahdl.common.processor.definition.SignalLike;
import name.martingeisse.mahdl.common.processor.expression.*;
import name.martingeisse.mahdl.common.processor.statement.*;
import name.martingeisse.mahdl.gradle.CompilationErrors;
import name.martingeisse.mahdl.input.cm.CmNode;
import name.martingeisse.mahdl.input.cm.impl.CmTokenImpl;
import name.martingeisse.mahdl.input.cm.impl.IElementType;

/**
 *
 */
public class ContinuousStatementExpressionGenerator {

	private static final CmNode DUMMY_CM_NODE = new CmTokenImpl(0, 0, "", IElementType.WHITE_SPACE);

	private static final ProcessedExpression UNCHANGED = new UnknownExpression(DUMMY_CM_NODE);

	private final GenerationModel model;
	private final StringBuilder builder;

	public ContinuousStatementExpressionGenerator(GenerationModel model, StringBuilder builder) {
		this.model = model;
		this.builder = builder;
	}

	/**
	 * For a MaHDL continuous do-block and a signal that gets assigned to in that block, builds an equivalent
	 * Java expression to be used in an ESDK module constructor. The expression evaluates to an RtlBitSignal or
	 * RtlVector signal.
	 * <p>
	 * This method may use the string builder provided in the constructor to build helper expressions as Java
	 * constructor statements; therefore, this method must not be called while building a Java statement is in progress.
	 */
	public ProcessedExpression buildEquivalentExpression(GenerationModel.DoBlockInfo<SignalLike> doBlockInfo, SignalLike target) {
		return buildEquivalentExpression(doBlockInfo.getDoBlock().getBody(), target, UNCHANGED);
	}

	private ProcessedExpression buildEquivalentExpression(ProcessedStatement statement, SignalLike target, ProcessedExpression soFar) {
		if (statement instanceof ProcessedBlock) {

			for (ProcessedStatement child : ((ProcessedBlock) statement).getStatements()) {
				soFar = buildEquivalentExpression(child, target, soFar);
			}
			return soFar;

		} else if (statement instanceof ProcessedAssignment) {

			ProcessedAssignment assignment = (ProcessedAssignment) statement;
			ProcessedExpression leftHandSide = assignment.getLeftHandSide();
			if (!(leftHandSide instanceof SignalLikeReference)) {
				CompilationErrors.reportError(assignment.getLeftHandSide().getErrorSource(),
					"only assignment to whole registers are currently supported");
				return soFar;
			}
			if (((SignalLikeReference) leftHandSide).getDefinition() == target) {
				return assignment.getRightHandSide();
			} else {
				return soFar;
			}

		} else if (statement instanceof ProcessedIf) {

			// Might do a real equals() check here, but that is currently not supported by ProcessedExpression. This is
			// not a real problem though since at worst we get a conditional expression with equal "then" and "else"
			// expressions.
			ProcessedIf processedIf = (ProcessedIf) statement;
			ProcessedExpression thenBranch = buildEquivalentExpression(processedIf.getThenBranch(), target, soFar);
			ProcessedExpression elseBranch = buildEquivalentExpression(processedIf.getElseBranch(), target, soFar);
			if (thenBranch == elseBranch) {
				return thenBranch;
			}
			try {
				return new ProcessedConditional(statement.getErrorSource(), processedIf.getCondition(), thenBranch, elseBranch);
			} catch (TypeErrorException e) {
				return soFar; // error has been reported during processing already
			}

		} else if (statement instanceof ProcessedSwitchStatement) {

			ProcessedSwitchStatement processedSwitchStatement = (ProcessedSwitchStatement) statement;
			TODO

		} else {

			// NOP or unknown statement
			return soFar;

		}
	}

}
