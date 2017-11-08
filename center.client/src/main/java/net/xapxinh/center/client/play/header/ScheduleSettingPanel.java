package net.xapxinh.center.client.play.header;

import java.util.Date;

import net.xapxinh.center.client.player.locale.PlayLocale;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;

class ScheduleSettingPanel extends FlowPanel{

	private final Label lblIcon;
	private final Label lblText;
	private final ListBox listBoxActions;
	private final DateBox dateBox;
	private final TimeBox timePicker;
	private final Button btnSave;

	ScheduleSettingPanel() {
		setStyleName("schedule");

		final FlowPanel actionPanel = new FlowPanel();
		actionPanel.setStyleName("action");
		add(actionPanel);

		lblIcon = new Label();
		lblIcon.setStyleName("icon");
		actionPanel.add(lblIcon);

		lblText = new Label(PlayLocale.getPlayConsts().schedule());
		lblText.setStyleName("name");
		actionPanel.add(lblText);

		listBoxActions = new ListBox();
		listBoxActions.addStyleName("option");
		listBoxActions.addItem(PlayLocale.getPlayConsts().none());
		listBoxActions.addItem(PlayLocale.getPlayConsts().stop());
		listBoxActions.addItem(PlayLocale.getPlayConsts().shutdown());
		actionPanel.add(listBoxActions);

		final FlowPanel dateTimePanel = new FlowPanel();
		dateTimePanel.setStyleName("dateTime");

		final Date now = new Date();
		final DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
	    dateBox = new DateBox();
	    dateBox.setStyleName("dateBox");
	    dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
	    dateBox.getDatePicker().setYearArrowsVisible(true);
	    dateBox.setValue(now);
	    dateTimePanel.add(dateBox);

		timePicker = new TimeBox(now, true);
		timePicker.setStyleName("timePickerWrapper");
		dateTimePanel.add(timePicker);

		btnSave = new Button("");
		btnSave.addStyleName("save");
		dateTimePanel.add(btnSave);

		add(dateTimePanel);
	}

	public ListBox getListBoxAction() {
		return this.listBoxActions;
	}

	public HorizontalPanel getShutdownHPanel() {
		return null;
	}

	public HorizontalPanel getPauseHPanel() {
		return null;
	}

	public Button getBtnSave() {
		return this.btnSave;
	}

	public TimeBox getTimeBox() {
		return this.timePicker;
	}

	public DateBox getDateBox() {
		return this.dateBox;
	}
}
