package gov.utah.dts.det.ccl.dao.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningCaseDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCase;
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
@Repository("trackingRecordScreeningCaseDao")
public class TrackingRecordScreeningCaseDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningCase, Long> implements
TrackingRecordScreeningCaseDao {

	private static final String CASES_FOR_SCREENING_QUERY = "from TrackingRecordScreeningCase t where t.trackingRecordScreening.id = :screeningId order by t.caseDate desc, t.caseNumber";

	@PersistenceContext
	private EntityManager em;

	public TrackingRecordScreeningCaseDaoImpl() {
		super(TrackingRecordScreeningCase.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<TrackingRecordScreeningCase> getCasesForScreening(Long screeningId) {
		Query query = em.createQuery(CASES_FOR_SCREENING_QUERY);
		query.setParameter("screeningId", screeningId);

		return (List<TrackingRecordScreeningCase>) query.getResultList();
	}

}
