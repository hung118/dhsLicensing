package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.FacilityDao;
import gov.utah.dts.det.ccl.dao.SearchException;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.FacilityTag;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.FacilityEventType;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.AccreditationExpiringView;
import gov.utah.dts.det.ccl.model.view.AlertFollowUpsNeededView;
import gov.utah.dts.det.ccl.model.view.AnnouncedInspectionNeededView;
import gov.utah.dts.det.ccl.model.view.BasicFacilityInformation;
import gov.utah.dts.det.ccl.model.view.ConditionalFacilityView;
import gov.utah.dts.det.ccl.model.view.ErepView;
import gov.utah.dts.det.ccl.model.view.ExemptVerificationView;
import gov.utah.dts.det.ccl.model.view.ExpiredAndExpiringLicenseView;
import gov.utah.dts.det.ccl.model.view.FacilityCaseloadView;
import gov.utah.dts.det.ccl.model.view.FacilityContactView;
import gov.utah.dts.det.ccl.model.view.FacilityEventView;
import gov.utah.dts.det.ccl.model.view.FacilityLicenseView;
import gov.utah.dts.det.ccl.model.view.FacilitySearchView;
import gov.utah.dts.det.ccl.model.view.FileCheckView;
import gov.utah.dts.det.ccl.model.view.NewApplicationPendingDeadlineView;
import gov.utah.dts.det.ccl.model.view.OutstandingCmpView;
import gov.utah.dts.det.ccl.model.view.SortableFacilityView;
import gov.utah.dts.det.ccl.model.view.TrackingRecordScreeningApprovalsView;
import gov.utah.dts.det.ccl.model.view.UnannouncedInspectionNeededView;
import gov.utah.dts.det.ccl.model.view.WorkInProgressView;
import gov.utah.dts.det.ccl.service.FacilitySearchCriteria;
import gov.utah.dts.det.ccl.service.UserCaseloadCount;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.ccl.view.FacilityResultView;
import gov.utah.dts.det.ccl.view.KeyValuePair;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("unchecked")
@Repository("facilityDao")
public class FacilityDaoImpl extends AbstractBaseDaoImpl<Facility, Long> implements FacilityDao {

	private static final String FACILITY_BY_FACILITY_ID_QUERY = "from Facility f where f.idNumber = :idNumber";

	private static final String LICENSE_BY_ID_QUERY = "from License l where l.id = :id";

	private static final String FACILITY_PEOPLE_QUERY = "from FacilityPerson fp left join fetch fp.person left join fetch fp.person.address "
			+ " where fp.facility.id = :facilityId ";
	private static final String FACILITY_PEOPLE_PERSON_TYPE_CLAUSE = " and fp.type in (:peopleTypes) ";
	private static final String FACILITY_PEOPLE_ORDER_BY_CLAUSE = " order by fp.person.name.firstName, fp.person.name.lastName ";

	private static final String FACILITY_PERSON_QUERY = "from FacilityPerson fp left join fetch fp.person left join fetch fp.person.address "
		+ " where fp.facility.id = :facilityId and fp.id = :facilityPersonId and (fp.type.id = :firstType or fp.type.id = :secondType) ";

	private static final String FACILITY_BOARD_MEMBER_QUERY = "from FacilityPerson fp left join fetch fp.person left join fetch fp.person.address "
		+ " where fp.facility.id = :facilityId and fp.id = :facilityPersonId and fp.type.id = :facilityPersonType ";

	private static final String SCREENED_PEOPLE_QUERY = "select distinct sav from TrackingRecordScreeningApprovalsView sav "
			+ "left join fetch sav.person p left join fetch p.address a left join fetch sav.facility f "
			+ "where (sav.facilityId = :facilityId or "
			+ "sav.facilityId in (select distinct fa.parent.id from FacilityAssociation fa where fa.child.id = :facilityId) or "
			+ "sav.facilityId in (select distinct fa.child.id from FacilityAssociation fa where fa.parent.id = :facilityId)) ";
	private static final String EXCLUDE_DIRECTORS_CLAUSE = " and sav.personId not in "
			+ "(select distinct fp.personId from FacilityPerson fp where fp.type.value in ('First Director','Second Director') and "
			+ "(fp.facilityId = :facilityId or "
			+ "(fp.facilityId not in (select distinct fa.parent.id from FacilityAssociation fa where fa.child.id = :facilityId) and "
			+ "fp.facilityId not in (select distinct fa.child.id from FacilityAssociation fa where fa.parent.id = :facilityId)))) ";
	private static final String EXCLUDE_CONTACTS_CLAUSE = " and sav.personId not in "
			+ "(select distinct fp.personId from FacilityPerson fp where fp.type.value in ('Primary Contact','Secondary Contact') and "
			+ "(fp.facilityId = :facilityId or "
			+ "(fp.facilityId not in (select distinct fa.parent.id from FacilityAssociation fa where fa.child.id = :facilityId) and "
			+ "fp.facilityId not in (select distinct fa.child.id from FacilityAssociation fa where fa.parent.id = :facilityId)))) ";
	private static final String SCREENED_PEOPLE_PERSON_CLAUSE = " and sav.personId = :personId ";

	private static final String FACILITY_SEARCH_QUERY = "select fsv from FacilitySearchView fsv where fsv.id in (select distinct f.id from Facility f ";
	private static final String FACILITY_SEARCH_COUNT_QUERY = "select count(f.id) from Facility f ";

