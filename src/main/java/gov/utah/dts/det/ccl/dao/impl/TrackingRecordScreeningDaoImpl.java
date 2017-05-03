package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.model.view.TRSSearchView;
import gov.utah.dts.det.ccl.service.TRSSearchCriteria;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.WhereClause;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author jtorres
 * 
 */
@Repository("trackingRecordScreeningDao")
public class TrackingRecordScreeningDaoImpl extends AbstractBaseDaoImpl<TrackingRecordScreening, Long> implements
TrackingRecordScreeningDao {

	private static final String TRS_SEARCH_QUERY = "from TRSSearchView tsv ";
	private static final String TRS_SEARCH_COUNT_QUERY = "select count(*) from TRSSearchView tsv ";
	private static final String SCREENINGS_FOR_FACILITY_QUERY = "from TrackingRecordScreening trs where trs.facility.id = :facilityId";
	private static final String SCREENING_DATE_FIELD = "trs.creationDate";
	private static final String LIVESCANS_ISSUED_QUERY = "from TrackingRecordScreening trs join fetch trs.trsDpsFbi fbi join fetch trs.facility f "
			+ "join fetch f.cbsTechnician t where t.id = :technicianId";
	private static final String LIVESCANS_ISSUED_START_CLAUSE = " AND trunc(fbi.livescanDate) >= :startDate";
	private static final String LIVESCANS_ISSUED_END_CLAUSE = " AND trunc(fbi.livescanDate) <= :endDate";
	private static final String LIVESCANS_ISSUED_ORDER_BY_CLAUSE = " order by f.name, fbi.livescanDate asc, trs.firstName asc, trs.lastName asc";

	public TrackingRecordScreeningDaoImpl() {
		super(TrackingRecordScreening.class);
	}

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	public int searchTRSCount(TRSSearchCriteria criteria) {
		StringBuilder sb = new StringBuilder(TRS_SEARCH_COUNT_QUERY);
		buildSearchQuery(sb, criteria, null);
		Query query = buildQuery(sb.toString(), criteria);
		Long results = (Long) query.getSingleResult();
		return results.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<TRSSearchView> searchTrackingRecordScreenings(TRSSearchCriteria criteria, SortBy sortBy, int page, int resultsPerPage) {
		StringBuilder sb = new StringBuilder(TRS_SEARCH_QUERY);
		buildSearchQuery(sb, criteria, sortBy);
		Query query = buildQuery(sb.toString(), criteria);

		int maxResults = resultsPerPage == 0 ? 250 : resultsPerPage;
		int firstResult = page * resultsPerPage;
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);

		List<TRSSearchView> list = query.getResultList();
		// return (List<TRSSearchCriteria>) query.getResultList();
		return list;
	}

	private void buildSearchQuery(StringBuilder query, TRSSearchCriteria criteria, SortBy sortBy) {
		if (criteria != null) {
			if (StringUtils.isNotBlank(criteria.getFirstName()) || StringUtils.isNotBlank(criteria.getLastName()) ||
					StringUtils.isNotBlank(criteria.getSsnLastFour()) || criteria.getBirthday() != null) 
			{
				query.append(" where ");
				WhereClause whereClause = new WhereClause(query);

				if (StringUtils.isNotBlank(criteria.getFirstName())) {
					whereClause.addClause(" upper(tsv.firstName) like :firstName ");
				}
				if (StringUtils.isNotBlank(criteria.getLastName())) {
					whereClause.addClause(" upper(tsv.lastName) like :lastName ");
				}
				if (StringUtils.isNotBlank(criteria.getSsnLastFour())) {
					whereClause.addClause(" tsv.ssnLastFour = :ssnLastFour");
				}
				if (criteria.getBirthday() != null) {
					whereClause.addClause(" trunc(tsv.birthday) = trunc(:birthdate) ");
				}
			}
		}
		
		ServiceUtils.addSortByClause(query, sortBy, null);
	}

	private Query buildQuery(String queryString, TRSSearchCriteria criteria) {
		Query query = em.createQuery(queryString);
		if (criteria != null) {
			// set parameters
			if (StringUtils.isNotBlank(criteria.getFirstName())) {
				query.setParameter("firstName", criteria.getFirstName().toUpperCase() + "%");
			}
			if (StringUtils.isNotBlank(criteria.getLastName())) {
				query.setParameter("lastName", criteria.getLastName().toUpperCase() + "%");
			}
			if (StringUtils.isNotBlank(criteria.getSsnLastFour())) {
				query.setParameter("ssnLastFour", criteria.getSsnLastFour().toUpperCase());
			}
			if (criteria.getBirthday() != null) {
				query.setParameter("birthdate", criteria.getBirthday());
			}
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingRecordScreening> getScreeningsForFacility(Long facilityId, ListRange listRange, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(SCREENINGS_FOR_FACILITY_QUERY);
		ServiceUtils.addIntervalClause(SCREENING_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);

		return (List<TrackingRecordScreening>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingRecordScreening> getLivescansIssued(Long technicianId, Date startDate, Date endDate) {
		StringBuilder sb = new StringBuilder(LIVESCANS_ISSUED_QUERY);
		if (startDate != null) {
			sb.append(LIVESCANS_ISSUED_START_CLAUSE);
		}
		if (endDate != null) {
			sb.append(LIVESCANS_ISSUED_END_CLAUSE);
		}
		sb.append(LIVESCANS_ISSUED_ORDER_BY_CLAUSE);

		Query query = em.createQuery(sb.toString());
		query.setParameter("technicianId", technicianId);
		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}

		return (List<TrackingRecordScreening>) query.getResultList();
	}

}
