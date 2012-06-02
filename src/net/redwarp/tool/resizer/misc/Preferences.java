package net.redwarp.tool.resizer.misc;

import java.util.Locale;
import java.util.ResourceBundle;

public class Preferences {
	private static ResourceBundle bundle = ResourceBundle.getBundle(
			"misc.preferences", Locale.FRANCE);

	public static String getVersion() {
		return bundle.getString("version");
	}
}
