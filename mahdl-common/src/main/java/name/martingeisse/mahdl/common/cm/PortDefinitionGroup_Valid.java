package name.martingeisse.mahdl.common.cm;

public interface PortDefinitionGroup_Valid extends PortDefinitionGroup {

        	PortDirection getDirection();
        	DataType getDataType();
        	CmList<PortDefinition> getDefinitions();
    
}
