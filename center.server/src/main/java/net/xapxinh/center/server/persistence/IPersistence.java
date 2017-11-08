package net.xapxinh.center.server.persistence;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IPersistence<T> {
	
	public void insert(T entity);

	public T update(T entity);

	public void delete(T entity);
	
	public void deleteById(long id);
	
	public List<T> loadAll();
	
	public T findById(long id);
}