	private static final String FACILITY_RESULT_VIEW_QUERY = "select new gov.utah.dts.det.ccl.view.FacilityResultView(f.id, f.name, "
			+ " f.idNumber, f.primaryPhone, cbs.addressOne, cbs.addressTwo, cbs.city, cbs.state, cbs.zipCode, ma.addressOne, ma.addressTwo, "
			+ " ma.city, ma.state, ma.zipCode) "
			+ " from Facility f left join f.cbsAddress cbs left join f.mailingAddress ma "
			+ " where f.id = :facilityId ";

	private static final String FACILITY_NAME_SEARCH_QUERY = "select new gov.utah.dts.det.ccl.view.FacilityResultView(f.id, initcap(f.name), "
			+ " f.idNumber, f.primaryPhone, cbs.addressOne, cbs.addressTwo, cbs.city, cbs.state, cbs.zipCode, ma.addressOne, ma.addressTwo, "
			+ " ma.city, ma.state, ma.zipCode) "
			+ " from Facility f left join f.cbsAddress cbs left join f.mailingAddress ma "
			+ " where f.active = 'Y' and f.cbsTechnician != null and upper(f.name) like upper(:facilityName) ";

	private static final String FACILITY_NAME_SEARCH_EXCLUDE_CLAUSE = " and f.id != :excludeFacilityId ";
	private static final String FACILITY_NAME_SEARCH_ORDER_BY = " order by initcap(f.name) asc";

	private static final String HISTORY_QUERY = "from FacilityEventView fev where fev.facilityId = :facilityId ";
	private static final String HISTORY_TYPES_CHECK = " and fev.primaryKey.eventType in (:eventTypes) ";
	private static final String HISTORY_DATE_FIELD = "fev.eventDate";

	private static final String FILE_CHECK_QUERY = "from FileCheckView fcv where fcv.facilityId = :facilityId ";
	private static final String FILE_CHECK_DATE_FIELD = "fcv.inspectionDate";
	private static final String FILE_CHECK_ORDER_BY_CLAUSE = " order by fcv.inspectionDate desc ";

	private static final String FACILITY_WITH_SEARCH_VIEW_QUERY = "from Facility f left join fetch f.searchView where f.id = :facilityId ";

	private static final String CASELOAD_QUERY = "from BasicFacilityInformation fac where fac.licensingSpecialist.id = :specialistId and (fac.status = 'REGULATED' or fac.status = 'IN_PROCESS') ";

	private static final String CASELOAD_COUNT_QUERY = "select pers.id as \"id\", pers.firstname || ' ' || pers.lastname as \"name\", ur.role_name as \"roleType\","
			+ "     su.active as \"active\", count(distinct fac.id) as \"count\""
			+ " from security_user su inner join person pers on su.personid = pers.id "
			+ "     inner join user_role ur on su.id = ur.user_id and (ur.role_name = 'ROLE_LICENSOR_SPECIALIST') "
			+ "     left outer join ( "
			+ "         select fac.id, fls.specialist_id, 'ROLE_LICENSOR_SPECIALIST' as role_name "
			+ "         from facility fac, facility_licensing_specialist fls "
			+ "         where fac.id = fls.facility_id and (fac.status = 'IN_PROCESS' or fac.status = 'REGULATED') "
			+ "     ) fac on pers.id = fac.specialist_id and ur.role_name = fac.role_name"
			+ " group by pers.id, pers.firstname, pers.lastname, ur.role_name, su.active order by pers.firstname, pers.lastname ";

	private static final String ALERT_EXPIRED_AND_EXPIRING_LICENSES_QUERY = "select eelv from ExpiredAndExpiringLicenseView eelv left join "
			+ " fetch eelv.applicationReceivedAction left join fetch eelv.facility fac ";
	private static final String ALERT_NEW_APPLICATION_PENDING_DEADLINES_QUERY = "select napdv from NewApplicationPendingDeadlineView napdv "
			+ " left join fetch napdv.facility fac ";
	private static final String ALERT_EXPIRING_ACCREDITATIONS_QUERY = "select aev from AccreditationExpiringView aev left join fetch "
			+ "     aev.facility fac ";
	private static final String ALERT_CONDITIONAL_FACILITIES_QUERY = "select cfv from ConditionalFacilityView cfv left join fetch cfv.facility fac ";
	private static final String ALERT_ANNOUNCED_INSPECTIONS_NEEDED_QUERY = "select ainv from AnnouncedInspectionNeededView ainv left join fetch "
			+ " ainv.facility fac ";
	private static final String ALERT_UNANNOUNCED_INSPECTIONS_NEEDED_QUERY = "select uinv from UnannouncedInspectionNeededView uinv left join fetch "
			+ " uinv.facility fac ";
	private static final String ALERT_FOLLOW_UP_INSPECTIONS_NEEDED_QUERY = "select view from FollowUpsNeededView view "
			+ "     left join fetch view.facility fac left join fetch view.findings ";
	private static final String ALERT_COMPL_FOLLOW_UPS_NEEDED_QUERY = "select view from ComplaintFollowUpsNeededView view "
			+ "     left join fetch view.facility fac left join fetch view.findings ";
	private static final String ALERT_WORK_IN_PROGRESS_QUERY = "select wipv from WorkInProgressView wipv left join fetch wipv.facility fac "
			+ " where wipv.owner.id = :personId";
	private static final String ALERT_OUTSTANDING_CMPS_QUERY = "select ocv from OutstandingCmpView ocv left join fetch ocv.facility fac "
			+ " left join fetch fac.licensingSpecialist ";
	private static final String ALERT_EXEMPT_VERIFICATIONS_QUERY = "select evv from ExemptVerificationView evv left join fetch evv.facility fac ";

	private static final String RECIPIENT_CLAUSE = " where view.recipient.id in (:recipientIds) ";

