package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.ComplaintDao;
import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.view.ComplaintAllegationView;
import gov.utah.dts.det.ccl.model.view.ComplaintView;
import gov.utah.dts.det.ccl.model.view.ComplaintsInProgressView;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.ccl.view.facility.StatewideUnlicensedComplaintView;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("complaintDao")
public class ComplaintDaoImpl extends AbstractBaseDaoImpl<Complaint, Long> implements ComplaintDao {

	private static final String COMPLAINT_HISTORY_QUERY = "from ComplaintView cv where cv.facilityId = :facilityId";
	private static final String COMPLAINT_HISTORY_FINALIZED_CLAUSE = " and cv.state = 'FINALIZED' ";
	private static final String COMPLAINT_HISTORY_UNFINALIZED_CLAUSE = " and cv.state != 'FINALIZED' ";
	private static final String COMPLAINT_DATE_FIELD = "cv.dateReceived";
	
	private static final String COMPLAINT_ALLEGATION_VIEWS_QUERY = "from ComplaintAllegationView cav where cav.complaint.id = :complaintId";

	private static final String COMPLAINTS_IN_PROGRESS_QUERY = "select c from Complaint c where c.facility.id = :facilityId and c.internalState != 'FINALIZED'"; 
	
	private static final String ALERT_COMPLAINTS_IN_PROGRESS_QUERY = "select cipv from ComplaintsInProgressView cipv left join fetch cipv.facility fac where ";
	private static final String ALERT_COMPLAINTS_IN_PROGRESS_PERSON_CLAUSE = "cipv.licensingSpecialistId = :licensingSpecialistId and ";
	private static final String ALERT_COMPLAINTS_IN_PROGRESS_ROLE_CLAUSE = "cipv.role = :role ";
	
	private static final String STATEWIDE_COMPLAINT_QUERY = "select new gov.utah.dts.det.ccl.view.facility.StatewideUnlicensedComplaintView(" +
			"c.id, fsv.id, fsv.facilityName, fsv.ownerName, c.dateReceived, fsv.locationAddress.addressOne, fsv.locationAddress.addressTwo," +
			" fsv.locationAddress.city, fsv.locationAddress.state, fsv.locationAddress.zipCode, fsv.status) from UnlicensedComplaint c, " +
			" FacilitySearchView fsv where c.facility.id = fsv.id ";
	private static final String STATEWIDE_COMPLAINT_COUNT_QUERY = "select count(c.id) from UnlicensedComplaint c where c.id = c.id ";
	private static final String STATEWIDE_COMPLAINT_DATE_FIELD = "c.dateReceived";

	public ComplaintDaoImpl() {
		super(Complaint.class);
	}

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<ComplaintView> getComplaintsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized) {
		StringBuilder sb = new StringBuilder(COMPLAINT_HISTORY_QUERY);
		if (finalized) {
			sb.append(COMPLAINT_HISTORY_FINALIZED_CLAUSE);
		} else {
			sb.append(COMPLAINT_HISTORY_UNFINALIZED_CLAUSE);
		}
		ServiceUtils.addIntervalClause(COMPLAINT_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		
		return (List<ComplaintView>) query.getResultList();
	}
	
	@Override
	public List<Complaint> getComplaintsInProgress(Long facilityId) {
		Query query = em.createQuery(COMPLAINTS_IN_PROGRESS_QUERY);
		query.setParameter("facilityId", facilityId);
		
		return (List<Complaint>) query.getResultList();
	}
	
	@Override
	public List<ComplaintsInProgressView> getComplaintsInProgress(Long personId, String role, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_COMPLAINTS_IN_PROGRESS_QUERY);
		if (personId != null) {
			sb.append(ALERT_COMPLAINTS_IN_PROGRESS_PERSON_CLAUSE);
		}
		sb.append(ALERT_COMPLAINTS_IN_PROGRESS_ROLE_CLAUSE);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		if (personId != null) {
			query.setParameter("licensingSpecialistId", personId);
		}
		query.setParameter("role", role);
		
		return (List<ComplaintsInProgressView>) query.getResultList();
	}
	
	@Override
	public List<ComplaintAllegationView> getAllegationViews(Long complaintId) {
		Query query = em.createQuery(COMPLAINT_ALLEGATION_VIEWS_QUERY);
		query.setParameter("complaintId", complaintId);
		
		return (List<ComplaintAllegationView>) query.getResultList();
	}
	
	@Override
	public List<StatewideUnlicensedComplaintView> getStatewideUnlicensedComplaints(ListRange listRange, SortBy sortBy, int page, int resultsPerPage) {
		StringBuilder sb = new StringBuilder(STATEWIDE_COMPLAINT_QUERY);
		ServiceUtils.addIntervalClause(STATEWIDE_COMPLAINT_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		
		int maxResults = resultsPerPage == 0 ? 250 : resultsPerPage;
		int firstResult = page * resultsPerPage;
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		
		return (List<StatewideUnlicensedComplaintView>) query.getResultList();
	}
	
	@Override
	public int getStatewideUnlicensedComplaintCount(ListRange listRange) {
		StringBuilder sb = new StringBuilder(STATEWIDE_COMPLAINT_COUNT_QUERY);
		ServiceUtils.addIntervalClause(STATEWIDE_COMPLAINT_DATE_FIELD, sb, listRange);
		
		Query query = em.createQuery(sb.toString());
		
		Long results = (Long) query.getSingleResult();
		return results.intValue();
	}

}