package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.PersonDao;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.service.PersonSearchCriteria;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository("personDao")
public class PersonDaoImpl extends AbstractBaseDaoImpl<Person, Long> implements PersonDao {
	
	private static final String PERSON_QUERY = "select distinct p from Person p where (upper(p.name.firstName) like :name) or (upper(p.name.lastName) like :name) order by upper(p.name.firstName), upper(p.name.middleName), upper(p.name.lastName)";
	
	@PersistenceContext
	private EntityManager em;
	
	public PersonDaoImpl() {
		super(Person.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getPersonsByCriteria(PersonSearchCriteria criteria) {
		Query query = em.createQuery(PERSON_QUERY);
		query.setParameter("name", criteria.getName().toUpperCase()+"%");

		return (List<Person>) query.getResultList();
	}
}