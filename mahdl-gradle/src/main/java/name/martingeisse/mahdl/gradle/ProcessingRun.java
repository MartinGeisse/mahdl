package name.martingeisse.mahdl.gradle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import name.martingeisse.mahdl.input.cm.Module;
import org.apache.commons.lang3.StringUtils;

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
		for (Map.Entry<ImmutableList<String>, Module> codeModelEntry : codeModels.entrySet()) {
			ImmutableList<String> qualifiedName = codeModelEntry.getKey();
			Module moduleCm = codeModelEntry.getValue();

			ImmutableList<String> packageName = qualifiedName.subList(0, qualifiedName.size() - 1);
			String localName = qualifiedName.get(qualifiedName.size() - 1);

			StringBuilder builder = new StringBuilder();
			if (!packageName.isEmpty()) {
				builder.append("package ").append(StringUtils.join(packageName, '.')).append(";\n");
				builder.append("\n");
			}
			builder.append("import name.martingeisse.esdk.core.rtl.*;\n");
			builder.append("import name.martingeisse.esdk.core.rtl.synthesis.verilog.*;\n");
			builder.append("\n");
			builder.append("public class ").append(localName).append(" extends RtlItem {\n");
			builder.append("\n");
			builder.append("	public ").append(localName).append("(RtlRealm realm) {\n");
			builder.append("		super(realm);\n");
			builder.append("	}\n");
			builder.append("\n");
			builder.append("	@Override\n");
			builder.append("	public VerilogContribution getVerilogContribution() {\n");
			builder.append("		return new EmptyVerilogContribution();\n");
			builder.append("	}\n");
			builder.append("\n");
			builder.append("}\n");

			generatedCode.put(qualifiedName, builder.toString());
		}

	}

}
