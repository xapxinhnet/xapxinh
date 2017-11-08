package net.xapxinh.center.client.core.util;

import org.fusesource.restygwt.client.Method;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

import net.xapxinh.center.client.core.event.ShowMessageEvent;
import net.xapxinh.center.client.core.locale.ClientLocale;
import net.xapxinh.center.client.core.rpc.UserLogOutEvent;

public final class ClientUtil {

	private ClientUtil() {
		// prevent installing
	}

	public static String getUrl(String language, String page) {
		String url = GWT.getModuleBaseURL().replaceFirst((GWT.getModuleName() + "/"), "");
		url = url + page + "?locale=" + language;
		return url;
	}

	public static void handleRpcFailure(Method method, Throwable exception, HandlerManager eventBus) {
		String message = "";
		final int statusCode = method.getResponse().getStatusCode();
		try {
			final JSONObject jsonValue = (JSONObject) JSONParser.parseStrict(method.getResponse().getText());
			final String status = jsonValue.get("status").toString();
			if (status.contains(Response.SC_UNAUTHORIZED + "")) {
				eventBus.fireEvent(new UserLogOutEvent());
			}
			else {
				message = jsonValue.get("message").toString();
				eventBus.fireEvent(new ShowMessageEvent(message, ShowMessageEvent.ERROR));
			}
		}
		catch (final Exception e) {
			if (statusCode == 0 || "".equals(method.getResponse().getStatusText())) {
				eventBus.fireEvent(new ShowMessageEvent(ClientLocale.getClientMessage().serverConnectionError(), ShowMessageEvent.ERROR));
			}
			else {
				message = ClientLocale.getClientMessage().serverErrorResponse(statusCode + "", method.getResponse().getStatusText());
				eventBus.fireEvent(new ShowMessageEvent(message, ShowMessageEvent.ERROR));
			}
		}
	}
}
