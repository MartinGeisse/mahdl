package name.martingeisse.mahdl.common.cm;

public interface Statement_IfThen extends Statement {

        	Expression getCondition();
        	Statement getThenBranch();
    
}
