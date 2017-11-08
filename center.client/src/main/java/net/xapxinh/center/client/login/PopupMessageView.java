package net.xapxinh.center.client.login;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class PopupMessageView extends PopupPanel implements PopupMessagePresenter.Display {

	private final FlowPanel container;
	private final Button btnImg;
	private final Label lblMessage;

	public PopupMessageView() {
		super(true, false);
		setStyleName("popup message");

		container = new FlowPanel();
		container.setStyleName("container");
		setWidget(container);

		btnImg = new Button("");
		container.add(btnImg);
		btnImg.addStyleName("icon");
		
		FlowPanel messageWrapper = new FlowPanel();
		messageWrapper.setStyleName("messageWrapper");
		container.add(messageWrapper);
		
		lblMessage = new Label("");
		messageWrapper.add(lblMessage);
		lblMessage.setStyleName("text");
	}

	@Override
	public PopupPanel asPopup() {
		return this;
	}

	@Override
	public Button getBtnImg() {
		return btnImg;
	}

	@Override
	public Label getLblMessage() {
		return lblMessage;
	}

	@Override
	public FlowPanel getContainer() {
		return container;
	}
}