	private static final String WHOLE_REGION_CLAUSE = " where fac.region.officeSpecialist.id = :personId ";
	private static final String SINGLE_PERSON_CLAUSE = " where fac.licensingSpecialist.id = :personId ";

	private static final String DEACTIVATION_FACILITY_TAGS_QUERY = "from FacilityTag ft where ft.tag in (:deactivationReasons) and trunc(ft.startDate, 'dd') <= trunc(current_date(), 'dd') ";

	private static final String FACILITIES_NEEDING_DEACTIVATED_QUERY = "from Facility f where f.status = 'EXEMPT' and id not in ("
			+ " select distinct ex.facility.id from Exemption ex where trunc(current_date(), 'dd') between trunc(ex.startDate, 'dd') and trunc(ex.expirationDate, 'dd')) ";

	private static final String EREP_EXPORT_QUERY = "from ErepView ev order by ev.id ";

	private static final String FACILITY_CONTACT_QUERY = "from FacilityContactView fcv where fcv.personId = :personId ";

	private static final String FACILITY_CONTACT_QUERY_BY_ID = "from FacilityContactView fcv where fcv.id = :id ";
	private static final String FACILITY_CONTACT_QUERY_ALL = "from FacilityContactView fcv  ";
	
	private static final String OPEN_APPLICATIONS_BY_SPECIALIST_QUERY = "from SortableFacilityView sfv join fetch sfv.facility f "
	        + " where f.licensingSpecialist.id = :specialistId and f.status = :inProcessStatus "
	        + " order by sfv.facilityName asc";

	private static final String FACILITY_LICENSE_SUMMARY_QUERY = "from FacilityLicenseView flv join fetch flv.facility f "
			+ " where f.licensingSpecialist.id = :specialistId and flv.status = 'Active' ";

	private static final String FACILITY_LICENSE_DETAIL_QUERY = "from FacilityLicenseView flv join fetch flv.facility f "
			+ " where f.licensingSpecialist.id = :specialistId and flv.status = 'Active' ";
	
	private static final String EXPIRATION_DATE_CHECK = " and trunc(flv.expirationDate, 'DD') = trunc(:licenseExpEnd) ";

    private static final String FACILITY_LICENSE_DETAIL_SORT_CLAUSE = " order by flv.facilityName asc, flv.expirationDate desc, flv.serviceCode";

	private static final String EXPIRING_LICENSES_BY_SPECIALIST_QUERY = "from FacilityLicenseView flv join fetch flv.facility f " 
			+ " where f.licensingSpecialist.id = :specialistId and flv.status = 'Active' "
			+ " and trunc(flv.expirationDate, 'DD') <= trunc(:licenseExpEnd) "
			+ " order by flv.facilityName asc, flv.expirationDate desc, flv.serviceCode";

	private static final String RENEWAL_LICENSES_BY_SPECIALIST_QUERY = "from FacilityLicenseView flv join fetch flv.facility f " 
			+ " where f.licensingSpecialist.id = :specialistId and flv.status = 'Active' "
			+ " and trunc(flv.expirationDate, 'DD') = trunc(:licenseExpEnd) "
			+ " order by flv.facilityName asc, flv.expirationDate desc, flv.serviceCode";

	private static final String FOSTER_CARE_RENEWAL_LICENSES_BY_SPECIALIST_QUERY = "from FacilityLicenseView flv join fetch flv.facility f " 
			+ " where f.licensingSpecialist.id = :specialistId and f.type in ('F','S') and flv.status = 'Active' "
			+ " and trunc(flv.expirationDate, 'DD') = trunc(:licenseExpEnd) "
			+ " order by flv.facilityName asc, flv.expirationDate desc, flv.serviceCode";
	
	private static final String PREVIOUS_LICENSE_NUMBERS = "select new gov.utah.dts.det.ccl.view.KeyValuePair(l.id, to_char(l.id)) from License l order by l.id";

	@PersistenceContext
	private EntityManager em;

