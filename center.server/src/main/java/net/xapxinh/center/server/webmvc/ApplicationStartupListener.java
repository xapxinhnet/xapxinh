package net.xapxinh.center.server.webmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import net.xapxinh.center.server.service.player.PlayerConnectionPool;

@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private PlayerConnectionPool playerConnectionPool;
	
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		playerConnectionPool.start();
	}
}