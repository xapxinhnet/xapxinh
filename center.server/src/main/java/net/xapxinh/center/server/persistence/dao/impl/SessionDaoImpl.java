package net.xapxinh.center.server.persistence.dao.impl;

import java.util.List;

import net.xapxinh.center.server.entity.Session;
import net.xapxinh.center.server.persistence.dao.SessionDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class SessionDaoImpl extends AbstractGenericDao<Session> implements SessionDao {

	public SessionDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
		setClass(Session.class);
	}

	@Override
	public Session findBySessionId(String sessionId) {
		String sql = "select distinct session_" 
				+ " from Session as session_" 
				+ " where session_.sessionId = :sessionId";
		
		Query query = getCurrentSession().createQuery(sql)
				.setParameter("sessionId", sessionId);

		List<Session> sessions = findMany(query);
		
		return getFirst(sessions);
	}
}