	public FacilityDaoImpl() {
		super(Facility.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Facility loadFacilityByIdNumber(String idNumber) {
		Query query = em.createQuery(FACILITY_BY_FACILITY_ID_QUERY);
		query.setParameter("idNumber", idNumber);

		Facility facility = null;
		try {
			facility = (Facility) query.getSingleResult();
		} catch (NoResultException nre) {
			// swallow exception and return null;
		}

		return facility;
	}

	@Override
	public License loadLicenseById(Long id) {
		Query query = em.createQuery(LICENSE_BY_ID_QUERY);
		query.setParameter("id", id);

		License license = null;
		try {
			license = (License) query.getSingleResult();
		} catch (NoResultException nre) {
			// swallow exception and return null;
		}

		return license;
	}

	@Override
	public List<FacilityPerson> loadFacilityPeople(Long facilityId, List<PickListValue> peopleTypes) {
		StringBuilder sb = new StringBuilder(FACILITY_PEOPLE_QUERY);
		if (peopleTypes != null && !peopleTypes.isEmpty()) {
			sb.append(FACILITY_PEOPLE_PERSON_TYPE_CLAUSE);
		}
		sb.append(FACILITY_PEOPLE_ORDER_BY_CLAUSE);

		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		if (peopleTypes != null && !peopleTypes.isEmpty()) {
			query.setParameter("peopleTypes", peopleTypes);
		}

		return (List<FacilityPerson>) query.getResultList();
	}

	@Override
	public FacilityPerson loadFacilityPerson(Long facilityId, Long facilityPersonId, Long firstType, Long secondType) {
		Query query = em.createQuery(FACILITY_PERSON_QUERY);
		query.setParameter("facilityId", facilityId);
		query.setParameter("facilityPersonId", facilityPersonId);
		query.setParameter("firstType", firstType);
		query.setParameter("secondType", secondType);

		return (FacilityPerson) query.getSingleResult();
	}

	@Override
	public FacilityPerson loadFacilityBoardMember(Long facilityId, Long facilityPersonId, Long facilityPersonType) {
		Query query = em.createQuery(FACILITY_BOARD_MEMBER_QUERY);
		query.setParameter("facilityId", facilityId);
		query.setParameter("facilityPersonId", facilityPersonId);
		query.setParameter("facilityPersonType", facilityPersonType);

		return (FacilityPerson) query.getSingleResult();
	}

	@Override
	public List<TrackingRecordScreeningApprovalsView> loadScreenedPeopleForFacility(Long facilityId, boolean excludeDirectors, boolean excludeContacts) {
		StringBuilder sb = new StringBuilder();
		sb.append(SCREENED_PEOPLE_QUERY);
		if (excludeDirectors) {
			sb.append(EXCLUDE_DIRECTORS_CLAUSE);
		}
		if (excludeContacts) {
			sb.append(EXCLUDE_CONTACTS_CLAUSE);
		}
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);

		return (List<TrackingRecordScreeningApprovalsView>) query.getResultList();
	}

	@Override
	public TrackingRecordScreeningApprovalsView loadScreenedPersonForFacility(Long facilityId, Long personId) {
		StringBuilder sb = new StringBuilder(SCREENED_PEOPLE_QUERY);
		sb.append(SCREENED_PEOPLE_PERSON_CLAUSE);

		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		query.setParameter("personId", personId);

		return (TrackingRecordScreeningApprovalsView) query.getSingleResult();
	}

	@Override
	public List<FacilitySearchView> searchFacilities(FacilitySearchCriteria criteria, SortBy sortBy, int page, int resultsPerPage) {
		StringBuilder sb = new StringBuilder(FACILITY_SEARCH_QUERY);
		buildSearchQueryString(sb, criteria, sortBy);

		Query query = buildSearchQuery(sb.toString(), criteria);

		int maxResults = resultsPerPage == 0 ? 250 : resultsPerPage;
		int firstResult = page * resultsPerPage;
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		
		List<FacilitySearchView> facilities = query.getResultList();
		for (FacilitySearchView f : facilities) {
			f.getLicenses().size();
		}

		return facilities;
	}

	@Override
	public int searchFacilitiesCount(FacilitySearchCriteria criteria) {
		StringBuilder sb = new StringBuilder(FACILITY_SEARCH_COUNT_QUERY);
		buildSearchQueryString(sb, criteria, null);

		Query query = buildSearchQuery(sb.toString(), criteria);

		Long results = (Long) query.getSingleResult();
		return results.intValue();
	}

	public String getReportCodes(Long serviceCode, String columnName) {
		String sql = "select " + columnName + " from report_code_lkup where service_code = " + serviceCode;
		Query query = em.createNativeQuery(sql);
	  
		return (String) query.getSingleResult();
	}

	private void buildSearchQueryString(StringBuilder queryString, FacilitySearchCriteria criteria, SortBy sortBy) {
		boolean criteriaSelected = false;
		if (StringUtils.isNotBlank(criteria.getCounty()) || StringUtils.isNotBlank(criteria.getCity()) || StringUtils.isNotBlank(criteria.getZipCode())) {
			queryString.append(", Address ma");
			if (StringUtils.isNotBlank(criteria.getCounty())) {
				queryString.append(", Location loc");
			}
		}
		if (StringUtils.isNotBlank(criteria.getLicenseeName()) ||
			(criteria.getLicenseTypeIds() != null && criteria.getLicenseTypeIds().size() > 0) ||
			(criteria.getLicenseStatusIds() != null && criteria.getLicenseStatusIds().size() > 0) ||
			(criteria.getLicenseServiceCodeIds() != null && criteria.getLicenseServiceCodeIds().size() > 0) ||
			criteria.getLicenseExpRangeStart() != null ||
			criteria.getLicenseExpRangeEnd() != null
		) {
			queryString.append(", License lic");
		}
		if (criteria.isConditional()) {
			queryString.append(", FacilityTag facilityTag");
		}
		if (criteria.getExemptionIds() != null && criteria.getExemptionIds().size() > 0) {
			queryString.append(", Exemption ex");
		}
		if (criteria.getInactiveReasonIds() != null && criteria.getInactiveReasonIds().size() > 0) {
			queryString.append(", ActionLog al");
		}
		
		queryString.append(" where f.id = f.id");

		// Add facility name filter
		if (StringUtils.isNotBlank(criteria.getFacilityName())) {
			queryString.append(" and upper(f.name) like upper(:facilityName)");
			criteriaSelected = true;
		}
		
		// Add site name filter
		if (StringUtils.isNotBlank(criteria.getSiteName())) {
			queryString.append(" and upper(f.siteName) like upper(:siteName)");
			criteriaSelected = true;			
		}

		// Add Primary Phone filter
		if (StringUtils.isNotBlank(criteria.getPrimaryPhone())) {
			queryString.append(" and f.primaryPhone.phoneNumber = :primaryPhone");
			criteriaSelected = true;
		}

		// Add Facility Owner filter
		if (StringUtils.isNotBlank(criteria.getOwnerName())) {
			queryString.append(" and upper(f.ownerName) like upper(:ownerName)");
			criteriaSelected = true;
		}

		// Add Region filter
		if (criteria.getRegionId() != null) {
			queryString.append(" and f.region.id = :regionId");
			criteriaSelected = true;
		}
		
		// Add County, City, and ZipCode filters
		if (StringUtils.isNotBlank(criteria.getCounty()) || StringUtils.isNotBlank(criteria.getCity()) || StringUtils.isNotBlank(criteria.getZipCode())) {
			queryString.append(" and ma.id = f.mailingAddress.id");
			if (StringUtils.isNotBlank(criteria.getCounty())) {
				//  Get the proper location for address city, state and zipcode
				queryString.append(" and upper(loc.city) = upper(ma.city) and upper(loc.state) = upper(ma.state) and loc.zipCode = ma.zipCode");
				queryString.append(" and upper(loc.county) like upper(:county)");
				criteriaSelected = true;
			}
	
			if (StringUtils.isNotBlank(criteria.getCity())) {
				queryString.append(" and upper(ma.city) like upper(:city)");
				criteriaSelected = true;
			}
	
			if (StringUtils.isNotBlank(criteria.getZipCode())) {
				queryString.append(" and ma.zipCode like :zipCode");
				criteriaSelected = true;
			}
		}

		// Add Facility Type filter
		if (criteria.getFacilityType() != null) {
			queryString.append(" and f.type = :facilityType");
			criteriaSelected = true;
		}
		
		// Add Facility Status filter
		if (criteria.getFacilityStatus() != null) {
			queryString.append(" and f.status = :facilityStatus");
			criteriaSelected = true;
		}

		if (StringUtils.isNotBlank(criteria.getLicenseeName()) ||
			(criteria.getLicenseTypeIds() != null && criteria.getLicenseTypeIds().size() > 0) ||
			(criteria.getLicenseStatusIds() != null && criteria.getLicenseStatusIds().size() > 0) ||
			(criteria.getLicenseServiceCodeIds() != null && criteria.getLicenseServiceCodeIds().size() > 0) ||
			criteria.getLicenseExpRangeStart() != null ||
			criteria.getLicenseExpRangeEnd() != null
		) {
			queryString.append(" and lic.facility.id = f.id");
		}

		// Add Licensor filter
		if (StringUtils.isNotBlank(criteria.getLicenseeName())) {
			queryString.append(" and upper(lic.licenseHolderName) like upper(:licenseeName) ");
			criteriaSelected = true;
		}

		// Add License Type(s) filter
		if (criteria.getLicenseTypeIds() != null && criteria.getLicenseTypeIds().size() > 0) {
			queryString.append(" and lic.subtype.id in (:licenseTypeIds)");
			criteriaSelected = true;
		}

		// Add License status(es) filter
		if (criteria.getLicenseStatusIds() != null && criteria.getLicenseStatusIds().size() > 0) {
			queryString.append(" and lic.status.id in (:licenseStatusIds)");
			criteriaSelected = true;
		}

		// Add License serviceCode(s) filter
		if (criteria.getLicenseServiceCodeIds() != null && criteria.getLicenseServiceCodeIds().size() > 0) {
			queryString.append(" and lic.serviceCode.id in (:licenseServiceCodeIds)");
			criteriaSelected = true;
		}

		// Add current license date range filter(s)
		if (criteria.getLicenseExpRangeStart() != null && criteria.getLicenseExpRangeEnd() != null) {
			queryString.append(" and (trunc(lic.expirationDate, 'DD') between trunc(:licenseExpRangeStart) and trunc(:licenseExpRangeEnd))");
			criteriaSelected = true;
		} else if (criteria.getLicenseExpRangeStart() != null) {
			queryString.append(" and trunc(lic.expirationDate, 'DD') >= :licenseExpRangeStart");
			criteriaSelected = true;
		} else if (criteria.getLicenseExpRangeEnd() != null) {
			queryString.append(" and trunc(lic.expirationDate, 'DD') <= :licenseExpRangeEnd");
			criteriaSelected = true;
		}

		// Process Is Conditional Facility TAG filter
		if (criteria.isConditional()) 
		{
			queryString.append(" and facilityTag.facility.id = f.id and facilityTag.tag.value = 'Conditional'");
			criteriaSelected = true;
		}


		// Add facility licensing specialist filter
		if (criteria.getLicensingSpecialistId() != null) {
			queryString.append(" and f.licensingSpecialist.id = :licensingSpecialistId");
			criteriaSelected = true;
		}

		// Add exemption reasons filter
		if (criteria.getExemptionIds() != null && criteria.getExemptionIds().size() > 0) {
			queryString.append(" and ex.facility.id = f.id and trunc(current_date, 'DD') between ex.startDate and ex.expirationDate");
			queryString.append(" and ex.exemption.id in (:exemptionIds)");
			criteriaSelected = true;
		}

		// Add inactive reasons filter
		if (criteria.getInactiveReasonIds() != null && criteria.getInactiveReasonIds().size() > 0) {
			queryString.append(" and al.facility.id = f.id and al.actionType.id in (:inactiveReasonIds)");
			criteriaSelected = true;
		}

		queryString.append(")");

		if (!criteriaSelected) {
			throw new SearchException("No criteria selected for search.");
		}

		ServiceUtils.addSortByClause(queryString, sortBy, null);
	}

	private Query buildSearchQuery(String queryString, FacilitySearchCriteria criteria) {
		Query query = em.createQuery(queryString);
		// set parameters
		if (StringUtils.isNotBlank(criteria.getFacilityName())) {
			String facName = criteria.getFacilityName().toUpperCase();
			if (criteria.getNameSearchType() == null || criteria.getNameSearchType() == FacilitySearchCriteria.NameSearchType.STARTS_WITH) {
				facName = facName + "%";
			} else {
				//Mean's FacilitySearchCriteria.NameSearchType.ANY_PART
				facName = "%" + facName + "%";
			}
			query.setParameter("facilityName", facName);
		}
		if (StringUtils.isNotBlank(criteria.getSiteName())) {
			String siteName = criteria.getSiteName().toUpperCase();
			if (criteria.getSiteNameSearchType() == null || criteria.getSiteNameSearchType() == FacilitySearchCriteria.NameSearchType.STARTS_WITH) {
				siteName = siteName + "%";
			} else {
				//Mean's FacilitySearchCriteria.NameSearchType.ANY_PART
				siteName = "%" + siteName + "%";
			}
			query.setParameter("siteName", siteName);
		}
		if (StringUtils.isNotBlank(criteria.getPrimaryPhone())) {
			query.setParameter("primaryPhone", criteria.getPrimaryPhone());
		}
		if (StringUtils.isNotBlank(criteria.getOwnerName())) {
			query.setParameter("ownerName",  "%"+criteria.getOwnerName()+"%");
		}
		if (criteria.getRegionId() != null) {
			query.setParameter("regionId", criteria.getRegionId());
		}
		if (StringUtils.isNotBlank(criteria.getCounty())) {
			query.setParameter("county", criteria.getCounty()+"%");
		}
		if (StringUtils.isNotBlank(criteria.getCity())) {
			query.setParameter("city", criteria.getCity()+"%");
		}
		if (StringUtils.isNotBlank(criteria.getZipCode())) {
			query.setParameter("zipCode", criteria.getZipCode()+"%");
		}
		if (criteria.getFacilityType() != null) {
			query.setParameter("facilityType", criteria.getFacilityType());
		}
		if (criteria.getFacilityStatus() != null) {
			query.setParameter("facilityStatus", criteria.getFacilityStatus());
		}
		if (StringUtils.isNotBlank(criteria.getLicenseeName())) {
			query.setParameter("licenseeName", "%"+criteria.getLicenseeName()+"%");
		}
		if (criteria.getLicenseTypeIds() != null && criteria.getLicenseTypeIds().size() > 0) {
			query.setParameter("licenseTypeIds", criteria.getLicenseTypeIds());
		}
		if (criteria.getLicenseStatusIds() != null && criteria.getLicenseStatusIds().size() > 0) {
			query.setParameter("licenseStatusIds", criteria.getLicenseStatusIds());
		}
		if (criteria.getLicenseServiceCodeIds() != null && criteria.getLicenseServiceCodeIds().size() > 0) {
			query.setParameter("licenseServiceCodeIds", criteria.getLicenseServiceCodeIds());
		}
		if (criteria.getLicenseExpRangeStart() != null) {
			query.setParameter("licenseExpRangeStart", criteria.getLicenseExpRangeStart());
		}
		if (criteria.getLicenseExpRangeEnd() != null) {
			query.setParameter("licenseExpRangeEnd", criteria.getLicenseExpRangeEnd());
		}
		if (criteria.getLicensingSpecialistId() != null) {
			query.setParameter("licensingSpecialistId", criteria.getLicensingSpecialistId());
		}
		if (criteria.getExemptionIds() != null && criteria.getExemptionIds().size() > 0) {
			query.setParameter("exemptionIds", criteria.getExemptionIds());
		}
		if (criteria.getInactiveReasonIds() != null && criteria.getInactiveReasonIds().size() > 0) {
			query.setParameter("inactiveReasonIds", criteria.getInactiveReasonIds());
		}
		
		return query;
	}

	@Override
	public FacilityResultView getFacilityResultView(Long facilityId) {
		Query query = em.createQuery(FACILITY_RESULT_VIEW_QUERY);
		query.setParameter("facilityId", facilityId);
		FacilityResultView frv = (FacilityResultView) query.getSingleResult();
		return frv;
	}

	@Override
	public List<FacilityResultView> searchFacilitiesByName(String name, Long excludeFacilityId) {
		StringBuilder sb = new StringBuilder(FACILITY_NAME_SEARCH_QUERY);
		if (excludeFacilityId != null) {
			sb.append(FACILITY_NAME_SEARCH_EXCLUDE_CLAUSE);
		}
		sb.append(FACILITY_NAME_SEARCH_ORDER_BY);

		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityName", name.toUpperCase() + "%");
		if (excludeFacilityId != null) {
			query.setParameter("excludeFacilityId", excludeFacilityId);
		}
		query.setMaxResults(20);
		List<FacilityResultView> frvs = query.getResultList();
		return frvs;
	}

	@Override
	public List<FacilityEventView> getFacilityHistory(Long facilityId, ListRange listRange, SortBy sortBy, List<FacilityEventType> eventTypes) {
		StringBuilder sb = new StringBuilder(HISTORY_QUERY);
		ServiceUtils.addIntervalClause(HISTORY_DATE_FIELD, sb, listRange);
		if (eventTypes != null && eventTypes.size() > 0) {
			sb.append(HISTORY_TYPES_CHECK);
		}
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		if (eventTypes != null && eventTypes.size() > 0) {
			query.setParameter("eventTypes", eventTypes);
		}

		return (List<FacilityEventView>) query.getResultList();
	}

	@Override
	public List<FileCheckView> getFileCheck(Long facilityId, ListRange listRange) {
		StringBuilder sb = new StringBuilder(FILE_CHECK_QUERY);
		ServiceUtils.addIntervalClause(FILE_CHECK_DATE_FIELD, sb, listRange);
		sb.append(FILE_CHECK_ORDER_BY_CLAUSE);

		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);

		return (List<FileCheckView>) query.getResultList();
	}

