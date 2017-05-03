package gov.utah.dts.det.ccl.dao.impl;

import java.util.List;
import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningLtr15Dao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLtr15;
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
@Repository("trackingRecordScreeningLtr15Dao")
public class TrackingRecordScreeningLtr15DaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningLtr15, Long> implements TrackingRecordScreeningLtr15Dao {

	private static final String LETTERS_FOR_SCREENING_QUERY = "from TrackingRecordScreeningLtr15 t where t.trackingRecordScreening.id = :screeningId ";

	public TrackingRecordScreeningLtr15DaoImpl() {
		super(TrackingRecordScreeningLtr15.class);
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<TrackingRecordScreeningLtr15> get15DayLettersForScreening(Long screeningId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(LETTERS_FOR_SCREENING_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("screeningId", screeningId);
		
		return (List<TrackingRecordScreeningLtr15>) query.getResultList();
	}

}
