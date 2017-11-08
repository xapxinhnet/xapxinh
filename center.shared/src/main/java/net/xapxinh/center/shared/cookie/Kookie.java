package net.xapxinh.center.shared.cookie;

import java.io.Serializable;

public class Kookie implements Serializable {

	private static final long serialVersionUID = 7419640619294967253L;

	public final static String XAPXINHSESSION = "XAPXINHSESSION";

	private final static String RAW_COOKIE_DLM = "&&";
	private final static String RAW_VALUE_DLM = "::";

	private final static String ENCODED_COOKIE_DLM = "%26%26";
	private final static String ENCODED_VALUE_DLM = "%3A%3A";

	public static final String PLAYER_ID = "playerId";
	public static final String PLAYER_PASS = "playerPass";
	public static final String LANGUAGE = "language";
	public static final String DURATION = "duration";

	private Long playerId;
	private String playerPass;
	private String language;
	private Long duration;

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerPass() {
		return playerPass;
	}

	public void setPlayerPass(String playerPass) {
		this.playerPass = playerPass;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public static Kookie fromRawCookieValue(String cookieValue) {
		return fromCookieValue(cookieValue, RAW_COOKIE_DLM, RAW_VALUE_DLM);
	}

	public static Kookie fromEncodedCookieValue(String cookieValue) {
		return fromCookieValue(cookieValue, ENCODED_COOKIE_DLM, ENCODED_VALUE_DLM);
	}

	private static Kookie fromCookieValue(String cookieValue, String cookieDlm, String valueDlm) {
		Kookie cookie = new Kookie();
		if (cookieValue == null) {
			return cookie;
		}
		final String[] pairs = cookieValue.split(cookieDlm);
		for (final String pair : pairs) {
			final String[] map = pair.split(valueDlm);
			if (map.length == 2) {
				cookie = setValue(cookie, map[0], map[1]);
			}
		}
		return cookie;
	}

	private static Kookie setValue(Kookie cookie, String key, String value) {
		if (PLAYER_ID.equals(key)) {
			cookie.setPlayerId(Long.parseLong(value));
		}
		else if (PLAYER_PASS.equals(key)) {
			cookie.setPlayerPass(value);
		}
		else if (LANGUAGE.equals(key)) {
			cookie.setLanguage(value);
		}
		else if (DURATION.equals(key)) {
			cookie.setDuration(Long.parseLong(value));
		}
		return cookie;
	}

	public String toCookieValue() {
		String cookieValue = "";

		if (playerId != null) {
			cookieValue = cookieValue + RAW_COOKIE_DLM + PLAYER_ID + RAW_VALUE_DLM + playerId;
		}
		if (playerPass != null) {
			cookieValue = cookieValue + RAW_COOKIE_DLM + PLAYER_PASS + RAW_VALUE_DLM + playerPass;
		}
		if (language != null) {
			cookieValue = cookieValue + RAW_COOKIE_DLM + LANGUAGE + RAW_VALUE_DLM + language;
		}
		if (duration != null) {
			cookieValue = cookieValue + RAW_COOKIE_DLM + DURATION + RAW_VALUE_DLM + duration;
		}
		return cookieValue;
	}
}
