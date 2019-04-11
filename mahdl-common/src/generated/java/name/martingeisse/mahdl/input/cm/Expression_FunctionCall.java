package name.martingeisse.mahdl.input.cm;

public interface Expression_FunctionCall extends Expression {

        	CmToken getFunctionName();
        	CmList<Expression> getArguments();
    
}
