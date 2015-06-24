package at.thammerer.herbarium.dao;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * @author thammerer
 * @param <T>
 */
public interface BaseDao<T> {

	public T findById(Class<T> entityClass, Serializable id);

	public T merge(T entity);

	public void persist(T entity);

	public void remove(T entity);

	void refresh(T entity);

	public List<T> findAll(Class<T> entityClass);

	public List<T> findAll(Class<T> entityClass, int maxRecords);

	EntityManager getEntityManager();
}
