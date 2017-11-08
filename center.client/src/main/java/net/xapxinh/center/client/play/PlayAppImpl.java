package net.xapxinh.center.client.play;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import net.xapxinh.center.client.core.cookie.CookieService;
import net.xapxinh.center.client.play.event.ChangeTokenEvent;
import net.xapxinh.center.client.player.event.ShowLoadingEvent;
import net.xapxinh.center.client.player.event.RpcFailureEvent;
import net.xapxinh.center.client.player.token.PlayToken;
import net.xapxinh.center.shared.cookie.Kookie;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

/**
 * @author Sann Tran
 */
public class PlayAppImpl implements IPlayApp, ValueChangeHandler<String> {
	
	@Inject
	protected IRpcAsynProvider rpcProvider;	
	
	@Inject
	protected CookieService cookieService;	
	
	@Inject
	protected HandlerManager eventBus;
	
	@Inject
	protected IPlayEventHandler eventHandler;
	
	@Inject
	protected IPlayAppController appController;
	
	public PlayAppImpl() {
		History.addValueChangeHandler(this);
	}
	
	@Override
	public void gotoApplication() {		
		appController.go();
		handleHistoryTokenChanged();
	}
	
	@Override
	public void handleHistoryTokenChanged() {
		cookieService.setSourceChangeTokenEvent(false);
		String token = History.getToken();
		
		if (isInvalidToken(token)) {			
			String newToken = PlayToken.newPlayerToken(cookieService.getCookie().getPlayerId());
			eventBus.fireEvent(new ChangeTokenEvent(newToken));
			return;
		}
		
		if (token.startsWith(PlayToken.PLAYER)) {
			appController.showPlayer();
			return;
		}
		
		cookieService.setSourceChangeTokenEvent(true);
		History.newItem(PlayToken.PLAYER);
	}

	private boolean isInvalidToken(String token) {
		return token == null || token.isEmpty() || !isNumber(PlayToken.getTokenPlayerId());
	}

	private boolean isNumber(String playerId) {
		try {
			Long.parseLong(playerId);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}

	public void gotoLoginPage() {
		appController.gotoLoginPage();
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (cookieService.getSourceChangeTokenEvent() == true) {
			handleHistoryTokenChanged();
		}
	}

	@Override
	public void go() {
		eventHandler.bind();
		
		rpcProvider.getLoginService().login(new MethodCallback<Kookie>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				eventBus.fireEvent(new RpcFailureEvent(method, exception));
			}
			@Override
			public void onSuccess(Method method, Kookie response) {
				eventBus.fireEvent(new ShowLoadingEvent(false));
				if (response != null) {
					PlayToken.setPlayerId(response.getPlayerId());
					cookieService.writeCookie(cookieService.getCookie().getDuration());
					gotoApplication();
				}
				else {
					cookieService.detroyCookie();
					gotoLoginPage();
				}
			}
		});
	}
	
	public void show(HasWidgets container) {
	}

	public String getBaseUrl() {
		return GWT.getModuleBaseURL().replaceAll((GWT.getModuleName() + "/"), "");
	}
}
