package gov.utah.dts.det.ccl.dao.impl;

import java.util.List;
import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningRequestsDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningRequests;
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
@SuppressWarnings("unchecked")
@Repository("trackingRecordScreeningRequestsDao")
public class TrackingRecordScreeningRequestDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningRequests, Long> implements TrackingRecordScreeningRequestsDao {

	private static final String REQUESTS_FOR_SCREENING_QUERY = "from TrackingRecordScreeningRequests t where t.trackingRecordScreening.id = :screeningId ";

	public TrackingRecordScreeningRequestDaoImpl() {
		super(TrackingRecordScreeningRequests.class);
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<TrackingRecordScreeningRequests> getRequestsForScreening(Long screeningId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(REQUESTS_FOR_SCREENING_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("screeningId", screeningId);
		
		return (List<TrackingRecordScreeningRequests>) query.getResultList();
	}

}
