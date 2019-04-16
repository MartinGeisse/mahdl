package name.martingeisse.mahdl.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *
 */
class MahdlGradlePlugin implements Plugin<Project> {

	void apply(Project project) {
		MahdlCodegenTask task = project.tasks.create("mahdlCodegen", MahdlCodegenTask.class);
		task.group = 'build';
		task.description = 'Generates Java code from MaHDL sources.';
		task.sourceDirectory = new File(project.projectDir, "src/mahdl");
		task.outputDirectory = new File(project.getBuildDir(), "mahdl-java");
		project.tasks.compileJava.dependsOn(task);
	}

}
