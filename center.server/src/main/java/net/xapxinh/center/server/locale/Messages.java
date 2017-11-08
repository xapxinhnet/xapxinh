package net.xapxinh.center.server.locale;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Sann Tran
 */
public class Messages {
	private static Messages viMessages;
	private static Messages enMessages;

	private final ResourceBundle resourceBundle;
	private final MessageFormat messageFormat;

	private static final ResourceBundle VI_VN_BUNDLE = ResourceBundle.getBundle("Messages", new Locale("vi", "VN"), new UTF8Control());
	private static final ResourceBundle EN_US_BUNDLE = ResourceBundle.getBundle("Messages", new Locale("en", "US"), new UTF8Control());

	private Messages(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		messageFormat = new MessageFormat("");
	}

	public static Messages get(String locale) {
		if ("vi".equals(locale)) {
			if (viMessages == null) {
				viMessages = new Messages(VI_VN_BUNDLE);
			}
			return viMessages;
		}
		else {
			if (enMessages == null) {
				enMessages = new Messages(EN_US_BUNDLE);
			}
			return enMessages;
		}
	}

	public String loginFailed() {
		return resourceBundle.getString("loginFailed");
	}
}
