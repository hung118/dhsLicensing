package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningDpsFbiDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningDpsFbi;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author jtorres
 * 
 */
@Repository("trackingRecordScreeningDpsFbiDao")
public class TrackingRecordScreeningDpsFbiDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningDpsFbi, Long> implements TrackingRecordScreeningDpsFbiDao {

	public TrackingRecordScreeningDpsFbiDaoImpl() {
		super(TrackingRecordScreeningDpsFbi.class);
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
