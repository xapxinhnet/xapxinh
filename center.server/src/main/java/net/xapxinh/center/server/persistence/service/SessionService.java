package net.xapxinh.center.server.persistence.service;

import org.springframework.transaction.annotation.Transactional;

import net.xapxinh.center.server.entity.Session;

@Transactional
public interface SessionService extends IGenericService<Session> {
	Session findBySessionId(String sessionId);
}
