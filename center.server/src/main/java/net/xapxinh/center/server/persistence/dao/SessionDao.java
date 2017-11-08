package net.xapxinh.center.server.persistence.dao;

import org.springframework.transaction.annotation.Transactional;

import net.xapxinh.center.server.entity.Session;

@Transactional
public interface SessionDao extends IGenericDao<Session> {

	Session findBySessionId(String sessionId);
}
