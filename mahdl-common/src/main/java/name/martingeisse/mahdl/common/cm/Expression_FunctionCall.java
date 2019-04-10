package name.martingeisse.mahdl.common.cm;

public interface Expression_FunctionCall extends Expression {

        	CmToken getFunctionName();
        	CmList<Expression> getArguments();
    
}
