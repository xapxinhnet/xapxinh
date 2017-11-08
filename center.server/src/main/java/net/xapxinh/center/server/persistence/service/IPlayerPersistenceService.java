package net.xapxinh.center.server.persistence.service;

import org.springframework.transaction.annotation.Transactional;

import net.xapxinh.center.server.entity.Player;

@Transactional
public interface IPlayerPersistenceService extends IGenericService<Player> {		

	public Player findByMac(String mac);

}
