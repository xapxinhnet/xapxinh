package net.xapxinh.center.client.login;

import net.xapxinh.center.client.core.cookie.CookieService;
import net.xapxinh.center.client.core.event.ShowLoadingEvent;
import net.xapxinh.center.client.core.event.ShowMessageEvent;
import net.xapxinh.center.client.core.locale.ClientLocale;
import net.xapxinh.center.client.core.rpc.LoginService;
import net.xapxinh.center.client.core.util.ClientUtil;
import net.xapxinh.center.client.login.locale.LoginLocale;
import net.xapxinh.center.shared.cookie.Crypto;
import net.xapxinh.center.shared.cookie.Kookie;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author Sann Tran
 */
public class LoginPresenter {

	public interface Display {
		SimplePanel asWidget();

		PasswordTextBox getPtbPass();

		Button getBtLogin();

		TextBox getTbUser();

		CheckBox getCheckBoxRemember();

		Label getlblViFlag();

		Label getlblEnFlag();
	}

	private final Display display;
	private final LoginService loginService;
	private final CookieService cookieService;
	private final HandlerManager eventBus;

	public LoginPresenter(CookieService cookie, LoginService loginService, HandlerManager eventBus, Display view) {
		cookieService = cookie;
		this.loginService = loginService;
		display = view;
		this.eventBus = eventBus;
	}

	public void bind() {

		display.getlblViFlag().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LoginApp.changeLanguge(ClientLocale.vi_VN);
			}
		});

		display.getlblEnFlag().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LoginApp.changeLanguge(ClientLocale.en_EN);
			}
		});


		display.getBtLogin().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doLogin();
			}
		});

		display.getTbUser().addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doLogin();
				}
			}
		});

		display.getPtbPass().addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doLogin();
				}
			}
		});

		display.getCheckBoxRemember().addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doLogin();
				}
			}
		});

		autoFillTextBoxUser();

		if (getLanguage().equals(ClientLocale.en_EN)) {
			display.getlblViFlag().setVisible(true);
		}
		else {
			display.getlblEnFlag().setVisible(true);
		}
	}

	public void show(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public void go() {
		bind();
	}

	private void autoFillTextBoxUser() {
		final Kookie cookie = cookieService.getCookie();
		if (cookie.getPlayerId() != null) {
			display.getTbUser().setText(toPlayerName(cookie.getPlayerId()));
		}
	}

	private String toPlayerName(Long playerId) {
		return "P" + playerId;
	}

	private void doLogin() {
		final String playerName = display.getTbUser().getText().trim();
		final String password = display.getPtbPass().getText();
		final boolean rememberPass = display.getCheckBoxRemember().getValue();

		if (!validatePlayerName(playerName)) {
			eventBus.fireEvent(new ShowMessageEvent(LoginLocale.getLoginMessages().fieldInvalid(LoginLocale.getLoginConst().playerName()), ShowMessageEvent.ERROR));
			return;
		}
		if (!validatePlayerPass(password)) {
			eventBus.fireEvent(new ShowMessageEvent(LoginLocale.getLoginMessages().fieldInvalid(LoginLocale.getLoginConst().password()), ShowMessageEvent.ERROR));
			return;
		}
		else {
			try {
				final Long playerId = Long.parseLong(playerName.substring(1));
				login(playerId, password, rememberPass);
			}
			catch (final Exception e) {
				eventBus.fireEvent(new ShowMessageEvent(LoginLocale.getLoginMessages().fieldInvalid(LoginLocale.getLoginConst().playerName()), ShowMessageEvent.ERROR));
			}
		}
	}

	protected void login(Long playerId, String password, final boolean remember) {
		final String hashPass = encryptPass(password);
		if (hashPass == null) {
			return;
		}
		loginService.login(playerId, hashPass, remember, getLanguage(), new MethodCallback<Kookie>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				ClientUtil.handleRpcFailure(method, exception, eventBus);
			}
			@Override
			public void onSuccess(Method method, Kookie response) {
				if (response == null) {
					eventBus.fireEvent(new ShowMessageEvent(LoginLocale.getLoginMessages().loginFailed(), ShowMessageEvent.ERROR));
					return;
				}
				eventBus.fireEvent(new ShowLoadingEvent(false));
				display.getPtbPass().setText("");
				writeClientCookie(response, remember);
				LoginApp.goToControlPage(getLanguage(), cookieService.getCookie().getPlayerId());
			}
		});
	}

	private boolean validatePlayerName(String userName) {
		if (userName.isEmpty() || !userName.startsWith("P")) {
			return false;
		}
		if (userName.length() > 20) {
			return false;
		}
		return true;
	}
	private boolean validatePlayerPass(String password) {
		if (password.length() > 20 ||  password.length() < 1) {
			return false;
		}
		return true;
	}

	private void writeClientCookie(Kookie cookie, boolean rememberPass) {
		long duration = 43200000; // = 1000 * 60 * 60 * 24 * 0.5  // 0.5 day
		if (rememberPass == true) {
			duration = 432000000; // = 1000 * 60 * 60 * 24 * 5  //5 days
		}
		cookie.setDuration(duration);
		cookieService.setCookie(cookie.toCookieValue());
		cookieService.writeCookie(duration);
	}

	private String getLanguage() {
		String language = ClientLocale.vi_VN;
		if (LocaleInfo.getCurrentLocale().getLocaleName().equals(ClientLocale.en_EN)) {
			language = ClientLocale.en_EN;
		}
		return language;
	}

	private String encryptPass(String pass) {
		try {
			return Crypto.getInstance().encrypt(pass);
		} catch (final Exception e) {
			eventBus.fireEvent(new ShowMessageEvent(e.getMessage(), ShowMessageEvent.ERROR));
			return null;
		}
	}
}
