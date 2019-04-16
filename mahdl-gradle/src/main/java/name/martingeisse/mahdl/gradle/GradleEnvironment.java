package name.martingeisse.mahdl.gradle;

import name.martingeisse.mahdl.common.Environment;
import name.martingeisse.mahdl.common.ReferenceResolutionException;
import name.martingeisse.mahdl.input.cm.CmNode;
import name.martingeisse.mahdl.input.cm.Module;
import name.martingeisse.mahdl.input.cm.QualifiedModuleName;
import name.martingeisse.mahdl.input.cm.impl.ModuleWrapper;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class GradleEnvironment implements Environment {

	public static void initialize() {
		if (Holder.INSTANCE == null) {
			Holder.INSTANCE = new GradleEnvironment();
		}
	}

	@Override
	public InputStream openDataFile(CmNode anchor, String filename) throws IOException {
		ModuleWrapper moduleWrapper = getModuleWrapper(anchor);
		if (moduleWrapper == null) {
			throw new IOException("could not locate data file due to previous errors");
		}
		return moduleWrapper.getProcessingRun().openDataFile(moduleWrapper, filename);
	}

	@Override
	public void validateModuleNameAgainstFilePath(Module module, QualifiedModuleName name) throws IOException {
		ModuleWrapper moduleWrapper = getModuleWrapper(module);
		if (moduleWrapper == null) {
			throw new IOException("could not validate module name due to previous errors");
		}
		moduleWrapper.getProcessingRun().validateModuleNameAgainstFilePath(moduleWrapper, name);
	}

	@Override
	public Module resolveModuleReference(QualifiedModuleName name) throws ReferenceResolutionException {
		ModuleWrapper moduleWrapper = getModuleWrapper(name);
		if (moduleWrapper == null) {
			throw new ReferenceResolutionException("could not resolve module reference due to previous errors");
		}
		return moduleWrapper.getProcessingRun().resolveModuleReference(moduleWrapper, name);
	}

	private ModuleWrapper getModuleWrapper(CmNode node) {
		while (node.getCmParent() != null) {
			node = node.getCmParent();
		}
		return (node instanceof ModuleWrapper) ? (ModuleWrapper) node : null;
	}

}
