package name.martingeisse.mahdl.gradle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import name.martingeisse.mahdl.input.cm.Module;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

	public void run() throws IOException {

		// load sources
		SourceLoader sourceLoader = new SourceLoader(mahdlDir);
		sourceLoader.run();
		this.codeModels = sourceLoader.getCodeModels();
		this.dataFiles = sourceLoader.getDataFiles();

		// TODO ...
		System.out.println();
		File dummyOutput = new File(javaDir, "Dummy.java");
		FileUtils.writeStringToFile(dummyOutput, "public class Dummy {public static void foo() {System.out.println(\"FOO\");}}", StandardCharsets.UTF_8);

	}

}
