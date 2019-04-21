package name.martingeisse.mahdl.gradle.model;

import name.martingeisse.mahdl.common.processor.definition.InstancePort;
import name.martingeisse.mahdl.common.processor.definition.SignalLike;
import name.martingeisse.mahdl.common.processor.statement.ProcessedDoBlock;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public final class ContinuousDoBlockInfo extends DoBlockInfo {

	private final SortedSet<SignalLike> signalLikes;
	private final SortedSet<InstancePort> instancePorts;

	public ContinuousDoBlockInfo(String name, ProcessedDoBlock doBlock) {
		super(name, doBlock);
		this.signalLikes = new TreeSet<>();
		this.instancePorts = new TreeSet<>();
		analyze(doBlock.getBody());
	}

	public SortedSet<SignalLike> getSignalLikes() {
		return signalLikes;
	}

	public SortedSet<InstancePort> getInstancePorts() {
		return instancePorts;
	}

	@Override
	protected void analyzeAssignmentTo(SignalLike destination) {
		signalLikes.add(destination);
	}

	@Override
	protected void analyzeAssignmentTo(InstancePort destination) {
		instancePorts.add(destination);
	}

}
