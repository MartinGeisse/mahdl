package name.martingeisse.mahdl.common.cm;

public interface Statement_IfThenElse extends Statement {

        	Expression getCondition();
        	Statement getThenBranch();
        	Statement getElseBranch();
    
}
