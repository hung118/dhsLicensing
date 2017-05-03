package gov.utah.dts.det.ccl.dao.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningActivityDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningActivity;
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
@Repository("trackingRecordScreeningActivityDao")
public class TrackingRecordScreeningActivityDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningActivity, Long> implements TrackingRecordScreeningActivityDao {

	private static final String ACTIVITY_FOR_SCREENING_QUERY = "from TrackingRecordScreeningActivity t where t.trackingRecordScreening.id = :screeningId ";
	
	@PersistenceContext
	private EntityManager em;

	public TrackingRecordScreeningActivityDaoImpl() {
		super(TrackingRecordScreeningActivity.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TrackingRecordScreeningActivity> getActivityForScreening(Long screeningId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ACTIVITY_FOR_SCREENING_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("screeningId", screeningId);
		
		return (List<TrackingRecordScreeningActivity>) query.getResultList();
	}
	
}
