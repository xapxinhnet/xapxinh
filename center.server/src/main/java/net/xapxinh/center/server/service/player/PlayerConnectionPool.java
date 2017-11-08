package net.xapxinh.center.server.service.player;


import org.apache.log4j.Logger;

import net.xapxinh.center.server.AppContext;
import net.xapxinh.center.server.api.player.PlayerConnection;
import net.xapxinh.center.server.config.AppConfig;
import net.xapxinh.center.server.entity.EntityFactory;
import net.xapxinh.center.server.entity.Player;
import net.xapxinh.center.server.persistence.service.IPlayerPersistenceService;
import net.xapxinh.center.shared.cookie.Crypto;

public class PlayerConnectionPool extends AbstractPlayerConnectionPool {

	private static final Logger LOGGER = Logger.getLogger(PlayerConnectionPool.class.getName());

	private final IPlayerPersistenceService playerPersistenceService;

	public PlayerConnectionPool(IPlayerPersistenceService playerPersistenceService) {
		this.playerPersistenceService = playerPersistenceService;
	}
	
	@Override
	protected Player getPlayer(String mac){
		Player player = playerPersistenceService.findByMac(mac);
		if (player == null) {
			player = EntityFactory.newPlayer();
			player.setMac(mac);
			playerPersistenceService.insert(player);
			player.setName(Player.getName(player.getId()));
			playerPersistenceService.update(player);
			try {
				AppContext.getDataService().insertPlayer(player);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return player;
	}

	@Override
	protected int getTcpPort() {
		return AppConfig.getInstance().PLAYER_TCP_PORT;
	}

	@Override
	protected void onAcceptedConnection(PlayerConnection connection) {
		try {
			connection.setPass(Crypto.getInstance().encrypt(connection.getPass()));
			connection.setPassword(Crypto.getInstance().encrypt(connection.getPassword()));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	protected void onRemovedConnection(Long connectionId) {
		// does nothing
	}
}
