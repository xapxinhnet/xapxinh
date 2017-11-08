package net.xapxinh.center.server.locale;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Sann Tran
 */
public class Constants {

	private static Constants viConst;
	private static Constants enConst;

	private final ResourceBundle resourceBundle;

	private static final ResourceBundle VI_VN_BUNDLE = ResourceBundle.getBundle("Constants", new Locale("vi", "VN"), new UTF8Control());
	private static final ResourceBundle EN_US_BUNDLE = ResourceBundle.getBundle("Constants", new Locale("en", "US"), new UTF8Control());

	private Constants(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public static Constants get(String locale) {
		if ("vi".equals(locale)) {
			if (viConst == null) {
				viConst = new Constants(VI_VN_BUNDLE);
			}
			return viConst;
		}
		else {
			if (enConst == null) {
				enConst = new Constants(EN_US_BUNDLE);
			}
			return enConst;
		}
	}
}
