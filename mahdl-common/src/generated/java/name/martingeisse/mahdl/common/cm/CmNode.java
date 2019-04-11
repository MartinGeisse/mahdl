package name.martingeisse.mahdl.common.cm;

public interface CmNode {

	CmNode getCmParent();

	int compareStartOffset(CmNode other);

}
