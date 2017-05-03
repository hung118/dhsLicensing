package gov.utah.dts.det.ccl.dao.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningMisCommDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMisComm;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author jtorres
 * 
 */
@Repository("trackingRecordScreeningMisCommDao")
public class TrackingRecordScreeningMisCommDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningMisComm, Long> implements TrackingRecordScreeningMisCommDao {

	private static final String MIS_COMM_FOR_SCREENING_QUERY = "from TrackingRecordScreeningMisComm t where t.trackingRecordScreening.id = :screeningId ";
	
	@PersistenceContext
	private EntityManager em;
	
	public TrackingRecordScreeningMisCommDaoImpl() {
		super(TrackingRecordScreeningMisComm.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TrackingRecordScreeningMisComm> getMisCommForScreening(Long screeningId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(MIS_COMM_FOR_SCREENING_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("screeningId", screeningId);
		
		return (List<TrackingRecordScreeningMisComm>) query.getResultList();
	}

}
