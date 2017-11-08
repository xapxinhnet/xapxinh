package net.xapxinh.center.server.persistence.dao;

import org.springframework.transaction.annotation.Transactional;

import net.xapxinh.center.server.entity.Player;

@Transactional
public interface PlayerDao extends IGenericDao<Player> {

	public Player findByMac(String key);
}
