package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.InspectionDao;
import gov.utah.dts.det.ccl.model.Finding;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.model.enums.CorrectionVerificationType;
import gov.utah.dts.det.ccl.model.view.InspectionView;
import gov.utah.dts.det.ccl.model.view.NoncomplianceFindingView;
import gov.utah.dts.det.ccl.model.view.NoncomplianceView;
import gov.utah.dts.det.ccl.service.RuleViolation;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.service.ApplicationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("inspectionDao")
public class InspectionDaoImpl extends AbstractBaseDaoImpl<Inspection, Long> implements InspectionDao {
	
	private static final String INSPECTION_VIEW_BY_ID_QUERY = "from InspectionView iv left join fetch iv.cmpTransactions cmps where iv.id = :inspectionId order by iv.inspectionDate desc";
	
	private static final String INSPECTION_HISTORY_QUERY = "from InspectionView iv where facilityId = :facilityId ";
	private static final String INSPECTION_HISTORY_FINALIZED_CLAUSE = " and iv.state = 'FINALIZED' ";
	private static final String INSPECTION_HISTORY_UNFINALIZED_CLAUSE = " and iv.state != 'FINALIZED' ";
	private static final String INSPECTION_DATE_FIELD = "iv.inspectionDate";

	private static final String COMPLIANCE_QUERY = "from NoncomplianceView ncv where ncv.facilityId = :facilityId and ncv.inspectionDate >= :date";
	
	private static final String NONCOMPLIANCE_FINDING_VIEW_QUERY = "from NoncomplianceFindingView nfv where nfv.facilityId = :facilityId order by nfv.findingType asc, nfv.sectionOrder asc, nfv.subsectionOrder asc, nfv.ruleNumber asc, nfv.inspectionDate asc";
	
//	private static final String COMPLIANCE_DATE_QUERY = " select case when last_announced_date > add_months(:date, -12) " +
//			"     then add_months(:date, -12) else last_announced_date end as previous_findings_date " +
//			" from (" +
//			"     select nvl(( " +
//			"         select max(ins.inspection_date) " +
//			"         from inspection ins, inspection_type it " +
//			"         where ins.facility_id = :facilityId " +
//			"             and ins.id = it.inspection_id " +
//			"             and it.inspection_type_id in ( " +
//			"                 with t as ( " +
//			"                     select ap.value AS txt " +
//			"                     from applicationproperty ap " +
//			"                     where ap.name = :type " +
//			"                 ) " +
//			"                 select to_number(regexp_substr(txt, '[^,]+', 1, rownum)) val " +
//			"                 from t " +
//			"                 connect by level <= length(regexp_replace(txt, '[^,]+')) + 1 " +
//			"             ) " +
//			"             and ins.inspection_date < :date " +
//			"     ), (" +
//			"         select min(inspection_date) " +
//			"         from inspection " +
//			"         where facility_id = :facilityId " +
//			"     )) as last_announced_date, :date " +
//			"     from dual " +
//			" )";
	
	private static final String INSPECTIONS_QUERY = " select ins from Inspection ins left join fetch ins.findings left join fetch ins.types " +
			" where ins.facility.id = :facilityId ";
	
	private static final String COMPLIANCE_DATE_QUERY = "select max(ins.inspectionDate) " +
			" from Inspection ins left join ins.types types " +
			" where ins.facility.id = :facilityId " +
			"     and ins.inspectionDate < :date " +
			"     and types.primaryKey.inspectionType in (:types) ";
	
	private static final String PREVIOUS_FINDINGS_FOR_RULE_QUERY = "select f " +
			" from Finding f join fetch f.inspection ins left join fetch ins.types " +
			" where ins.facility.id = :facilityId and ins.inspectionDate between :startDate and :endDate " +
			"     and ( " +
			"         f.rule.id = :ruleId or f.rule.id in ( " +
			"             select rcr.oldRule.id from RuleCrossReference rcr where rcr.newRule.id = :ruleId " +
			"         ) " +
			"     ) " +
			" order by ins.inspectionDate desc, ins.id";
	
	private static final String RULE_VIOLATIONS_QUERY = "select new gov.utah.dts.det.ccl.service.RuleViolation(ins.id, ins.inspectionDate, itv.primaryType," +
			"     itv.otherTypes, nclev.value, findCat.value, find.rescindedDate, find.appealDate) " +
			" from Finding find left join find.inspection ins left join find.noncomplianceLevel nclev left join " +
			"     find.findingCategory findCat, InspectionTypesView itv " +
			" where ins.facility.id = :facilityId and ins.id = itv.inspectionId and (find.rule.id = :ruleId or find.rule.id in ( " +
			"     select rcr.oldRule.id from RuleCrossReference rcr where rcr.newRule.id = :ruleId " +
			" )) " +
			" order by ins.inspectionDate desc ";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private ApplicationService applicationService;

