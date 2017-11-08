package net.xapxinh.center.client.login;

import java.util.HashMap;

import net.xapxinh.center.client.core.cookie.CookieService;
import net.xapxinh.center.client.core.cookie.CookieServiceImpl;
import net.xapxinh.center.client.core.event.ShowLoadingEvent;
import net.xapxinh.center.client.core.locale.ClientLocale;
import net.xapxinh.center.client.core.rpc.LoginService;
import net.xapxinh.center.client.core.util.ClientUtil;
import net.xapxinh.center.shared.cookie.Kookie;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class LoginApp implements ValueChangeHandler<String> {

	private final LoginService loginService;
	private final HandlerManager eventBus;
	protected CookieService cookieService;
	private LoginAppController appController;
	private LoginEventHandler eventHandler;

	public LoginApp(LoginService loginService, HandlerManager eventBus) {

		this.loginService = loginService;
		this.eventBus = eventBus;

		History.addValueChangeHandler(this);
		cookieService = new CookieServiceImpl();
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		// does not handle any thing
	}

	public void go() {
		loginService.login(new MethodCallback<Kookie>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				ClientUtil.handleRpcFailure(method, exception, eventBus);
			}
			@Override
			public void onSuccess(Method method, Kookie response) {
				eventBus.fireEvent(new ShowLoadingEvent(false));
				if (response != null) {
					cookieService.writeCookie(response.getDuration());
					goToControlPage(ClientLocale.vi_VN, cookieService.getCookie().getPlayerId());
				}
				else {
					cookieService.detroyCookie();
					gotoLogin();
				}
			}
		});
	}

	static void goToControlPage(String language, Long playerId) {
		final String page = "play.html";
		String url = ClientUtil.getUrl(language, page);
		if (playerId != null) {
			url = url + "#player_" + playerId;
		}
		Window.open(url, "_self", "");
	}

	/**
	 * Change language current from vi to en or opposite.
	 *
	 * @author ChatHien
	 * @param language the locale of application
	 */
	static void changeLanguge(String language) {

		final String page = "login.html";
		final String url = ClientUtil.getUrl(language, page);
		Window.open(url, "_self", "");
	}

	private void gotoLogin() {
		appController = new LoginAppController(cookieService, eventBus, loginService);
		eventHandler = new LoginEventHandler(eventBus, loginService, appController);
		eventHandler.bind();
		appController.showLogin(RootPanel.get("center"));
	}

	static HashMap<String, String> getParamsMap(HandlerManager eventBus, String procedure) {
		final HashMap<String, String> params = new HashMap<String, String>();
		params.put("rpcCommand", procedure);
		eventBus.fireEvent(new ShowLoadingEvent(true));
		return params;

	}
}
