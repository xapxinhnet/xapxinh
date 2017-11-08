package net.xapxinh.center.client.play;

import net.xapxinh.center.client.core.cookie.CookieService;
import net.xapxinh.center.client.core.event.ShowMessageEvent;
import net.xapxinh.center.client.core.event.ShowMessageEventHandler;
import net.xapxinh.center.client.core.rpc.UserLogOutEvent;
import net.xapxinh.center.client.core.rpc.UserLogOutEventHandler;
import net.xapxinh.center.client.core.util.ClientUtil;
import net.xapxinh.center.client.play.event.ChangeLanguageEvent;
import net.xapxinh.center.client.play.event.ChangeLanguageEventHandler;
import net.xapxinh.center.client.play.event.ChangeTokenEvent;
import net.xapxinh.center.client.play.event.ChangeTokenEventHandler;
import net.xapxinh.center.client.play.event.ClickLogoEvent;
import net.xapxinh.center.client.play.event.ClickLogoEventHandler;
import net.xapxinh.center.client.player.event.MenuItemSelectedEvent;
import net.xapxinh.center.client.player.event.MenuItemSelectedEventHandler;
import net.xapxinh.center.client.player.event.RpcFailureEvent;
import net.xapxinh.center.client.player.event.RpcFailureEventHandler;
import net.xapxinh.center.client.player.event.ShowLoadingEvent;
import net.xapxinh.center.client.player.event.ShowLoadingEventHandler;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;

/**
 * @author Sann Tran
 */
public class PlayEventHandlerImpl implements IPlayEventHandler {

	@Inject
	private HandlerManager eventBus;

	@Inject
	private CookieService cookieService;

	@Inject
	private IPlayAppController appController;

	@Override
	public void bind() {

		eventBus.addHandler(RpcFailureEvent.TYPE, new RpcFailureEventHandler() {
			@Override
			public void onRpcFailure(RpcFailureEvent event) {
				doShowLoading(false);
				ClientUtil.handleRpcFailure(event.getMethod(), event.getException(), eventBus);
			}
		});

		eventBus.addHandler(ShowMessageEvent.TYPE, new ShowMessageEventHandler() {
			@Override
			public void onShowMessage(ShowMessageEvent event) {
				doShowLoading(false);
				doShowMessage(event.getMessage(), event.getMessageType());
			}
		});

		eventBus.addHandler(ChangeTokenEvent.TYPE, new ChangeTokenEventHandler() {
			@Override
			public void onChangeToken(ChangeTokenEvent event) {
				if (event.getToken() != null && !event.getToken().equals(History.getToken())) {
					doChangeToken(event.getToken());
				}
			}
		});

		eventBus.addHandler(ChangeLanguageEvent.TYPE, new ChangeLanguageEventHandler() {
			@Override
			public void onChangeLanguage(ChangeLanguageEvent event) {
				doChangeLanguage(event.getLanguage());
			}
		});

		eventBus.addHandler(UserLogOutEvent.TYPE, new UserLogOutEventHandler() {
			@Override
			public void onUserLogOut(UserLogOutEvent event) {
				doLogOutUser();
			}
		});

		eventBus.addHandler(ClickLogoEvent.TYPE, new ClickLogoEventHandler() {
			@Override
			public void onClickLogo(ClickLogoEvent event) {
				appController.onClickLogo();
			}
		});

		eventBus.addHandler(ShowLoadingEvent.TYPE, new ShowLoadingEventHandler() {
			@Override
			public void onShowLoading(ShowLoadingEvent event) {
				System.out.println(event.isLoading());
				doShowLoading(event.isLoading());
			}
		});

		eventBus.addHandler(MenuItemSelectedEvent.TYPE, new MenuItemSelectedEventHandler() {
			@Override
			public void onMenuItemSelected(MenuItemSelectedEvent event) {
				appController.updateSelectedMenutem(event.getMenuItem());
			}
		});
	}

	private void doChangeToken(String token) {
		cookieService.setSourceChangeTokenEvent(true);
		cookieService.setPrevToken(History.getToken());
		History.newItem(token);
	}

	protected void doShowLoading(boolean loading) {
		appController.showLoading(loading);
	}

	protected void doLogOutUser() {
		appController.logout();
	}

	protected void doChangeLanguage(String language) {
		appController.changeLanguage(language);
	}

	protected void doShowMessage(String message, int type) {
		appController.showMessage(message, type);
	}
}
