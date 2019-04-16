package name.martingeisse.mahdl.gradle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import name.martingeisse.mahdl.input.cm.Module;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ProcessingRun {

	private ImmutableMap<ImmutableList<String>, Module> codeModels;
	private ImmutableMap<ImmutableList<String>, File> dataFiles;
	private Map<ImmutableList<String>, String> generatedCode;

	public ProcessingRun(ImmutableMap<ImmutableList<String>, Module> codeModels, ImmutableMap<ImmutableList<String>, File> dataFiles) {
		this.codeModels = codeModels;
		this.dataFiles = dataFiles;
	}

	public ImmutableMap<ImmutableList<String>, String> getGeneratedCode() {
		return ImmutableMap.copyOf(generatedCode);
	}

	public void run() {
		generatedCode = new HashMap<>();

		// TODO test
		ImmutableList<String> key = ImmutableList.of("test", "Dummy");
		String code = "package test; public class Dummy {public static void foo() {System.out.println(\"FOO\");}}";
		generatedCode.put(key, code);

	}

}
