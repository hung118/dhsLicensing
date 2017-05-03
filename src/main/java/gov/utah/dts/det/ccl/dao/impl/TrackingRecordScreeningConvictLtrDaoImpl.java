package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningConvictLtrDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConvictionLetter;
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
@Repository("trackingRecordScreeningConvictLtrDao")
public class TrackingRecordScreeningConvictLtrDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningConvictionLetter, Long> implements TrackingRecordScreeningConvictLtrDao {

	private static final String CONVICTION_LETTERS_QUERY = "from TrackingRecordScreeningConvictionLetter t where t.trackingRecordScreening.id = :screeningId ";

	public TrackingRecordScreeningConvictLtrDaoImpl() {
		super(TrackingRecordScreeningConvictionLetter.class);
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingRecordScreeningConvictionLetter> getConvictionLettersForScreening(Long screeningId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(CONVICTION_LETTERS_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("screeningId", screeningId);
		
		return (List<TrackingRecordScreeningConvictionLetter>) query.getResultList();
	}

}
