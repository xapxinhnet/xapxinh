package net.xapxinh.center.server.webmvc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.xapxinh.center.shared.cookie.Kookie;

public class HttpServletRequestUtil {
	static Kookie getCookie(HttpServletRequest httpServletRequest) {
		Cookie[] cookies = httpServletRequest.getCookies();
		Cookie kookie = null;
		if (cookies == null || cookies.length == 0) {
			return new Kookie();
		}
		for (Cookie cookie : cookies) {
			if (Kookie.XAPXINHSESSION.equalsIgnoreCase(cookie.getName())) {
				kookie = cookie;
			}
		}
		if (kookie == null) {
			return new Kookie();
		}
		String cookieValue = kookie.getValue();
		return Kookie.fromEncodedCookieValue(cookieValue);
	}
	
	static String getLocale(HttpServletRequest httpServletRequest) {
		Kookie kookie = getCookie(httpServletRequest);
		return kookie.getLanguage();
	}
}
