package net.xapxinh.center.client.play.header;

import net.xapxinh.center.client.player.locale.PlayLocale;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

class PopupUserMenuView extends PopupPanel implements PopupUserMenuPresenter.Display {
	
	private FlowPanel container;
	private Label lblArrow;
	private final Label lblVnFlag;
	private final Label lblEnFlag;
	private final FlowPanel viPanel;
	private final FlowPanel enPanel;
	private final FocusPanel logoutPanel;
	
	private ScheduleSettingPanel popupMenuControlView;
	
	PopupUserMenuView() {
		super(true, true);
		setStyleName("popup user");
		getElement().setId(getId());
		container = new FlowPanel();
		container.setStyleName("wrapper");
		add(container);
		
		lblArrow = new Label();
		lblArrow.setStyleName("arrow");
		container.add(lblArrow);
		
		Label lblUnderArrow = new Label();
		lblUnderArrow.setStyleName("underarrow");
		container.add(lblUnderArrow);
		
		logoutPanel = new FocusPanel();
		container.add(logoutPanel);
		
		logoutPanel.setStyleName("logout");
		FlowPanel panel = new FlowPanel();
		logoutPanel.setWidget(panel);
		
		Button btnLogout = new Button("");
		btnLogout.addStyleName("btnLogout");
		panel.add(btnLogout);
		
		Label lblLogout = new Label(PlayLocale.getPlayConsts().logout());
		lblLogout.setStyleName("logoutName");
		panel.add(lblLogout);
		
		FlowPanel languageView = new FlowPanel();
		languageView.setStyleName("languages");
		container.add(languageView);
		
		viPanel = new FlowPanel();
		viPanel.setStyleName("vn");
		lblVnFlag = new Label();
		lblVnFlag.setStyleName("vnFlag");
		viPanel.add(lblVnFlag);
		Label lblVi = new Label("Tiếng Việt");
		lblVi.setStyleName("vnName");
		viPanel.add(lblVi);
		languageView.add(viPanel);
		
		enPanel = new FlowPanel();
		enPanel.setStyleName("en");
		lblEnFlag = new Label();
		lblEnFlag.setStyleName("enFlag");
		enPanel.add(lblEnFlag);
		Label lblEn = new Label("English");
		lblEn.setStyleName("enName");
		enPanel.add(lblEn);
		languageView.add(enPanel);
		
		popupMenuControlView = new ScheduleSettingPanel();
		container.add(popupMenuControlView);
	}

	@Override
	public PopupPanel asPopup() {
		return this;
	}

	@Override
	public String getId() {
		return "popup.user";
	}

	@Override
	public ScheduleSettingPanel getScheduleSetting() {
		return this.popupMenuControlView;
	}
	
	@Override
	public Label getlblViFlag() {
		return this.lblVnFlag;
	}
	
	@Override
	public Label getlblEnFlag() {
		return this.lblEnFlag;
	}

	@Override
	public FocusPanel getLogoutPanel() {
		return logoutPanel;
	}
}
