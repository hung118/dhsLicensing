package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningCbsCommDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCbsComm;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author jtorres
 * 
 */
@Repository("trackingRecordScreeningCbsCommDao")
public class TrackingRecordScreeningCbsCommDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningCbsComm, Long> implements TrackingRecordScreeningCbsCommDao {

	public TrackingRecordScreeningCbsCommDaoImpl() {
		super(TrackingRecordScreeningCbsComm.class);
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}
}
