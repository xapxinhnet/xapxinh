package net.xapxinh.center.client.play;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

import net.xapxinh.center.client.core.cookie.CookieService;
import net.xapxinh.center.client.core.util.ClientUtil;
import net.xapxinh.center.client.play.header.PlayHeaderPresenter;
import net.xapxinh.center.client.play.header.PlayHeaderView;
import net.xapxinh.center.client.play.popup.PlayPopupManager;
import net.xapxinh.center.client.player.PlayerPresenter;
import net.xapxinh.center.client.player.PlayerView;
import net.xapxinh.center.client.player.event.RpcFailureEvent;
import net.xapxinh.center.client.player.event.ShowLoadingEvent;
import net.xapxinh.center.client.player.rpc.PlayerService;
import net.xapxinh.center.client.player.token.PlayToken;
import net.xapxinh.center.shared.cookie.Kookie;

/**
 * @author Sann Tran
 */

public class PlayAppControllerImpl implements IPlayAppController {
	
	private HtmlPage htmlPage;
	
	@Inject
	private CookieService cookieService;	
	@Inject
	protected HandlerManager eventBus;
	@Inject
	protected IRpcAsynProvider rpcProvider;
	
	private PlayPopupManager popupManager;
	private PlayerPresenter playerPresenter;
	private PlayHeaderPresenter headerPresenter;
	
	@Inject
	protected PlayerService playerAsyn;
	
	public PlayAppControllerImpl() {
		this.htmlPage = new HtmlPage();
	}
	
	@Override
	public void showMessage(String message, int type) {
		getPopUpManager().showPopupMessage(message, type);
	}
	
	@Override
	public void showLoading(boolean loading) {
		if (loading) {
			htmlPage.getSpinner().setStyleName("loading");
		}
		else {
			htmlPage.getSpinner().setStyleName("normal");
		}
	}
	
	@Override
	public void logout() {
		rpcProvider.getLoginService().logout(new MethodCallback<Kookie>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				eventBus.fireEvent(new RpcFailureEvent(method, exception));
			}

			@Override
			public void onSuccess(Method method, Kookie response) {
				eventBus.fireEvent(new ShowLoadingEvent(false));				
				gotoLoginPage();
			}
		});
	}
	
	@Override
	public void gotoLoginPage() {	
		cookieService.detroyCookie();
		String url = ClientUtil.getUrl(cookieService.getCookie().getLanguage(), "login.html");
		Window.open(url, "_self", "");
	}
	
	@Override
	public void changeLanguage(String language) {	
		cookieService.getCookie().setLanguage(language);
		cookieService.writeCookie(cookieService.getCookie().getDuration());
		String url = ClientUtil.getUrl(cookieService.getCookie().getLanguage(), "play.html");
		url = url + "#" + History.getToken();
		Window.open(url, "_self", "");
	}
	
	public PlayPopupManager getPopUpManager() {
		if (popupManager == null) {
			popupManager = new PlayPopupManager(eventBus, cookieService, rpcProvider);
		}
		return popupManager;
	}
	
	@Override
	public void onClickLogo() {
		getPlayerPresenter().getAlbumListInstance().reset();
		getPlayerPresenter().showAlbumList();
	}

	@Override
	public void showPlayer() {
		getPlayerPresenter().show(htmlPage.getCenter());
	}

	private void setMediaPlayer(Long playerId) {
		getPlayerPresenter().setPlayerId(playerId);
		getHeaderPresenter().setPlayerId(playerId);
	}

	public PlayerPresenter getPlayerPresenter() {
		if (playerPresenter == null) {
			playerPresenter = new PlayerPresenter(rpcProvider.getPlayerService(), eventBus, new PlayerView());
			playerPresenter.go();
		}
		return playerPresenter;
	}
	
	public PlayHeaderPresenter getHeaderPresenter() {
		if (headerPresenter == null) {
			headerPresenter = new PlayHeaderPresenter(playerAsyn, eventBus, new PlayHeaderView());
			headerPresenter.go();
		}
		return headerPresenter;
	}
	
	@Override
	public void go() {
		setMediaPlayer(PlayToken.getPlayerId());
		getHeaderPresenter().show(htmlPage.getHeader());
	}

	@Override
	public void updateSelectedMenutem(String newSelectedMenuItem) {
		getHeaderPresenter().updateSelectedMenutem(newSelectedMenuItem);
	}
}
