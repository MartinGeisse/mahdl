package name.martingeisse.mahdl.plugin.input.psi;

import com.intellij.lang.ASTNode;
import name.martingeisse.mahdl.common.cm.UnaryOperation;
import org.jetbrains.annotations.NotNull;

public final class Expression_UnaryMinus extends Expression implements UnaryOperation {

    public Expression_UnaryMinus(@NotNull ASTNode node) {
        super(node);
    }

        public Expression getOperand() {
            return (Expression)InternalPsiUtil.getChild(this, 1);
        }
    
		
	
	
	
}
