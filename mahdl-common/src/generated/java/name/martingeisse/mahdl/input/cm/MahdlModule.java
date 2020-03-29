package name.martingeisse.mahdl.input.cm;

public interface MahdlModule extends CmNode {

	CmOptional<CmToken> getNativeness();

	QualifiedModuleName getModuleName();

	CmList<PortDefinitionGroup> getPortDefinitionGroups();

	CmList<ImplementationItem> getImplementationItems();

}
