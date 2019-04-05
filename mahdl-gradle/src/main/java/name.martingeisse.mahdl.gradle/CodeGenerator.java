package name.martingeisse.mahdl.gradle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 *
 */
public class CodeGenerator {

	public void run(File projectFolder) {

		Properties properties = new Properties();
		try (FileInputStream fileInputStream = new FileInputStream(new File(projectFolder, "codegen.properties"))) {
			properties.load(fileInputStream);
		} catch (IOException e) {
			throw new RuntimeException("error reading codegen.properties", e);
		}

		String greeting = properties.getProperty("greeting");
		if (greeting == null) {
			throw new RuntimeException("greeting property not found");
		}

		File outputFolder = new File("src/generated/java");
		if (!outputFolder.mkdirs()) {
			if (!outputFolder.isDirectory()) {
				throw new RuntimeException("could not create output folder");
			}
		}

		try (FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFolder, "Hello.java"))) {
			try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8)) {
				try (PrintWriter out = new PrintWriter(outputStreamWriter)) {
					out.println();
					out.println("public class Hello {");
					out.println();
					out.println("\tpublic static void main(String[] args) {");
					out.println("\t\tSystem.out.println(\"" + greeting.replace("\\", "\\\\").replace("\"", "\\\"") + "\");");
					out.println("\t}");
					out.println();
					out.println("}");
					out.println();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("error generating output file", e);
		}

	}

}