	public InspectionDaoImpl() {
		super(Inspection.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public InspectionView loadInspectionViewById(Long inspectionId) {
		Query query = em.createQuery(INSPECTION_VIEW_BY_ID_QUERY);
		query.setParameter("inspectionId", inspectionId);
		
		return (InspectionView) query.getSingleResult();
	}
	
	@Override
	public void refreshInspectionView(InspectionView inspectionView) {
		em.refresh(inspectionView);
	}
	
	@Override
	public List<InspectionView> getInspectionsForFacility(Long facilityId, ListRange listRange, SortBy sortBy,
			boolean finalized) {
		StringBuilder sb = new StringBuilder(INSPECTION_HISTORY_QUERY);
		if (finalized) {
			sb.append(INSPECTION_HISTORY_FINALIZED_CLAUSE);
		} else {
			sb.append(INSPECTION_HISTORY_UNFINALIZED_CLAUSE);
		}
		ServiceUtils.addIntervalClause(INSPECTION_DATE_FIELD, sb, listRange);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		
		return (List<InspectionView>) query.getResultList();
	}
	
	@Override
	public List<Inspection> getInspectionsForFacility(Long facilityId, Date startDate, Date endDate) {
		StringBuilder sb = new StringBuilder(INSPECTIONS_QUERY);
		if (startDate != null && endDate != null) {
			sb.append(" and trunc(ins.inspectionDate, 'dd') between trunc(:startDate, 'dd') and trunc(:endDate, 'dd') ");
		} else if (startDate != null) {
			sb.append(" and trunc(ins.inspectionDate, 'dd') > trunc(:startDate, 'dd') ");
		} else if (endDate != null) {
			sb.append(" and trunc(ins.inspectionDate, 'dd') < trunc(:endDate, 'dd') ");
		}
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}
		
		Set<Inspection> inspections = new HashSet<Inspection>((List<Inspection>) query.getResultList());
		List<Inspection> sortedInsps = new ArrayList<Inspection>(inspections);
		Collections.sort(sortedInsps);
		
		return sortedInsps;
	}
	
	@Override
	public Set<Finding> getFindingsForRule(Long facilityId, Long ruleId, Date startDate, Date endDate) {
		Query query = em.createQuery(PREVIOUS_FINDINGS_FOR_RULE_QUERY);
		query.setParameter("facilityId", facilityId);
		query.setParameter("ruleId", ruleId);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		
		Set<Finding> findings = new HashSet<Finding>();
		findings.addAll((List<Finding>) query.getResultList());
		return findings;
	}
	
	@Override
	public List<RuleViolation> getRuleViolations(Long facilityId, Long ruleId) {
		Query query = em.createQuery(RULE_VIOLATIONS_QUERY);
		query.setParameter("facilityId", facilityId);
		query.setParameter("ruleId", ruleId);
		return (List<RuleViolation>) query.getResultList();
	}
	
	@Override
	public Date getComplianceDate(Long facilityId, Date date) {
		List<PickListValue> annualAnn = applicationService.getPickListValuesForApplicationProperty(ApplicationPropertyKey.ANNUAL_ANNOUNCED_TYPES.getKey());
		Query query = em.createQuery(COMPLIANCE_DATE_QUERY);
		query.setParameter("facilityId", facilityId);
		query.setParameter("date", date);
		query.setParameter("types", annualAnn);
		
		Date compDt = (Date) query.getSingleResult();
		if (compDt == null) {
			compDt = DateUtils.addYears(date, -2);
		} else if (compDt.compareTo(DateUtils.addYears(date, -1)) > 0) {
			compDt = DateUtils.addYears(date, -1);
		} else if (compDt.compareTo(DateUtils.addYears(date, -2)) < 0) {
			compDt = DateUtils.addYears(date, -2);
	}
		return compDt;
	}
	
	@Override
	public List<NoncomplianceView> getNonComplianceHistory(Long facilityId, SortBy sortBy) {
		Date startDate = getComplianceDate(facilityId, new Date());
		
		StringBuilder sb = new StringBuilder(COMPLIANCE_QUERY);
		ServiceUtils.addSortByClause(sb, sortBy, null);
		
		Query query = em.createQuery(sb.toString());
		query.setParameter("facilityId", facilityId);
		query.setParameter("date", startDate);
		
		return (List<NoncomplianceView>) query.getResultList();
	}
	
	@Override
	public List<NoncomplianceFindingView> getNoncomplianceFindingViews(Long facilityId) {
		Query query = em.createQuery(NONCOMPLIANCE_FINDING_VIEW_QUERY);
		query.setParameter("facilityId", facilityId);
		
		return (List<NoncomplianceFindingView>) query.getResultList();
	}
	
	@Override
	public List<Finding> getPreviousFindingsNeedingFollowups(Long inspectionId) {
		Query query = em.createQuery("select f from Finding f join fetch f.inspection ins, Inspection currIns " +
				" where ins.facility.id = currIns.facility.id and ins.inspectionDate < currIns.inspectionDate and currIns.id = :inspectionId" +
				"    and f.correctionVerification = '" + CorrectionVerificationType.VERIFICATION_PENDING.getCharacter() + "'");
		
		query.setParameter("inspectionId", inspectionId);
		
		return (List<Finding>) query.getResultList();
	}
}