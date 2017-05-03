package gov.utah.dts.det.ccl.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

@SuppressWarnings("unchecked")
public interface GenericDao {

	public Object getEntity(Class type, Serializable id);
	
	public Object insert(Object entity);

	public Object update(Object entity);

	/**
	 * Calls the entityManager.remove()
	 * @param entity
	 * @return
	 */
	public boolean delete(Object entity);
	
	public List<Object> findByProperty(Class type, String property, Object value);
	
	public List<Object> findByPropertiesUsingAnd(Class type, String[] property, Object[] value);

	public List<Object> query(String hql);
	
	public Object querySingleResult(String hql);
	
	public EntityManager getEntityManager();
	
	/**
	 * Creates an HQL statement to delete the object with the given id
	 * @param type
	 * @param id
	 * @return
	 */
	public boolean delete(Class type, Object id);
	
	/**
	 * Creates an HQL statement to delete the object with the given property = value
	 * e.g. DELETE Person p WHERE p.property = value 
	 * @param type
	 * @param property
	 * @param id
	 * @return
	 */
	public int delete(Class type, String property, Object value);
}