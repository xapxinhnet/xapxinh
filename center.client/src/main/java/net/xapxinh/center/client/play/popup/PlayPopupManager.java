package net.xapxinh.center.client.play.popup;

import net.xapxinh.center.client.core.cookie.CookieService;
import net.xapxinh.center.client.play.IRpcAsynProvider;

import com.google.gwt.event.shared.HandlerManager;

/**
 * @author Sann Tran
 */
public class PlayPopupManager {
	
	protected final HandlerManager eventBus;
	private PopupMessagePresenter popupMessageInstance;
	
	public PlayPopupManager(HandlerManager eventBus, CookieService cookieService, IRpcAsynProvider playRpcService) {
		this.eventBus = eventBus;
	}

	private PopupMessagePresenter getPopupMessageInstance() {
		if (popupMessageInstance == null) {
			popupMessageInstance = new PopupMessagePresenter(eventBus, new PopupMessageView());
			popupMessageInstance.go();
		}
		return popupMessageInstance;
	}
	
	public void showPopupMessage(String message, int type) {
		getPopupMessageInstance().show(message, type);
	}
}
