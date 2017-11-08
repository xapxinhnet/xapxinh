package net.xapxinh.center.server.persistence.service.impl;

import org.springframework.transaction.annotation.Transactional;

import net.xapxinh.center.server.entity.Player;
import net.xapxinh.center.server.persistence.dao.PlayerDao;
import net.xapxinh.center.server.persistence.service.IPlayerPersistenceService;

@Transactional
public class PlayerPersistenceServiceImpl extends AbstractGenericService<Player> implements IPlayerPersistenceService {

	private PlayerDao playerDao;
	
	public PlayerPersistenceServiceImpl(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}

	@Override
	protected PlayerDao getDao() {
		return playerDao;
	}
	
	@Override
	public Player findByMac(String mac) {
		return getDao().findByMac(mac);
	}
}
