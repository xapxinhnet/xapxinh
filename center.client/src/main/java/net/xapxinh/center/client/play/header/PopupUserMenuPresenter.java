package net.xapxinh.center.client.play.header;

import java.util.Date;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

import net.xapxinh.center.client.core.event.ShowMessageEvent;
import net.xapxinh.center.client.core.locale.ClientLocale;
import net.xapxinh.center.client.core.rpc.UserLogOutEvent;
import net.xapxinh.center.client.play.event.ChangeLanguageEvent;
import net.xapxinh.center.client.player.AbstractPresenter;
import net.xapxinh.center.client.player.event.RpcFailureEvent;
import net.xapxinh.center.client.player.event.ShowLoadingEvent;
import net.xapxinh.center.client.player.locale.PlayLocale;
import net.xapxinh.center.client.player.rpc.PlayerService;
import net.xapxinh.center.shared.dto.player.Schedule;

class PopupUserMenuPresenter extends AbstractPresenter {

	public interface Display {
		PopupPanel asPopup();
		String getId();
		ScheduleSettingPanel getScheduleSetting();
		FocusPanel getLogoutPanel();
		Label getlblViFlag();
		Label getlblEnFlag();
	}
	
	private Display display;
	protected Long playerId;
	protected final PlayerService playerAsyn;
	private final DateTimeFormat dateTimeFormat;
	
	PopupUserMenuPresenter(PlayerService playerAsyn, HandlerManager eventBus, Display view) {
		super(eventBus);
		this.display = view;
		this.playerAsyn = playerAsyn;
		dateTimeFormat = DateTimeFormat.getFormat("yyyyMMddhhmma");
	}
	
	public void bind() {
		display.getScheduleSetting().getBtnSave().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateSchedule();
			}
		});
		
		display.getlblViFlag().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ChangeLanguageEvent(ClientLocale.vi_VN));
			}
		});
		
		display.getlblEnFlag().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new ChangeLanguageEvent(ClientLocale.en_EN));
			}
		});
		
		display.getLogoutPanel().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new UserLogOutEvent());
			}
		});
	}
	
	public boolean isShowing() {
		return RootPanel.get(display.getId()) != null;
	}

	@Override
	public void go() {
		bind();
	}

	@Override
	public void show(HasWidgets container) {
		show();
	}
	
	public void show() {
		display.asPopup().show();
		rpcGetSchedule();
	}
	
	private void rpcGetSchedule() {
		if (playerId == null) {
			return;
		}
		playerAsyn.getSchedule(playerId, new MethodCallback<Schedule>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				eventBus.fireEvent(new RpcFailureEvent(method, exception));
			}

			@Override
			public void onSuccess(Method method, Schedule response) {
				eventBus.fireEvent(new ShowLoadingEvent(false));
				Date date = dateTimeFormat.parse(response.getDateTime());
				display.getScheduleSetting().getDateBox().setValue(date);
				display.getScheduleSetting().getTimeBox().setValue(date.getTime());
				display.getScheduleSetting().getListBoxAction().setSelectedIndex(getScheduleActionIndex(response.getAction()));
			}
		});
	}

	public void hide() {
		display.asPopup().hide();
	}
	
	@SuppressWarnings("deprecation")
	private void updateSchedule() {
		Date date = display.getScheduleSetting().getDateBox().getValue();
		
		long timeMillis = display.getScheduleSetting().getTimeBox().getTime();
		Date time = new Date(timeMillis);
		
		date.setHours(time.getHours());
		date.setMinutes(time.getMinutes());
		
		String dateTime = dateTimeFormat.format(date);
		int actionIndex = display.getScheduleSetting().getListBoxAction().getSelectedIndex();
		String action = getScheduleAction(actionIndex);
		
		updateSchedule(action, dateTime);
	}
	
	private String getScheduleAction(int actionListBoxIndex) {
		if (actionListBoxIndex == 1) {
			return Schedule.STOP;
		}
		if (actionListBoxIndex == 2) {
			return Schedule.SHUTDOWN;
		}
		return Schedule.NONE;
	}
	
	private int getScheduleActionIndex(String action) {
		if (Schedule.STOP.equals(action)) {
			return 1;
		}
		if (Schedule.SHUTDOWN.equals(action)) {
			return 2;
		}
		return 0;
	}
	
	protected void updateSchedule(String action, String dateTime) {
		if (playerId == null) {
			return;
		}
		
		Schedule schedule = new Schedule();
		schedule.setAction(action);
		schedule.setDateTime(dateTime);
		
		playerAsyn.updateSchedule(playerId, schedule, new MethodCallback<Schedule>() {
			@Override
			public void onFailure(Method method, Throwable exception) {
				eventBus.fireEvent(new RpcFailureEvent(method, exception));
			}

			@Override
			public void onSuccess(Method method, Schedule response) {
				eventBus.fireEvent(new ShowMessageEvent(PlayLocale.getPlayMessages().scheduleSaved(), ShowMessageEvent.SUCCESS));
			}
		});
	}

	public void setPlayerModel(Long playerId) {
		this.playerId = playerId;
	}
}
