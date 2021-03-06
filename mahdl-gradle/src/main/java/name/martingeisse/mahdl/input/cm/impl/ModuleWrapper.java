package name.martingeisse.mahdl.input.cm.impl;

import name.martingeisse.mahdl.gradle.ProcessingRun;
import name.martingeisse.mahdl.input.cm.CmNode;
import name.martingeisse.mahdl.input.cm.MahdlModule;

import java.io.File;

/**
 *
 */
public final class ModuleWrapper extends CmNodeImpl {

	private final File file;
	private final MahdlModule module;
	private ProcessingRun processingRun;

	public ModuleWrapper(File file, MahdlModule module) {
		super(0, 0);
		this.file = file;
		this.module = module;
		((CmNodeImpl) this.module).setParent(this);
	}

	public File getFile() {
		return file;
	}

	public MahdlModule getModule() {
		return module;
	}

	public ProcessingRun getProcessingRun() {
		return processingRun;
	}

	public void setProcessingRun(ProcessingRun processingRun) {
		this.processingRun = processingRun;
	}

	public static ModuleWrapper get(CmNode node) {
		while (node.getCmParent() != null) {
			node = node.getCmParent();
		}
		return (node instanceof ModuleWrapper) ? (ModuleWrapper) node : null;
	}

}
