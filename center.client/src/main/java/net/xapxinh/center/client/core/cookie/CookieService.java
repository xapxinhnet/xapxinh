package net.xapxinh.center.client.core.cookie;


import net.xapxinh.center.shared.cookie.Kookie;

public interface CookieService {
	
	public Kookie getCookie() ;

	public void writeCookie(Long duration);	

	public String getPrevToken();

	public void setPrevToken(String token);

	public void detroyCookie();

	public boolean getSourceChangeTokenEvent();
	
	public void setSourceChangeTokenEvent(boolean source);

	void setCookie(String cookieValue);
	
}
