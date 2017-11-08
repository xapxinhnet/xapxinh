package net.xapxinh.center.client.login;

import net.xapxinh.center.client.login.locale.LoginLocale;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author Sann Tran
 */
public class LoginView extends SimplePanel implements LoginPresenter.Display {
	
	private final TextBox tbUser;
	private final PasswordTextBox ptbPass;
	private final Button btnLogin;
	private final CheckBox checkBoxRemember;
	private final Label lblViFlag;
	private final Label lblEnFlag;
	

	public LoginView() {

		this.setStyleName("login_form");

		FlowPanel containerPanel = new FlowPanel();
		setWidget(containerPanel);
		containerPanel.setSize("250px", "300px");
		containerPanel.setStyleName("container");
		
		SimplePanel sPanelLogo = new SimplePanel();
		containerPanel.add(sPanelLogo);
		sPanelLogo.setStyleName("logo");
		sPanelLogo.setSize("150px", "70px");
		
		FlowPanel topPanel = new FlowPanel();
		containerPanel.add(topPanel);
		topPanel.addStyleName("login_language");
		
		lblViFlag = new Label();
		lblViFlag.setStyleName("vnFlag");
		lblViFlag.setVisible(false);
		topPanel.add(lblViFlag);
		lblViFlag.setSize("24px", "30px");
		
		lblEnFlag = new Label();
		lblEnFlag.setStyleName("enFlag");
		lblEnFlag.setVisible(false);
		topPanel.add(lblEnFlag);
		lblEnFlag.setSize("24px", "30px");

		Label lblUser = new Label(LoginLocale.getLoginConst().playerName());
		lblUser.setStyleName("user");
		containerPanel.add(lblUser);

		tbUser = new TextBox();
		tbUser.setWidth("240px");
		containerPanel.add(tbUser);

		Label lblPass = new Label(LoginLocale.getLoginConst().password());
		lblPass.setStyleName("password");
		containerPanel.add(lblPass);

		ptbPass = new PasswordTextBox();
		ptbPass.setWidth("240px");
		containerPanel.add(ptbPass);

		checkBoxRemember = new CheckBox(LoginLocale.getLoginConst().remeberPassword());
		checkBoxRemember.setStyleName("remember");
		containerPanel.add(checkBoxRemember);
		
		btnLogin = new Button(LoginLocale.getLoginConst().login());
		btnLogin.addStyleName("submit");
		containerPanel.add(btnLogin);
		btnLogin.setSize("115px", "38px");
	}

	@Override
	public PasswordTextBox getPtbPass() {
		return this.ptbPass;
	}

	@Override
	public Button getBtLogin() {
		return this.btnLogin;
	}

	@Override
	public TextBox getTbUser() {
		return this.tbUser;
	}

	@Override
	public SimplePanel asWidget() {
		return this;
	}

	@Override
	public CheckBox getCheckBoxRemember() {
		return this.checkBoxRemember;
	}

	@Override
	public Label getlblViFlag() {
		return this.lblViFlag;
	}

	@Override
	public Label getlblEnFlag() {
		return this.lblEnFlag;
	}

}
