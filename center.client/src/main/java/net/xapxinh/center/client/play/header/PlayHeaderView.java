package net.xapxinh.center.client.play.header;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

import net.xapxinh.center.client.player.locale.PlayLocale;
import net.xapxinh.center.client.player.menu.MenuItemView;

public class PlayHeaderView extends FlowPanel implements IPlayHeaderView {

	private final Button btnLogo;
	private final Button btnMenu;
	private final MenuItemView selectedMenuItem;
	private final Button btnUserMenu;

	public PlayHeaderView() {
		
		setHeight("50px");
		
		btnLogo = new Button("");
		btnLogo.addStyleName("logo");
		add(btnLogo);
		
		btnMenu = new Button("");
		btnMenu.addStyleName("menu");
		add(btnMenu);
		
		selectedMenuItem = new MenuItemView();
		selectedMenuItem.setText(PlayLocale.getPlayConsts().album());
		selectedMenuItem.setIconStyleName("icon cloudcd");
		add(selectedMenuItem);
		
		btnUserMenu = new Button();
		add(btnUserMenu);
		btnUserMenu.addStyleName("userMenu");
	}
	
	@Override
	public FlowPanel asPanel() {
		return this;
	}
	
	@Override
	public Button getBtnLogo() {
		return this.btnLogo;
	}
	
	@Override
	public Button getBtnMenu() {
		return this.btnMenu;
	}
	
	@Override
	public MenuItemView getSelectedMenuItem() {
		return this.selectedMenuItem;
	}

	@Override
	public Button getBtnUserMenu() {
		return btnUserMenu;
	}
}
