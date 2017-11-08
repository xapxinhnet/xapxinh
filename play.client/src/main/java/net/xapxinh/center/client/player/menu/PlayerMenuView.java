package net.xapxinh.center.client.player.menu;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;

import net.xapxinh.center.client.player.locale.PlayLocale;

public class PlayerMenuView extends FlowPanel implements PlayerMenuPresenter.Display {
	
	private final CheckBox toggle;
	private final MenuItemView albumMenuItem;
	private final MenuItemView fileMenuItem;
	private final MenuItemView youtubeMenuItem;
	private final MenuItemView playlistMenuItem;
	
	public PlayerMenuView() {		
		setStyleName("menu");
		
		toggle = new CheckBox("");
		toggle.setStyleName("toggle");
		add(toggle);
		
		toggle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (toggle.getValue()) {
					show();
				}
				else {
					hide();
				}
			}
		});
		
		albumMenuItem = new MenuItemView();
		albumMenuItem.setIconStyleName("icon album");
		albumMenuItem.setText(PlayLocale.getPlayConsts().album());
		add(albumMenuItem);
		
		fileMenuItem = new MenuItemView();
		fileMenuItem.setIconStyleName("icon browse");
		fileMenuItem.setText(PlayLocale.getPlayConsts().file());
		add(fileMenuItem);
		
		youtubeMenuItem = new MenuItemView();
		youtubeMenuItem.setIconStyleName("icon youtube");
		youtubeMenuItem.setText(PlayLocale.getPlayConsts().youtube());
		add(youtubeMenuItem);
		
		playlistMenuItem = new MenuItemView();
		playlistMenuItem.setIconStyleName("icon playlist");
		playlistMenuItem.setText(PlayLocale.getPlayConsts().playlist());
		add(playlistMenuItem);
	}
	
	@Override
	public FlowPanel asPanel() {
		return this;
	}
	
	@Override
	public MenuItemView getAlbumMenuItem() {
		return this.albumMenuItem;
	}
	
	@Override
	public MenuItemView getFileMenuItem() {
		return this.fileMenuItem;
	}
	
	@Override
	public MenuItemView getYoutubeMenuItem() {
		return this.youtubeMenuItem;
	}
	
	@Override
	public MenuItemView getPlaylistMenuItem() {
		return this.playlistMenuItem;
	}

	@Override
	public boolean isShowed() {
		return getStyleName().contains("show");
	}
	
	@Override
	public void hide() {
		toggle.setValue(false);
		setStyleName("menu collapsed");
	}
	@Override
	public void show() {
		toggle.setValue(true);
		setStyleName("menu expanded");
	}
}