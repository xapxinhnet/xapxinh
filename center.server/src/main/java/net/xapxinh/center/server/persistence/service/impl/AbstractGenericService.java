package net.xapxinh.center.server.persistence.service.impl;

import java.io.Serializable;
import java.util.List;

import net.xapxinh.center.server.persistence.dao.IGenericDao;
import net.xapxinh.center.server.persistence.service.IGenericService;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractGenericService<T extends Serializable> implements IGenericService<T> {	
	
	public List<T> loadAll() {
		return getDao().loadAll();
	}
	public T findById(final long id) {
		return getDao().findById(id);
	}
	public void insert(final T entity) {
		getDao().insert(entity);
	}
	public T update(final T entity) {
		return getDao().update(entity);
	}
	public void delete(final T entity) {
		getDao().delete(entity);
	}
	public void deleteById(final long entityId) {
		getDao().deleteById(entityId);
	}
	
	protected abstract IGenericDao<T> getDao();
}
