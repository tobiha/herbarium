package at.thammerer.herbarium.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * @author thammerer
 * @param <T>
 */
public class BaseDao<T>  {

	@PersistenceContext
	protected EntityManager em;

	public T findById(Class<T> entityClass, Serializable id) {
		T instance = em.find(entityClass, id);
		return instance;
	}

	public T merge(T entity) {
		T result = em.merge(entity);
		return result;
	}

	public void persist(T entity) {
		em.persist(entity);
	}

	public void remove(T entity) {
		em.remove(entity);
	}

	public void refresh(T entity) {
		em.refresh(entity);
	}

	public List<T> findAll(Class<T> entityClass) {
		TypedQuery<T> query = em.createQuery("SELECT t FROM " + entityClass.getName() + " t", entityClass);
		return query.getResultList();
	}

	public List<T> findAll(Class<T> entityClass, int maxRecords) {
		if (maxRecords <= 0) {
			return findAll(entityClass);
		} else {
			TypedQuery<T> query = em.createQuery("SELECT t FROM " + entityClass.getName() + " t", entityClass);
			query.setMaxResults(maxRecords);
			return query.getResultList();
		}
	}

	public EntityManager getEntityManager(){
		return em;
	}

}
