package name.martingeisse.mahdl.common.cm;

public interface CmOptional<T extends CmNode> extends CmNode {

    T getIt();

}
