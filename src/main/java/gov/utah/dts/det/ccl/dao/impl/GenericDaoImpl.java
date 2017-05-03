package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.GenericDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("genericDao")
public class GenericDaoImpl implements GenericDao {

	@Autowired
	@PersistenceContext
	private EntityManager em;
	
	public Object getEntity(Class type, Serializable id) {
		return em.find(type, id);
	}

	public Object insert(Object entity) {
		em.persist(entity);
		return entity;
	}

	public Object update(Object entity) {
		return em.merge(entity);
	}

	public boolean delete(Object entity) {
		em.remove(entity);
		return true;
	}
	
	public boolean delete(Class type, Object id) {
		String hql = "DELETE "+type.getSimpleName()+" obj WHERE obj.id = "+id.toString();
		if (em.createQuery(hql).executeUpdate() > 0) {
			return true;
		}
		return false;
	}

	public int delete(Class type, String property, Object value) {
		String hql = "DELETE "+type.getSimpleName()+" obj WHERE obj."+property+" = "+value;
		if (value instanceof String) {
			hql = "DELETE "+type.getSimpleName()+" obj WHERE obj."+property+" = '"+value+"'";
		}
		return em.createQuery(hql).executeUpdate();
	}

	public List<Object> findByProperty(Class type, String property, Object value) {
		String hql = "FROM "+type.getSimpleName()+" e WHERE e."+property+" = ?";
		Query q = em.createQuery(hql, type);
		q.setParameter(1, value);
		return q.getResultList();
	}
	
	public List<Object> findByPropertiesUsingAnd(Class type, String[] property, Object[] value) {
		String hql = "FROM "+type.getSimpleName()+" e WHERE ";
		for (int i = 0; i < property.length; i++) {
			if (i == 0)
				hql += "e."+property[i]+" = ? ";
			else
				hql += "AND e."+property[i]+" = ? ";
		}
		Query q = em.createQuery(hql, type);
		for (int i = 1; i <= value.length; i++) {
			q.setParameter(i, value);
		}
		return q.getResultList();
	}
	
	public List<Object> query(String hql) {
		Query q = em.createQuery(hql);
		return q.getResultList();
	}
	
	public Object querySingleResult(String hql) {
		Query q = em.createQuery(hql);
		return q.getSingleResult();
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
}