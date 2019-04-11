package name.martingeisse.mahdl.plugin.input.psi;

import com.intellij.lang.ASTNode;
import name.martingeisse.mahdl.common.cm.UnaryOperation;
import org.jetbrains.annotations.NotNull;

public final class Expression_UnaryNot extends Expression implements UnaryOperation {

    public Expression_UnaryNot(@NotNull ASTNode node) {
        super(node);
    }

        public Expression getOperand() {
            return (Expression)InternalPsiUtil.getChild(this, 1);
        }
    
		
	
	
	
}
