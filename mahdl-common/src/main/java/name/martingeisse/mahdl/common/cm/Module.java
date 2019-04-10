package name.martingeisse.mahdl.common.cm;

public interface Module extends CmNode {

        	CmOptional<CmToken> getNativeness();
        	QualifiedModuleName getModuleName();
        	CmList<PortDefinitionGroup> getPortDefinitionGroups();
        	CmList<ImplementationItem> getImplementationItems();
    
}
