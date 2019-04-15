/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.mahdl.gradle;

import java.io.File;

/**
 *
 */
public class MahdlGradleMain {

	public static void main(File projectDir) {
		File mahdlDir = new File(projectDir, "src/main/mahdl");
		File javaDir = new File(projectDir, "src/generated/java");
		CompilationRun compilationRun = new CompilationRun(mahdlDir, javaDir);
		compilationRun.run();
		CompilationErrors.failOnErrors();
	}

}
