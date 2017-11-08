package net.xapxinh.center.server.entity;


public class EntityFactory {
	
	public static Player newPlayer() {
		return new Player();
	}

	public static Session newSession(String sessionId) {
		Session session = new Session();
		session.setSessionId(sessionId);
		return session;
	}
}