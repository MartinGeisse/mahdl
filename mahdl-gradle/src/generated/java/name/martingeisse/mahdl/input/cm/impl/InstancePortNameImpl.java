package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.input.cm.CmToken;
import name.martingeisse.mahdl.input.cm.InstancePortName;

public final class InstancePortNameImpl extends CmNodeImpl implements InstancePortName {

	private final CmToken identifier;

	public InstancePortNameImpl(int row, int column, Object[] childNodes) {
		super(row, column);
		this.identifier = (CmToken) childNodes[0];
	}

	public CmToken getIdentifier() {
		return identifier;
	}

}