	@Override
	public Facility loadFacilityWithSearchViewById(Long facilityId) {
		Query query = em.createQuery(FACILITY_WITH_SEARCH_VIEW_QUERY);
		query.setParameter("facilityId", facilityId);

		return (Facility) query.getSingleResult();
	}

	@Override
	public List<BasicFacilityInformation> getCaseload(Person licensingSpecialist) {
		Query query = em.createQuery(CASELOAD_QUERY);
		query.setParameter("specialistId", licensingSpecialist.getId());
		return (List<BasicFacilityInformation>) query.getResultList();
	}

	@Override
	public List<FacilityCaseloadView> getUserCaseload(Long specialistId, RoleType roleType, SortBy sortBy) {
		if (roleType == null || (roleType != RoleType.ROLE_LICENSOR_SPECIALIST)) {
			throw new IllegalArgumentException("Can only pull caseloads for licensing specialists");
		}
		StringBuilder sb = new StringBuilder("from FacilityCaseloadView fcv left join fetch fcv.licensingSpecialist ls where fcv.");
		if (roleType == RoleType.ROLE_LICENSOR_SPECIALIST) {
			sb.append("licensing");
		}
		sb.append("Specialist.id = :specialistId ");
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("specialistId", specialistId);
		return (List<FacilityCaseloadView>) query.getResultList();
	}

