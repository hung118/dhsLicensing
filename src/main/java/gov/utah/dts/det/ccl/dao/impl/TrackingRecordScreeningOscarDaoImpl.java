package gov.utah.dts.det.ccl.dao.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningOscarDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningOscar;
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
@Repository("trackingRecordScreeningOscarDao")
public class TrackingRecordScreeningOscarDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreeningOscar, Long> implements TrackingRecordScreeningOscarDao {

	private static final String OSCAR_FOR_SCREENING_QUERY = "from TrackingRecordScreeningOscar t where t.trackingRecordScreening.id = :screeningId ";
	
	@PersistenceContext
	private EntityManager em;
	
	public TrackingRecordScreeningOscarDaoImpl() {
		super(TrackingRecordScreeningOscar.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TrackingRecordScreeningOscar> getOscarForScreening(Long screeningId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(OSCAR_FOR_SCREENING_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("screeningId", screeningId);
		
		return (List<TrackingRecordScreeningOscar>) query.getResultList();
	}

}
