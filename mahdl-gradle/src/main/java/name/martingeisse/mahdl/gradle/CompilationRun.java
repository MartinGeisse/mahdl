package name.martingeisse.mahdl.gradle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import name.martingeisse.mahdl.input.cm.Module;

import java.io.File;

/**
 *
 */
public class CompilationRun {

	private final File mahdlDir;
	private final File javaDir;
	private ImmutableMap<ImmutableList<String>, Module> codeModels = null;
	private ImmutableMap<ImmutableList<String>, File> dataFiles = null;

	public CompilationRun(File mahdlDir, File javaDir) {
		this.mahdlDir = mahdlDir;
		this.javaDir = javaDir;
	}

	public void run() {

		// load sources
		SourceLoader sourceLoader = new SourceLoader(mahdlDir);
		sourceLoader.run();
		this.codeModels = sourceLoader.getCodeModels();
		this.dataFiles = sourceLoader.getDataFiles();

		// TODO ...
		System.out.println();

	}

}
