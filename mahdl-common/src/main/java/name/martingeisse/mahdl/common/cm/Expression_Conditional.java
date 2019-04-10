package name.martingeisse.mahdl.common.cm;

public interface Expression_Conditional extends Expression {

        	Expression getCondition();
        	Expression getThenBranch();
        	Expression getElseBranch();
    
}
