package net.xapxinh.center.client.login;

import net.xapxinh.center.client.core.event.ShowMessageEvent;
import net.xapxinh.center.client.core.event.ShowMessageEventHandler;
import net.xapxinh.center.client.core.rpc.LoginService;

import com.google.gwt.event.shared.HandlerManager;

public class LoginEventHandler {

	final HandlerManager eventBus;
	final LoginService loginAsyn;

	final LoginAppController appController;

	public LoginEventHandler(HandlerManager eventBus, LoginService loginAsyn, LoginAppController appController) {

		this.eventBus = eventBus;
		this.loginAsyn = loginAsyn;
		this.appController = appController;
	}

	void bind() {
		eventBus.addHandler(ShowMessageEvent.TYPE, new ShowMessageEventHandler() {
			@Override
			public void onShowMessage(ShowMessageEvent event) {
				doShowMessage(event.getMessage(), event.getMessageType());
			}
		});
	}

	protected void doShowMessage(String message, int messageType) {
		appController.showPopupMessage(message, messageType);
	}
}
