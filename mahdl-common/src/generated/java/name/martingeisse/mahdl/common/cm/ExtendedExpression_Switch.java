package name.martingeisse.mahdl.common.cm;

public interface ExtendedExpression_Switch extends ExtendedExpression {

        	Expression getSelector();
        	CmList<ExpressionCaseItem> getItems();
    
}
