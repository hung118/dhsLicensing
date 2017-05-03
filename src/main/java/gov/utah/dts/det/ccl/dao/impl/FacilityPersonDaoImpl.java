package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.FacilityPersonDao;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository("facilityPersonDao")
public class FacilityPersonDaoImpl extends AbstractBaseDaoImpl<FacilityPerson, Long> implements FacilityPersonDao {

	@PersistenceContext
	private EntityManager em;
	
	public FacilityPersonDaoImpl() {
		super(FacilityPerson.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
}