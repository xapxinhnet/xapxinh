package net.xapxinh.center.server.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import net.xapxinh.center.server.api.player.PlayerConnection;
import net.xapxinh.center.server.entity.Player;
import net.xapxinh.center.server.exception.LoginFailedException;
import net.xapxinh.center.server.exception.PlayerConnectException;
import net.xapxinh.center.server.exception.UnknownPlayerException;
import net.xapxinh.center.server.listener.SessionCollection;
import net.xapxinh.center.server.locale.Messages;
import net.xapxinh.center.server.locale.PlayMessages;
import net.xapxinh.center.server.persistence.service.IPlayerPersistenceService;
import net.xapxinh.center.server.service.player.PlayerConnectionPool;
import net.xapxinh.center.shared.cookie.Kookie;

@RestController
@EnableWebMvc
public class LoginServiceController {

	@Autowired
	private PlayerConnectionPool playerConnectionPool;
	@Autowired
	private IPlayerPersistenceService playerPersistenceService;

	private static final  Logger LOGGER = Logger.getLogger(LoginServiceController.class.getName());

	@RequestMapping(
			value = "kookies/logout",
			method = RequestMethod.POST,
			produces = "application/json; charset=utf-8")
	@ResponseBody
	public Kookie logout(HttpServletRequest request) {
		final Kookie kookie = HttpServletRequestUtil.getCookie(request);
		HttpSession session = request.getSession(false);
		if (session != null) {
			LOGGER.info("User logout. Player id: " + kookie.getPlayerId() + " Session id: " + session.getId());
		}
		else {
			LOGGER.info("User logout. Player id: " + kookie.getPlayerId() + " Session is null");
		}
		SessionCollection.detroySession(session);
		return new Kookie();
	}

	@RequestMapping(
			value = "kookies/login",
			params = {},
			method = RequestMethod.POST,
			produces = "application/json; charset=utf-8")
	@ResponseBody
	public Kookie login(HttpServletRequest request) {
		final Kookie kookie = HttpServletRequestUtil.getCookie(request);
		LOGGER.info("Login using kookie. Player id: " + kookie.getPlayerId() + ". Player pass " + kookie.getPlayerPass());
		if (SessionCollection.getSession(kookie, request) != null) {
			LOGGER.info("Login successfully. Session found in session collection");
			return kookie;
		}
		else {
			LOGGER.info("Login unsuccessfully. No session found in session collection.");
			return null;
		}
	}

	@RequestMapping(
			value = "kookies/login",
			params = {"playerId", "hashPass", "remember", "language"},
			method = RequestMethod.POST,
			produces = "application/json; charset=utf-8")
	@ResponseBody
	public Kookie login(@RequestParam("playerId") Long playerId,
			@RequestParam("hashPass") String hashPass,
			@RequestParam("remember") boolean remember,
			@RequestParam("language") String language, HttpServletRequest request) throws PlayerConnectException, LoginFailedException, UnknownPlayerException {

		request.setAttribute("locale", language);

		LOGGER.info("Login using password. Player id: " + playerId);
		if (!isValidPlayerId(playerId)) {
			LOGGER.info("Login unsuccessfully. User name is invalid");
			throw new UnknownPlayerException(playerId + "");
		}
		getPlayer(playerId, hashPass);
		final HttpSession session = SessionCollection.registerSession(playerId, hashPass, request);
		LOGGER.info("Login successfully. Player id: " + playerId + ". Session id: " + session.getId());
		final Kookie cookie = new Kookie();
		cookie.setPlayerId(playerId);
		cookie.setPlayerPass(hashPass);
		cookie.setLanguage(language);
		return cookie;
	}

	private Player getPlayer(Long playerId, String hashPass) throws LoginFailedException, PlayerConnectException {
		final PlayerConnection connection = playerConnectionPool.getPlayerConnection(playerId);
		if (connection == null) {
			throw new PlayerConnectException(Player.getName(playerId));
		}
		final String pass = hashPass + "";
		if (pass.equals(connection.getPass()) || pass.equals(connection.getPassword())) {
			final Player player = playerPersistenceService.findById(playerId);
			player.setPassword(hashPass);
			return player;
		}
		throw new LoginFailedException();
	}

	private boolean isValidPlayerId(Long playerId) {
		return playerId != null;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ServerMessageResponse handleException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		final String locale = (String)request.getAttribute("locale");
        if (e instanceof UnknownPlayerException) {
			LOGGER.error(e.getMessage() + " is unknown");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new ServerMessageResponse(HttpServletResponse.SC_BAD_REQUEST,
					PlayMessages.get(locale).playerIsUnknown(e.getMessage()));
		}
		else if (e instanceof LoginFailedException) {
			LOGGER.error(e.getMessage());
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return new ServerMessageResponse(HttpServletResponse.SC_FORBIDDEN,
					Messages.get(locale).loginFailed());
		}
		else if (e instanceof PlayerConnectException) {
			LOGGER.error(PlayMessages.get(locale).playerConnectionError() + " " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return new ServerMessageResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					PlayMessages.get(locale).playerConnectionError());
		}
		else {
			LOGGER.error(e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return new ServerMessageResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					e.getMessage());
		}
	}
}
