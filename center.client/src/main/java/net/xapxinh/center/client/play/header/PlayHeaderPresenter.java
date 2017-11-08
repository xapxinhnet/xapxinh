package net.xapxinh.center.client.play.header;

import net.xapxinh.center.client.play.event.ClickLogoEvent;
import net.xapxinh.center.client.player.AbstractPresenter;
import net.xapxinh.center.client.player.event.MenuItemSelectedEvent;
import net.xapxinh.center.client.player.event.ShowPlayerMenuEvent;
import net.xapxinh.center.client.player.locale.PlayLocale;
import net.xapxinh.center.client.player.rpc.PlayerService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

public class PlayHeaderPresenter extends AbstractPresenter {

	private final IPlayHeaderView display;
	private PopupUserMenuPresenter popupMenuPresenter;
	protected final PlayerService playerAsyn;

	public PlayHeaderPresenter(PlayerService playerAsyn, HandlerManager eventBus, IPlayHeaderView view) {
		super(eventBus);
		display = view;
		this.playerAsyn = playerAsyn;
	}
	@Override
	public void go() {
		display.getBtnLogo().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ClickLogoEvent());
			}
		});
		display.getBtnUserMenu().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getPopupMenuPresenter().show();
			}
		});
		display.getSelectedMenuItem().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ShowPlayerMenuEvent());
			}
		});
		display.getBtnMenu().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ShowPlayerMenuEvent());
			}
		});
	}
	
	private PopupUserMenuPresenter getPopupMenuPresenter() {
		if (popupMenuPresenter == null) {
			popupMenuPresenter = new PopupUserMenuPresenter(playerAsyn, eventBus, new PopupUserMenuView());
			popupMenuPresenter.go();
		}
		return popupMenuPresenter;
	}

	@Override
	public void show(HasWidgets container) {
		container.clear();
		container.add(display.asPanel());
	}
	
	public void setPlayerId(Long playerId) {
		getPopupMenuPresenter().setPlayerModel(playerId);
		display.getBtnUserMenu().setText("P" + playerId);
	}
	
	public void updateSelectedMenutem(String newSelectedMenuItem) {
		if (MenuItemSelectedEvent.ALBUM.equals(newSelectedMenuItem)) {
			display.getSelectedMenuItem().setIconStyleName("icon cloudcd");
			display.getSelectedMenuItem().setText(PlayLocale.getPlayConsts().album());
		}
		else if (MenuItemSelectedEvent.BROWSE.equals(newSelectedMenuItem)) {
			display.getSelectedMenuItem().setIconStyleName("icon browse");
			display.getSelectedMenuItem().setText(PlayLocale.getPlayConsts().file());
		}
		else if (MenuItemSelectedEvent.YOUTUBE.equals(newSelectedMenuItem)) {
			display.getSelectedMenuItem().setIconStyleName("icon youtube");
			display.getSelectedMenuItem().setText(PlayLocale.getPlayConsts().youtube());
		}
	}
}
