package name.martingeisse.mahdl.common.cm;

public interface SignalLikeDefinition_WithInitializer extends SignalLikeDefinition {

        	CmToken getIdentifier();
        	ExtendedExpression getInitializer();
    
}