	@Override
	public List<UserCaseloadCount> getUserCaseloadCounts() {
		Session session = (Session) em.getDelegate();
		SQLQuery query = session.createSQLQuery(CASELOAD_COUNT_QUERY);
		query.addScalar("id", StandardBasicTypes.LONG);
		query.addScalar("name", StandardBasicTypes.STRING);
		query.addScalar("roleType", StandardBasicTypes.STRING);
		query.addScalar("active", StandardBasicTypes.YES_NO);
		query.addScalar("count", StandardBasicTypes.LONG);
		query.setResultTransformer(Transformers.aliasToBean(UserCaseloadCount.class));
		return (List<UserCaseloadCount>) query.list();
	}

	@Override
	public List<ExpiredAndExpiringLicenseView> getExpiredAndExpiringLicenses(Long personId, boolean showWholeRegion, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_EXPIRED_AND_EXPIRING_LICENSES_QUERY);
		appendPersonClause(sb, showWholeRegion);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);

		return (List<ExpiredAndExpiringLicenseView>) query.getResultList();
	}

	@Override
	public List<NewApplicationPendingDeadlineView> getNewApplicationPendingDeadlines(Long personId, boolean showWholeRegion, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_NEW_APPLICATION_PENDING_DEADLINES_QUERY);
		appendPersonClause(sb, showWholeRegion);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);

		return (List<NewApplicationPendingDeadlineView>) query.getResultList();
	}

	@Override
	public List<AccreditationExpiringView> getExpiringAccreditations(Long personId, boolean showWholeRegion, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_EXPIRING_ACCREDITATIONS_QUERY);
		appendPersonClause(sb, showWholeRegion);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);

		return (List<AccreditationExpiringView>) query.getResultList();
	}

	@Override
	public List<ConditionalFacilityView> getFacilitiesOnConditionalLicenses(Long personId, boolean showWholeRegion, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_CONDITIONAL_FACILITIES_QUERY);
		appendPersonClause(sb, showWholeRegion);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);

		return (List<ConditionalFacilityView>) query.getResultList();
	}

	@Override
	public List<AnnouncedInspectionNeededView> getAnnouncedInspectionsNeeded(Long personId, boolean showWholeRegion, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_ANNOUNCED_INSPECTIONS_NEEDED_QUERY);
		appendPersonClause(sb, showWholeRegion);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);

		return (List<AnnouncedInspectionNeededView>) query.getResultList();
	}

	@Override
	public List<UnannouncedInspectionNeededView> getUnannouncedInspectionsNeeded(Long personId, boolean showWholeRegion, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_UNANNOUNCED_INSPECTIONS_NEEDED_QUERY);
		appendPersonClause(sb, showWholeRegion);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);

		return (List<UnannouncedInspectionNeededView>) query.getResultList();
	}

	@Override
	public List<AlertFollowUpsNeededView> getFollowUpInspectionsNeeded(Set<Long> recipientIds, String role, SortBy sortBy, boolean fetchFacility) {
		StringBuilder sb = new StringBuilder();
		if ("COMPL".equals(role)) {
			sb.append(ALERT_COMPL_FOLLOW_UPS_NEEDED_QUERY);
		} else if ("NORM".equals(role)) {
			sb.append(ALERT_FOLLOW_UP_INSPECTIONS_NEEDED_QUERY);
		}
		sb.append(RECIPIENT_CLAUSE);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("recipientIds", recipientIds);

		Set<AlertFollowUpsNeededView> followUps = new LinkedHashSet<AlertFollowUpsNeededView>((List<AlertFollowUpsNeededView>) query.getResultList());
		List<AlertFollowUpsNeededView> followUpsList = new ArrayList<AlertFollowUpsNeededView>(followUps);
		return followUpsList;
	}

	@Override
	public List<WorkInProgressView> getWorkInProgress(Long personId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_WORK_IN_PROGRESS_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);

		return (List<WorkInProgressView>) query.getResultList();
	}

	@Override
	public List<OutstandingCmpView> getOutstandingCmps(Long personId, boolean showWholeRegion) {
		StringBuilder sb = new StringBuilder(ALERT_OUTSTANDING_CMPS_QUERY);
		appendPersonClause(sb, showWholeRegion);

		Query query = em.createQuery(sb.toString());
		query.setParameter("personId", personId);

		return (List<OutstandingCmpView>) query.getResultList();
	}

	@Override
	public List<ExemptVerificationView> getExemptVerifications(SortBy sortBy) {
		StringBuilder sb = new StringBuilder(ALERT_EXEMPT_VERIFICATIONS_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);

		Query query = em.createQuery(sb.toString());

		return (List<ExemptVerificationView>) query.getResultList();
	}

	@SuppressWarnings("unused")
	private void appendFacilityFetch(StringBuilder sb, boolean fetchFacility) {
		if (fetchFacility) {
			sb.append(" left join fetch view.facility fac ");
		}
	}

	private void appendPersonClause(StringBuilder sb, boolean showWholeRegion) {
		if (showWholeRegion) {
			sb.append(WHOLE_REGION_CLAUSE);
		} else {
			sb.append(SINGLE_PERSON_CLAUSE);
		}
	}

	@Override
	public List<FacilityContactView> getContactFacilities(Long personId, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(personId != null ? FACILITY_CONTACT_QUERY : FACILITY_CONTACT_QUERY_ALL);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		Query query = em.createQuery(sb.toString());
		if (personId != null) {
			query.setParameter("personId", personId);
		}
		return (List<FacilityContactView>) query.getResultList();
	}

	public FacilityContactView loadContactFacilityById(Long id) {
		StringBuilder sb = new StringBuilder(FACILITY_CONTACT_QUERY_BY_ID);
		Query query = em.createQuery(sb.toString());
		if (id != null) {
			query.setParameter("id", id);
		}
		return (FacilityContactView) query.getSingleResult();
	}

	@Override
	public List<FacilityTag> getDeactivationFacilityTags(List<PickListValue> deactivationReasons) {
		Query query = em.createQuery(DEACTIVATION_FACILITY_TAGS_QUERY);
		query.setParameter("deactivationReasons", deactivationReasons);
		return (List<FacilityTag>) query.getResultList();
	}

	@Override
	public List<Facility> getFacilitiesToDeactivate() {
		Query query = em.createQuery(FACILITIES_NEEDING_DEACTIVATED_QUERY);
		return (List<Facility>) query.getResultList();
	}

	@Override
	public List<ErepView> getErepViews() {
		Query query = em.createQuery(EREP_EXPORT_QUERY);
		return (List<ErepView>) query.getResultList();
	}
	
	@Override
	public List<SortableFacilityView> getOpenApplicationsBySpecialist(Long specialistId) {
		Query query = em.createQuery(OPEN_APPLICATIONS_BY_SPECIALIST_QUERY);
		query.setParameter("specialistId", specialistId);
		query.setParameter("inProcessStatus", FacilityStatus.IN_PROCESS);
		return (List<SortableFacilityView>) query.getResultList();
	}
	
	@Override
	public List<FacilityLicenseView> getFacilityLicenseSummary(Long specialistId, Date expDate, SortBy sortBy) {
		StringBuilder sb = new StringBuilder(FACILITY_LICENSE_SUMMARY_QUERY);
		if (expDate != null) {
			sb.append(EXPIRATION_DATE_CHECK);
		}
		ServiceUtils.addSortByClause(sb, sortBy, null);
		Query query = em.createQuery(sb.toString());
		query.setParameter("specialistId", specialistId);
		if (expDate != null) {
			query.setParameter("licenseExpEnd", expDate);
		}
		return (List<FacilityLicenseView>) query.getResultList();
	}

	@Override
	public List<FacilityLicenseView> getFacilityLicenseDetail(Long specialistId, Date expDate) {
		StringBuilder sb = new StringBuilder(FACILITY_LICENSE_DETAIL_QUERY);
		if (expDate != null) {
			sb.append(EXPIRATION_DATE_CHECK);
		}
		sb.append(FACILITY_LICENSE_DETAIL_SORT_CLAUSE);
		Query query = em.createQuery(sb.toString());
		query.setParameter("specialistId", specialistId);
		if (expDate != null) {
			query.setParameter("licenseExpEnd", expDate);
		}
		return (List<FacilityLicenseView>) query.getResultList();
	}

	@Override
	public List<FacilityLicenseView> getExpiringLicensesBySpecialist(Date expDate, Long specialistId) {
		Query query = em.createQuery(EXPIRING_LICENSES_BY_SPECIALIST_QUERY);
		query.setParameter("specialistId", specialistId);
		query.setParameter("licenseExpEnd", expDate);
		return (List<FacilityLicenseView>) query.getResultList();
	}

	@Override
	public List<FacilityLicenseView> getRenewalLicensesBySpecialist(Date expDate, Long specialistId) {
		Query query = em.createQuery(RENEWAL_LICENSES_BY_SPECIALIST_QUERY);
		query.setParameter("specialistId", specialistId);
		query.setParameter("licenseExpEnd", expDate);
		return (List<FacilityLicenseView>) query.getResultList();
	}

	@Override
	public List<FacilityLicenseView> getFosterCareRenewalLicensesBySpecialist(Date expDate, Long specialistId) {
		Query query = em.createQuery(FOSTER_CARE_RENEWAL_LICENSES_BY_SPECIALIST_QUERY);
		query.setParameter("specialistId", specialistId);
		query.setParameter("licenseExpEnd", expDate);
		return (List<FacilityLicenseView>) query.getResultList();
	}
	
	public List<KeyValuePair> findAllPreviousLicenseNumbers() {
		Query query = em.createQuery(PREVIOUS_LICENSE_NUMBERS);
		return (List<KeyValuePair>) query.getResultList();
	}
	
}