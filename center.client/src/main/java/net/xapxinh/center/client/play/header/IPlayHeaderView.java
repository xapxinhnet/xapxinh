package net.xapxinh.center.client.play.header;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

import net.xapxinh.center.client.player.menu.MenuItemView;

public interface IPlayHeaderView {
	Button getBtnUserMenu();
	FlowPanel asPanel();
	Button getBtnLogo();
	Button getBtnMenu();
	MenuItemView getSelectedMenuItem();
}
