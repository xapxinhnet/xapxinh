package net.xapxinh.center.server.persistence.service.impl;

import org.springframework.transaction.annotation.Transactional;

import net.xapxinh.center.server.entity.Session;
import net.xapxinh.center.server.persistence.dao.SessionDao;
import net.xapxinh.center.server.persistence.service.SessionService;

@Transactional
public class SessionServiceImpl extends AbstractGenericService<Session> implements SessionService {

	private SessionDao sessionDao;
	
	public SessionServiceImpl(SessionDao sessionDao) {
		this.sessionDao = sessionDao;
	}

	@Override
	protected SessionDao getDao() {
		return sessionDao;
	}

	@Override
	public Session findBySessionId(String sessionId) {
		return getDao().findBySessionId(sessionId);
	}
}
