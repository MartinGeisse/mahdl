package name.martingeisse.mahdl.common;

import name.martingeisse.mahdl.input.cm.CmNode;
import name.martingeisse.mahdl.input.cm.MahdlModule;
import name.martingeisse.mahdl.input.cm.QualifiedModuleName;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public interface Environment {

	InputStream openDataFile(CmNode anchor, String filename) throws IOException;

	void validateModuleNameAgainstFilePath(MahdlModule module, QualifiedModuleName name) throws IOException;

	MahdlModule resolveModuleReference(QualifiedModuleName name) throws ReferenceResolutionException;

	class Holder {
		public static Environment INSTANCE = null;
	}

}
