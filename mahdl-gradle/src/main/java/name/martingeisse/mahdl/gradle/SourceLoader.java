package name.martingeisse.mahdl.gradle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import name.martingeisse.mahdl.input.FlexGeneratedMahdlLexer;
import name.martingeisse.mahdl.input.MapagGeneratedMahdlParser;
import name.martingeisse.mahdl.input.cm.CmNode;
import name.martingeisse.mahdl.input.cm.Module;
import name.martingeisse.mahdl.input.cm.impl.CmTokenImpl;
import name.martingeisse.mahdl.input.cm.impl.IElementType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public final class SourceLoader {

	private static final String MAHDL_FILENAME_SUFFIX = ".mahdl";

	private final File mahdlDir;
	private final Map<ImmutableList<String>, Module> codeModels = new HashMap<>();
	private final Map<ImmutableList<String>, File> dataFiles = new HashMap<>();
	private final List<String> segments = new ArrayList<>();

	public SourceLoader(File mahdlDir) {
		this.mahdlDir = mahdlDir;
	}

	public ImmutableMap<ImmutableList<String>, Module> getCodeModels() {
		return ImmutableMap.copyOf(codeModels);
	}

	public ImmutableMap<ImmutableList<String>, File> getDataFiles() {
		return ImmutableMap.copyOf(dataFiles);
	}

	public void run() {
		segments.clear();
		loadSources(mahdlDir, new ArrayList<>());
	}

	private void loadSources(File mahdlPackageDir, List<String> segments) {
		File[] entries = mahdlPackageDir.listFiles();
		if (entries == null) {
			return;
		}
		for (File entry : entries) {
			if (entry.isDirectory()) {
				pushSegment(entry.getName());
				loadSources(entry, segments);
				popSegment();
			} else if (entry.isFile()) {
				String filename = entry.getName();
				if (filename.endsWith(MAHDL_FILENAME_SUFFIX)) {
					String moduleName = filename.substring(0, filename.length() - MAHDL_FILENAME_SUFFIX.length());
					Module codeModel = loadSourceFile(entry);
					if (codeModel != null) {
						codeModels.put(buildName(moduleName), codeModel);
					}
				} else {
					dataFiles.put(buildName(filename), entry);
				}
			}
		}
	}

	private ImmutableList<String> buildName(String localName) {
		pushSegment(localName);
		ImmutableList<String> fullName = ImmutableList.copyOf(segments);
		popSegment();
		return fullName;
	}

	private void pushSegment(String segment) {
		segments.add(segment);
	}

	private void popSegment() {
		segments.remove(segments.size() - 1);
	}

	private Module loadSourceFile(File file) {
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			try (InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8)) {
				return loadSourceFile(file.getPath(), inputStreamReader);
			}
		} catch (IOException e) {
			CompilationErrors.reportError(file.getPath(), 0, e.toString());
			return null;
		}
	}

	private Module loadSourceFile(String path, Reader reader) throws IOException {

		// run lexer
		FlexGeneratedMahdlLexer lexer = new FlexGeneratedMahdlLexer(reader);
		List<CmTokenImpl> tokens = new ArrayList<>();
		while (true) {
			IElementType elementType = lexer.advance();
			if (elementType == null) {
				break;
			}
			tokens.add(new CmTokenImpl(lexer.yyline + 1, lexer.yycolumn + 1, lexer.yytext().toString(), elementType));
		}

		// run parser
		MapagGeneratedMahdlParser parser = new MapagGeneratedMahdlParser();
		CmNode rootNode = parser.parse(tokens.toArray(new CmTokenImpl[0]));
		if (rootNode == null) {
			return null;
		}

		// check root node type
		if (rootNode instanceof Module) {
			return (Module) rootNode;
		} else {
			CompilationErrors.reportError(path, 0, "wrong root CM node: " + rootNode);
			return null;
		}

	}

}
