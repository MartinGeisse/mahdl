package name.martingeisse.mahdl.gradle.model;

import name.martingeisse.mahdl.common.processor.definition.SignalLike;
import name.martingeisse.mahdl.common.processor.expression.InstancePortReference;
import name.martingeisse.mahdl.common.processor.statement.ProcessedDoBlock;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public final class ContinuousDoBlockInfo extends DoBlockInfo {

	private final SortedSet<SignalLike> signalLikes;
	private final SortedSet<InstancePortReference> instancePortReferences;

	public ContinuousDoBlockInfo(String name, ProcessedDoBlock doBlock) {
		super(name, doBlock);
		this.signalLikes = new TreeSet<>();
		this.instancePortReferences = new TreeSet<>();
		analyze(doBlock.getBody());
	}

	public SortedSet<SignalLike> getSignalLikes() {
		return signalLikes;
	}

	public SortedSet<InstancePortReference> getInstancePortReferences() {
		return instancePortReferences;
	}

	@Override
	protected void analyzeAssignmentTo(SignalLike destination) {
		signalLikes.add(destination);
	}

	@Override
	protected void analyzeAssignmentTo(InstancePortReference destination) {
		instancePortReferences.add(destination);
	}

	public String findLocalClockSource(InstancePortReference target) {

	}

}
