package net.xapxinh.center.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import net.xapxinh.center.server.api.net.HttpApi;
import net.xapxinh.center.server.api.player.IPlayerApi;
import net.xapxinh.center.server.persistence.service.IPlayerPersistenceService;
import net.xapxinh.center.server.persistence.service.SessionService;
import net.xapxinh.center.server.service.data.DataService;
import net.xapxinh.center.server.service.player.IPlayerService;
import net.xapxinh.center.server.service.player.PlayerConnectionPool;

public class AppContext implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) {
		context = ctx;
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	public static HttpApi getHttpApi() {
		return getApplicationContext().getBean("httpApi", HttpApi.class);
	}
	
	public static IPlayerApi getPlayerApi() {
		return getApplicationContext().getBean("playerApi", IPlayerApi.class);
	}
	
	public static IPlayerService getPlayerService() {
		return getApplicationContext().getBean("playerService", IPlayerService.class);
	}
	
	public static DataService getDataService() {
		return getApplicationContext().getBean("dataService", DataService.class);
	}
	
	public static SessionService getSessionService() {
		return getApplicationContext().getBean("sessionService", SessionService.class);
	}

	public static IPlayerPersistenceService getPlayerPersistenceService() {
		return getApplicationContext().getBean("playerPersistenceService", IPlayerPersistenceService.class);
	}

	public static PlayerConnectionPool getPlayerConnectionPool() {
		return getApplicationContext().getBean("playerConnectionPool", PlayerConnectionPool.class);
	}
}