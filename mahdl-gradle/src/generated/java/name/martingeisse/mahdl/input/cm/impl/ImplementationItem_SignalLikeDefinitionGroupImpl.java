package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.input.cm.*;

public final class ImplementationItem_SignalLikeDefinitionGroupImpl extends ImplementationItemImpl implements ImplementationItem_SignalLikeDefinitionGroup {

	private final SignalLikeKind kind;
	private final DataType dataType;
	private final CmList<SignalLikeDefinition> definitions;

	public ImplementationItem_SignalLikeDefinitionGroupImpl(int row, int column, Object[] childNodes) {
		super(row, column);
		this.kind = (SignalLikeKind) childNodes[0];
		this.dataType = (DataType) childNodes[1];
		this.definitions = (CmList<SignalLikeDefinition>) childNodes[2];
	}

	public SignalLikeKind getKind() {
		return kind;
	}

	public DataType getDataType() {
		return dataType;
	}

	public CmList<SignalLikeDefinition> getDefinitions() {
		return definitions;
	}

}
