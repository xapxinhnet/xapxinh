package net.xapxinh.center.client.core.cookie;

import java.util.Date;

import net.xapxinh.center.shared.cookie.Kookie;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

public class CookieServiceImpl implements CookieService {

	private Kookie cookie;
	private String prevToken;
	private boolean sourceChangeTokenEvent = false;

	@Override
	public Kookie getCookie() {
		if (cookie == null) {
			cookie = createCookie();
		}
		return cookie;
	}

	@Override
	public void setCookie(String cookieValue) {
		cookie = Kookie.fromRawCookieValue(cookieValue);
	}

	private static Kookie createCookie() {
		return Kookie.fromRawCookieValue(Cookies.getCookie(Kookie.XAPXINHSESSION));
	}

	@Override
	public void writeCookie(Long duration) {
		final String cookiesValue = getCookie().toCookieValue();
		final Date expires = new Date(System.currentTimeMillis() + duration);
		Cookies.setCookie(Kookie.XAPXINHSESSION, cookiesValue, expires, getDomain(), "/", false);
	}

	private String getDomain() {
		return Window.Location.getHostName();
	}

	@Override
	public String getPrevToken() {
		return prevToken;
	}

	@Override
	public void setPrevToken(String token) {
		this.prevToken = token;
	}

	@Override
	public void detroyCookie() {
		cookie = null;
		final Date expires = new Date(System.currentTimeMillis() - 31536000000L); // after 1 year
		Cookies.setCookie(Kookie.XAPXINHSESSION, "expired", expires, getDomain(), "/", false);
	}

	@Override
	public boolean getSourceChangeTokenEvent() {
		return this.sourceChangeTokenEvent;
	}

	@Override
	public void setSourceChangeTokenEvent(boolean source) {
		this.sourceChangeTokenEvent = source;
	}
}
