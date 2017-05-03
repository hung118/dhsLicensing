package gov.utah.dts.det.ccl.dao.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningConvictionDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConviction;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
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
@Repository("trackingRecordScreeningConvictionDao")
public class TrackingRecordScreeningConvictionDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningConviction, Long> implements
TrackingRecordScreeningConvictionDao {

	private static final String CONVICTIONS_FOR_SCREENING_QUERY = "from TrackingRecordScreeningConviction t where t.trackingRecordScreening.id = :screeningId order by t.convictionDate desc";
	private static final String CONVICTIONS_FOR_LETTER_QUERY = "from TrackingRecordScreeningConviction t where t.trackingRecordScreening.id = :screeningId " +
																"and t.dismissed != true and t.convictionType.value != 'PIA' order by t.convictionDate desc";

	
	@PersistenceContext
	private EntityManager em;

	public TrackingRecordScreeningConvictionDaoImpl() {
		super(TrackingRecordScreeningConviction.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<TrackingRecordScreeningConviction> getConvictionsForScreening(Long screeningId) {
		Query query = em.createQuery(CONVICTIONS_FOR_SCREENING_QUERY);
		query.setParameter("screeningId", screeningId);

		return (List<TrackingRecordScreeningConviction>) query.getResultList();
	}
	
	@Override
	public List<TrackingRecordScreeningConviction> getConvictionsForLetter(Long screeningId) {
		Query query = em.createQuery(CONVICTIONS_FOR_LETTER_QUERY);
		query.setParameter("screeningId", screeningId);

		return (List<TrackingRecordScreeningConviction>) query.getResultList();
	}

}
