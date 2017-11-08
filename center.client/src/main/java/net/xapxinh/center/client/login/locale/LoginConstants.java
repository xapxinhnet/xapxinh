package net.xapxinh.center.client.login.locale;

import com.google.gwt.i18n.client.Constants;

/**
 * @author Nguyen Hien
 */
public interface LoginConstants extends Constants {
	
	@DefaultStringValue("Player")
	String playerName();
	
	@DefaultStringValue("Mật khẩu")
	String password();
	
	@DefaultStringValue("Nhớ mật khẩu")
	String remeberPassword();
	
	@DefaultStringValue("Đăng nhập")
	String login();
	
}
