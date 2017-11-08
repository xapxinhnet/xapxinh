package net.xapxinh.center.client.core.rpc;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Sann Tran
 */
public interface UserLogOutEventHandler extends EventHandler {
	void onUserLogOut(UserLogOutEvent event);
}