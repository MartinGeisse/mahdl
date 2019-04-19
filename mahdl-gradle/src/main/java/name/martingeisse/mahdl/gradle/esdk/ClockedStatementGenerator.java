package name.martingeisse.mahdl.gradle.esdk;

import name.martingeisse.mahdl.common.processor.expression.SignalLikeReference;
import name.martingeisse.mahdl.common.processor.statement.*;
import name.martingeisse.mahdl.gradle.CompilationErrors;

/**
 *
 */
public class ClockedStatementGenerator {

	private final GenerationModel model;
	private final StringBuilder builder;
	private final ExpressionGenerator expressionGenerator;

	public ClockedStatementGenerator(GenerationModel model, StringBuilder builder, ExpressionGenerator expressionGenerator) {
		this.model = model;
		this.builder = builder;
		this.expressionGenerator = expressionGenerator;
	}

	/**
	 * For a MaHDL statement that is part of a clocked do-block, generates Java code (using the string builder that
	 * was passed to the constructor) that adds ESDK statements to the specified statement sequence.
	 */
	public void generateStatements(String sequence, ProcessedStatement statement) {
		if (statement instanceof ProcessedBlock) {
			for (ProcessedStatement child : ((ProcessedBlock) statement).getStatements()) {
				generateStatements(sequence, child);
			}
		} else if (statement instanceof ProcessedAssignment) {
			ProcessedAssignment assignment = (ProcessedAssignment) statement;
			if (!(assignment.getLeftHandSide() instanceof SignalLikeReference)) {
				CompilationErrors.reportError(assignment.getLeftHandSide().getErrorSource(),
					"only assignment to whole registers are currently supported");
				return;
			}
			String leftHandSide = ((SignalLikeReference) assignment.getLeftHandSide()).getDefinition().getName();
			String rightHandSide = expressionGenerator.buildExpression(assignment.getRightHandSide());
			builder.append(sequence).append(".assign(").append(leftHandSide).append(", ").append(rightHandSide).append(");\n");
		} else if (statement instanceof ProcessedIf) {
			ProcessedIf processedIf = (ProcessedIf) statement;
			// TODO

		} else if (statement instanceof ProcessedSwitchStatement) {
			ProcessedSwitchStatement processedSwitchStatement = (ProcessedSwitchStatement) statement;
			// TODO
		}
	}

}
