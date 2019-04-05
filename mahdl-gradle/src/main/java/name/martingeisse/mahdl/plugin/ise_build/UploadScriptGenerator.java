package name.martingeisse.mahdl.plugin.ise_build;

import java.io.PrintWriter;

/**
 *
 */
public class UploadScriptGenerator extends TextFileGenerator {

	private final BuildContext buildContext;

	public UploadScriptGenerator(BuildContext buildContext) {
		this.buildContext = buildContext;
	}

	@Override
	protected void generate(PrintWriter out) {
		out.println("ssh martin@ise ./auto-ise/upload.sh");
	}

}
