package net.xapxinh.center.server.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.xapxinh.center.server.AppContext;
import net.xapxinh.center.server.api.player.PlayerConnection;
import net.xapxinh.center.shared.cookie.Kookie;

import org.apache.log4j.Logger;

public class SessionCollection implements HttpSessionListener {

	static Logger logger = Logger.getLogger(SessionCollection.class.getName());

	private static List<HttpSession> activeSessions = new ArrayList<HttpSession>();

	private static final int INACTIVE_INTERVAL = 1 * 60 * 60; // 1 hour

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		final HttpSession session = event.getSession();
		registerSession(session);
	}

	public static void registerSession(HttpSession session) {
		if (!isSessionExisted(session)) {
			getActiveSessions().add(session);
			logger.info("Add new session into collection - Session id: " + session.getId());
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		final HttpSession session = event.getSession();
		getActiveSessions().remove(session);
		logger.info("Deduct session from collection - Session id: "
				+ session.getId());
	}

	private static HttpSession getSession(Long playerId, String playerPass) {
		if (playerId != null && getActiveSessions() != null && !getActiveSessions().isEmpty()) {
			for (final HttpSession ss : getActiveSessions()) {
				if (playerId.equals(ss.getAttribute("playerId")) &&
						(playerPass + "").equals(ss.getAttribute("playerPass"))) {
					return ss;
				}
			}
		}
		return null;
	}

	public static synchronized List<HttpSession> getActiveSessions() {
		if (activeSessions == null) {
			activeSessions = new ArrayList<HttpSession>();
		}
		return activeSessions;
	}

	private static synchronized boolean isSessionExisted(HttpSession session) {
		for (final HttpSession ss : getActiveSessions()) {
			if (ss.getId().equals(session.getId())) {
				return true;
			}
		}
		return false;
	}

	public static void detroySession(HttpSession httpSession) {
		httpSession.invalidate();
	}

	public static HttpSession getSession(Kookie kookie, HttpServletRequest request) {
		if (kookie.getPlayerId() == null || kookie.getPlayerPass() == null) {
			return null;
		}
		HttpSession httpSession = getSession(kookie.getPlayerId(), kookie.getPlayerPass());
		if (httpSession == null) {
			final PlayerConnection conn = AppContext.getPlayerConnectionPool().getPlayerConnection(kookie.getPlayerId());
			final String pass = kookie.getPlayerPass() + "";
			if (conn != null && (pass.equals(conn.getPass()) || pass.equals(conn.getPassword()))) {
				httpSession = registerSession(kookie.getPlayerId(), kookie.getPlayerPass(), request);
			}
			else {
				return null;
			}
		}
		return httpSession;
	}

	public static HttpSession registerSession(Long playerId, String playerPass, HttpServletRequest request) {
		final HttpSession httpSession = request.getSession(true);
		httpSession.setAttribute("playerId", playerId);
		httpSession.setAttribute("playerPass", playerPass);
		httpSession.setMaxInactiveInterval(INACTIVE_INTERVAL);
		registerSession(httpSession);
		return httpSession;
	}
}
