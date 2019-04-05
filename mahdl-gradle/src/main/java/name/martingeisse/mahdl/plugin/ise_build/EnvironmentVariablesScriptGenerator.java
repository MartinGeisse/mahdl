package name.martingeisse.mahdl.plugin.ise_build;

import java.io.PrintWriter;

/**
 *
 */
public class EnvironmentVariablesScriptGenerator extends TextFileGenerator {

	private final BuildContext buildContext;

	public EnvironmentVariablesScriptGenerator(BuildContext buildContext) {
		this.buildContext = buildContext;
	}

	@Override
	protected void generate(PrintWriter out) {
		out.println("export XST_SCRIPT_FILE=build.xst");
		out.println("export CONSTRAINTS_FILE=build.ucf");
	}

}
