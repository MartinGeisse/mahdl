package name.martingeisse.mahdl.plugin.input.psi;

import com.intellij.lang.ASTNode;
import name.martingeisse.mahdl.common.cm.BinaryOperation;
import org.jetbrains.annotations.NotNull;

public final class Expression_BinaryGreaterThanOrEqual extends Expression implements BinaryOperation {

    public Expression_BinaryGreaterThanOrEqual(@NotNull ASTNode node) {
        super(node);
    }

        public Expression getLeftOperand() {
            return (Expression)InternalPsiUtil.getChild(this, 0);
        }
        public Expression getRightOperand() {
            return (Expression)InternalPsiUtil.getChild(this, 2);
        }
    
		
	
	
	
}
