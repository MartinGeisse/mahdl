package name.martingeisse.mahdl.input.cm;

public interface CmNode {

	CmNode getCmParent();

	int compareStartOffset(CmNode other);

}
