package net.xapxinh.center.client.login;

import net.xapxinh.center.client.core.event.ShowMessageEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * @author Sann Tran
 */
public class PopupMessagePresenter {

	public interface Display {

		public Button getBtnImg();

		public Label getLblMessage();

		PopupPanel asPopup();

		FlowPanel getContainer();
	}

	private final Display display;
	private Timer timer = null;
	private int timeLeft;

	public PopupMessagePresenter(HandlerManager eventBus, Display display) {
		this.display = display;
	}

	public void bind() {
		display.getBtnImg().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.asPopup().hide();
			}
		});
	}

	private void showMessage5s(String message, int type) {
		if (ShowMessageEvent.NORMAL == type) {
			display.asPopup().setStyleName("popup message normal");
		}
		else if (ShowMessageEvent.ERROR == type) {
			display.asPopup().setStyleName("popup message error");
		}
		else if (ShowMessageEvent.SUCCESS == type) {
			display.asPopup().setStyleName("popup message success");
		}
		else if (ShowMessageEvent.WARNING == type) {
			display.asPopup().setStyleName("popup message warning");
		}
		display.getLblMessage().setText(message);
		display.asPopup().show();

		timeLeft = 5;
		if (timer != null) {
			timer.cancel();
			timer.run();
			timer.scheduleRepeating(1000);
		}
		else {
			timer = new Timer() {
				@Override
				public void run() {
					if (timeLeft == 0) {
						display.asPopup().hide();
						cancel();
					}
					timeLeft--;
				}
			};
			timer.scheduleRepeating(1000);
		}
	}

	public void showNormalMessage(String message) {
		showMessage5s(message, ShowMessageEvent.NORMAL);
	}

	public void showWarningMessage(String message) {
		showMessage5s(message, ShowMessageEvent.WARNING);
	}

	public void showSuccessMessage(String message) {
		showMessage5s(message, ShowMessageEvent.SUCCESS);
	}

	public void showErrorMessage(String message) {
		showMessage5s(message, ShowMessageEvent.ERROR);
	}

	public void go() {
		bind();
	}

	public void show(HasWidgets container) {

	}
	
	public void show(String message, int type) {
		showMessage5s(message, type);
	}
	
	public void show(String message, int type, String additionalStyle) {
		showMessage5s(message, type);
		display.asPopup().addStyleName(additionalStyle);
	}
}
