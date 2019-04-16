package name.martingeisse.mahdl.gradle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import name.martingeisse.mahdl.common.ReferenceResolutionException;
import name.martingeisse.mahdl.common.processor.ErrorHandler;
import name.martingeisse.mahdl.common.processor.ModuleProcessor;
import name.martingeisse.mahdl.common.processor.definition.ModuleDefinition;
import name.martingeisse.mahdl.input.cm.CmToken;
import name.martingeisse.mahdl.input.cm.Module;
import name.martingeisse.mahdl.input.cm.QualifiedModuleName;
import name.martingeisse.mahdl.input.cm.impl.CmNodeImpl;
import name.martingeisse.mahdl.input.cm.impl.ModuleWrapper;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ProcessingRun {

	private ImmutableMap<ImmutableList<String>, ModuleWrapper> codeModels;
	private ImmutableMap<ModuleWrapper, ImmutableList<String>> codeModelsReverse;
	private ImmutableMap<ImmutableList<String>, File> dataFiles;
	private Map<ImmutableList<String>, String> generatedCode;

	public ProcessingRun(ImmutableMap<ImmutableList<String>, ModuleWrapper> codeModels, ImmutableMap<ImmutableList<String>, File> dataFiles) {
		this.codeModels = codeModels;
		this.codeModelsReverse = ImmutableMap.copyOf(reverse(codeModels));
		this.dataFiles = dataFiles;
	}

	private static final <A, B> Map<B, A> reverse(Map<A, B> map) {
		Map<B, A> result = new HashMap<>();
		for (Map.Entry<A, B> entry : map.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}
		return result;
	}

	public ImmutableMap<ImmutableList<String>, String> getGeneratedCode() {
		return ImmutableMap.copyOf(generatedCode);
	}

	public void run() {
		generatedCode = new HashMap<>();
		for (Map.Entry<ImmutableList<String>, ModuleWrapper> codeModelEntry : codeModels.entrySet()) {
			ImmutableList<String> qualifiedName = codeModelEntry.getKey();
			ModuleWrapper moduleWrapper = codeModelEntry.getValue();
			try {
				moduleWrapper.setProcessingRun(this);
				Module moduleCm = moduleWrapper.getModule();
				ErrorHandler errorHandler = (errorSource, message) -> {
					File file = moduleWrapper.getFile();
					int row = ((CmNodeImpl)errorSource).getRow();
					CompilationErrors.reportError(file.getPath(), row, message);
				};
				ModuleDefinition moduleDefinition = new ModuleProcessor(moduleCm, errorHandler).process();

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
			} finally {
				moduleWrapper.setProcessingRun(null);
			}
		}
	}

	public InputStream openDataFile(ModuleWrapper moduleWrapper, String filename) throws IOException {
		ImmutableList<String> qualifiedModuleName = codeModelsReverse.get(moduleWrapper);
		ImmutableList<String> qualifiedFileName = replaceLast(qualifiedModuleName, filename);
		File dataFile = dataFiles.get(qualifiedFileName);
		if (dataFile == null) {
			throw new FileNotFoundException(filename);
		}
		return new FileInputStream(dataFile);
	}

	public void validateModuleNameAgainstFilePath(ModuleWrapper moduleWrapper, QualifiedModuleName name) throws IOException {
		ImmutableList<String> moduleNameFromFilename = codeModelsReverse.get(moduleWrapper);
		ImmutableList<String> moduleNameFromContents = convertQualifiedModuleName(name);
		if (!moduleNameFromContents.equals(moduleNameFromFilename)) {
			throw new IOException("expected module " + toString(moduleNameFromFilename) + " in this file, found " + toString(moduleNameFromContents));
		}
	}

	public Module resolveModuleReference(ModuleWrapper moduleWrapper, QualifiedModuleName name) throws ReferenceResolutionException {
		ImmutableList<String> segments = convertQualifiedModuleName(name);
		ModuleWrapper resolved = codeModels.get(segments);
		if (resolved == null) {
			throw new ReferenceResolutionException("unknown module: " + toString(segments));
		}
		return resolved.getModule();
	}

	// helper methods below

	private ImmutableList<String> replaceLast(ImmutableList<String> list, String segment) {
		List<String> result = new ArrayList<>(list);
		result.set(result.size() - 1, segment);
		return ImmutableList.copyOf(result);
	}

	private ImmutableList<String> convertQualifiedModuleName(QualifiedModuleName qualifiedModuleName) {
		List<String> segments = new ArrayList<>();
		for (CmToken token : qualifiedModuleName.getSegments().getAll()) {
			System.out.println("(((" + token.getText() + ")))");
			segments.add(token.getText());
		}
		return ImmutableList.copyOf(segments);
	}

	private String toString(ImmutableList<String> segments) {
		return StringUtils.join(segments, '.');
	}

}
