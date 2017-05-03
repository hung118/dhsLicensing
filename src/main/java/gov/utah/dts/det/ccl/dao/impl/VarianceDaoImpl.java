package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.VarianceDao;
import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.model.view.VarianceAlertView;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("varianceDao")
public class VarianceDaoImpl extends AbstractBaseDaoImpl<Variance, Long> implements VarianceDao {
	
	private static final String VARIANCE_HISTORY_QUERY = "from Variance v where v.facility.id = :facilityId ";
	private static final String VARIANCE_DATE_FIELD = "v.requestDate";
	private static final String LICENSOR_VARIANCE_QUERY = "from VarianceAlertView v where v.finalized = 'N' and v.licensorOutcome is null";
	private static final String MANAGER_VARIANCE_QUERY = "from VarianceAlertView v where v.finalized = 'N' and v.supervisorOutcome is null";
	private static final String DIRECTOR_VARIANCE_QUERY = "from VarianceAlertView v where v.finalized = 'N' and v.supervisorOutcome is not null";
	
	private static final String ALERT_VARIANCES_EXPIRING_QUERY = "select vev from VarianceExpiringView vev left join fetch vev.facility fac ";

	private static final String WHOLE_REGION_CLAUSE = " where fac.region.officeSpecialist.id = :personId ";
	private static final String SINGLE_PERSON_CLAUSE = " where fac.licensingSpecialist.id = :personId ";
	
	@PersistenceContext
	private EntityManager em;
	
	public VarianceDaoImpl() {
		super(Variance.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<Variance> getVariancesForFacility(Long facilityId, ListRange listRange, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(VARIANCE_HISTORY_QUERY);
		ServiceUtils.addIntervalClause(VARIANCE_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		
		return (List<Variance>) query.getResultList();
	}
	
	@Override
	public List<Variance> getVariancesExpiring(Long personId, boolean showWholeRegion, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_VARIANCES_EXPIRING_QUERY);
		if (showWholeRegion) {
			sb.append(WHOLE_REGION_CLAUSE);
		} else {
			sb.append(SINGLE_PERSON_CLAUSE);
		}
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);
		
		return (List<Variance>) query.getResultList();
	}

	@Override
	public List<VarianceAlertView> getLicensorVariances(SortBy sortBy) {
		StringBuilder sb = new StringBuilder(LICENSOR_VARIANCE_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		Query query = em.createQuery(sb.toString());
		
		return (List<VarianceAlertView>) query.getResultList();
	}

	@Override
	public List<VarianceAlertView> getManagerVariances(SortBy sortBy) {
		StringBuilder sb = new StringBuilder(MANAGER_VARIANCE_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		Query query = em.createQuery(sb.toString());
		
		return (List<VarianceAlertView>) query.getResultList();
	}

	@Override
	public List<VarianceAlertView> getDirectorVariances(SortBy sortBy) {
		StringBuilder sb = new StringBuilder(DIRECTOR_VARIANCE_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		Query query = em.createQuery(sb.toString());
		
		return (List<VarianceAlertView>) query.getResultList();
	}
}