package net.xapxinh.center.client.play;

/**
 * @author Sann Tran
 */

public interface IPlayAppController {
	
	public void go();
	
	public void showMessage(String message, int type);

	public void showLoading(boolean loading);

	public void logout();

	public void onClickLogo();

	public void gotoLoginPage();

	public void changeLanguage(String language);
	
	public void showPlayer();

	public void updateSelectedMenutem(String menuItem);
}
