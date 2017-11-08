package net.xapxinh.center.server.entity;

import java.io.Serializable;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private Long expired;	
	private String language;
	private String sessionId;
	private Integer extend;
	private Long playerId;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getExpired() {
		return expired;
	}

	public void setExpired(Long expired) {
		this.expired = expired;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getExtend() {
		return extend;
	}

	public void setExtend(Integer extend) {
		this.extend = extend;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
}
