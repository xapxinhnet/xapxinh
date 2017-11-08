package net.xapxinh.center.client.play;

import net.xapxinh.center.client.player.IPresenter;

/**
 * @author Sann Tran
 */
public interface IPlayApp extends IPresenter {
	void gotoApplication();
	void gotoLoginPage(); 
	void handleHistoryTokenChanged();  
}
