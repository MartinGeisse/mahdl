package name.martingeisse.mahdl.common.cm;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class CmUtil {

	// prevent instantiation
	private CmUtil() {
	}

	@NotNull
	public static String canonicalizeQualifiedModuleName(@NotNull QualifiedModuleName name) {
		return StringUtils.join(parseQualifiedModuleName(name), '.');
	}

	@NotNull
	public static String[] parseQualifiedModuleName(@NotNull QualifiedModuleName name) {
		List<String> segments = new ArrayList<>();
		for (CmToken segment : name.getSegments().getAll()) {
			segments.add(segment.getText());
		}
		return segments.toArray(new String[segments.size()]);
	}

}
