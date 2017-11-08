package net.xapxinh.center.server.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import net.xapxinh.center.server.persistence.IPersistence;

@Transactional
public interface IGenericDao<T extends Serializable> extends IPersistence<T> {	
	List<T> findMany(Query query);
	List<T> findMany(Query query, int fromIndex, int maxResults);
}
