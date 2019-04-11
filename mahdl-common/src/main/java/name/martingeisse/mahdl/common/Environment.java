package name.martingeisse.mahdl.common;

import name.martingeisse.mahdl.common.cm.CmNode;
import name.martingeisse.mahdl.common.cm.Module;
import name.martingeisse.mahdl.common.cm.QualifiedModuleName;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public interface Environment {

	InputStream openDataFile(CmNode anchor, String filename) throws IOException;

	void validateModuleNameAgainstFilePath(Module module, QualifiedModuleName name) throws IOException;

	Module resolveModuleReference(QualifiedModuleName name) throws ReferenceResolutionException;

	class Holder {
		public static Environment INSTANCE = null;
	}

}
