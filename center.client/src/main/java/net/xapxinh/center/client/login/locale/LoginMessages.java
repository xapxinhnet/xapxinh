package net.xapxinh.center.client.login.locale;

import com.google.gwt.i18n.client.Messages;

/**
 * @author Sann Tran
 */
public interface LoginMessages extends Messages {

	@DefaultMessage("''{0}'' không hợp lệ")
	String fieldInvalid(String field);

	@DefaultMessage("Player/Mật khẩu không đúng")
	String loginFailed();
}
