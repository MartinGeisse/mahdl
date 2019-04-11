package name.martingeisse.mahdl.common.cm;

public interface Statement_Switch extends Statement {

        	Expression getSelector();
        	CmList<StatementCaseItem> getItems();
    
}
