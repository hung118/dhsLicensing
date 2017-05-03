package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningLetterDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLetter;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author jtorres
 * 
 */
@Repository("trackingRecordScreeningLetterDao")
public class TrackingRecordScreeningLetterDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningLetter, Long> implements TrackingRecordScreeningLetterDao {

	private static final String LETTERS_FOR_SCREENING_QUERY = "from TrackingRecordScreeningLetter t where t.trackingRecordScreening.id = :screeningId ";
	
	public TrackingRecordScreeningLetterDaoImpl() {
		super(TrackingRecordScreeningLetter.class);
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingRecordScreeningLetter> getLettersForScreening(Long screeningId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(LETTERS_FOR_SCREENING_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("screeningId", screeningId);
		
		return (List<TrackingRecordScreeningLetter>) query.getResultList();
	}
	
}
