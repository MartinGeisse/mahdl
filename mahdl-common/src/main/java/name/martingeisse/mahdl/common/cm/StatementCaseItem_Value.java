package name.martingeisse.mahdl.common.cm;

public interface StatementCaseItem_Value extends StatementCaseItem {

        	CmList<Expression> getSelectorValues();
        	CmList<Statement> getStatements();
    
}
