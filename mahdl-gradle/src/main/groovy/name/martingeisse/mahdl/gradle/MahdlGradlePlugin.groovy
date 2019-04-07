package name.martingeisse.mahdl.gradle

import name.martingeisse.mahdl.plugin.CodeGenerator
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *
 */
class MahdlGradlePlugin implements Plugin<Project> {

	MahdlGradlePlugin() {
	}

	void apply(Project project) {

		project.task('mahdlCodegen') {
			group 'build'
			description 'Generates Java code from MaHDL sources.'
			project.tasks.compileJava.dependsOn(delegate)
			doLast {
				new CodeGenerator().run(project.projectDir)
				CompilationErrors.failOnErrors();
			}
		}

	}

}
