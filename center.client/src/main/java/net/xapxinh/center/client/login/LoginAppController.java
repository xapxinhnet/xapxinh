package net.xapxinh.center.client.login;

import net.xapxinh.center.client.core.cookie.CookieService;
import net.xapxinh.center.client.core.rpc.LoginService;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class LoginAppController {

	private final HandlerManager eventBus;
	private final LoginService loginAsyn;
	private final CookieService cookieService;

	private LoginPresenter loginInstance;
	private PopupMessagePresenter popupMessageInstance;

	public LoginAppController(CookieService cookieService, HandlerManager eventBus, LoginService loginAsyn) {
		this.eventBus = eventBus;
		this.loginAsyn = loginAsyn;
		this.cookieService = cookieService;
	}

	private PopupMessagePresenter getPopupMessageInstance() {
		if (popupMessageInstance == null) {
			popupMessageInstance = new PopupMessagePresenter(eventBus, new PopupMessageView());
			popupMessageInstance.go();
		}
		return popupMessageInstance;
	}

	private LoginPresenter getLoginInstance() {
		if (loginInstance == null) {
			loginInstance = new LoginPresenter(cookieService, loginAsyn, eventBus, new LoginView());
			loginInstance.go();
		}
		return loginInstance;
	}

	public void showPopupMessage(String message, int type) {
		getPopupMessageInstance().show(message, type, "login");
	}

	public void showLogin(HasWidgets container) {
		getLoginInstance().show(container);
	}
}
