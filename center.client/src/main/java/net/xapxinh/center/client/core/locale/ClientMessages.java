package net.xapxinh.center.client.core.locale;

import com.google.gwt.i18n.client.Messages;

/**
 * @author Sann Tran
 */
public interface ClientMessages extends Messages {

	@DefaultMessage("Lỗi ''{0}'': ''{1}''")
	String serverErrorResponse(String status, String message);

	@DefaultMessage("Lỗi kết nối server")
	String serverConnectionError();

}
