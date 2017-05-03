package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.PickListValuePersonRelationshipDao;
import gov.utah.dts.det.ccl.model.PickListValuePersonRelationship;
import gov.utah.dts.det.ccl.model.PickListValuePersonRelationshipPk;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository("pickListValuePersonRelationshipDao")
public class PickListValuePersonRelationshipDaoImpl extends AbstractBaseDaoImpl<PickListValuePersonRelationship, PickListValuePersonRelationshipPk> implements PickListValuePersonRelationshipDao {

	public PickListValuePersonRelationshipDaoImpl() {
		super(PickListValuePersonRelationship.class);
	}

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
}