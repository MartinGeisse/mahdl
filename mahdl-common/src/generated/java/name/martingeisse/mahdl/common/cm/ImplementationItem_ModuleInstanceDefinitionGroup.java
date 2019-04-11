package name.martingeisse.mahdl.common.cm;

public interface ImplementationItem_ModuleInstanceDefinitionGroup extends ImplementationItem {

        	QualifiedModuleName getModuleName();
        	CmList<ModuleInstanceDefinition> getDefinitions();
    
}
