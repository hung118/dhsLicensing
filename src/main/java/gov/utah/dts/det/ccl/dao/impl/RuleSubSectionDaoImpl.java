package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.RuleSubSectionDao;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.model.view.RuleView;
import gov.utah.dts.det.ccl.view.RuleResultView;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("ruleSubSectionDao")
public class RuleSubSectionDaoImpl extends AbstractBaseDaoImpl<RuleSubSection, Long> implements RuleSubSectionDao {

	private static final String RULE_VIEW_QUERY = "select new gov.utah.dts.det.ccl.view.RuleResultView(rv.id, rv.ruleNumber, rv.description, rv.ruleText) from RuleView rv where rv.id = :ruleId";
	private static final String RULE_VIEW_LOOKUP_QUERY = "select new gov.utah.dts.det.ccl.view.RuleResultView(rv.id, rv.ruleNumber, rv.description, rv.ruleText) from RuleView rv where rv.baseRuleNumber = '501'";
	private static final String RULE_VIEW_SEARCH_INSPECTION_QUERY = "select distinct rv from RuleView rv, Inspection i where rv.baseRuleNumber = '501' and i.id = :inspectionId " +
			" and rv.id not in (select f.rule.id from Finding f where f.inspection.id = :inspectionId) ";
	private static final String RULE_VIEW_SEARCH_QUERY = "select distinct rv from RuleView rv where rv.baseRuleNumber = '501'";
	private static final String RULE_VIEW_SEARCH_ACTIVE_CLAUSE = " and rv.ruleActive = 'Y' and rv.sectionActive = 'Y' and rv.sectionCategory = 'A' and (rv.id IS NULL or (rv.subSectionActive = 'Y' and rv.subSectionCategory = 'A'))";
	private static final String RULE_VIEW_SEARCH_RULE_NUMBER_CLAUSE = " and lower(rv.ruleNumber) like :ruleQuery";
	private static final String RULE_VIEW_SEARCH_EXCLUDES_CLAUSE = " and rv.id not in (:ruleExcludes)";
	private static final String RULE_VIEW_SEARCH_ORDER_BY_CLAUSE = " order by rv.sectionOrder, rv.subSectionOrder, rv.ruleNumber";
	
//	private static final String FACILITY_RULE_VIEW_QUERY = "select rv from RuleView rv, FacilitySearchView fsv where (fsv.id = :facilityId and rv.subSectionActive = 'Y' and rv.sectionActive = 'Y' and rv.ruleActive = 'Y') and (rv.facilityTypeId = fsv.type.id or (lower(rv.facilityTypeName) = 'all')) and rv.dontIssueFindings = 'N' ";
//	private static final String FACILITY_RULE_VIEW_RULE_NUMBER_CLAUSE = " and lower(rv.ruleNumber) like :ruleQuery ";
//	private static final String FACILITY_RULE_VIEW_DUPLICATE_RULE_CLAUSE = " and rv.id not in (select f.rule.id from Finding f where f.inspection.id = :inspectionId) ";
//	private static final String FACILITY_RULE_VIEW_ORDER_BY_CLAUSE = " order by rv.sectionOrder, rv.subSectionOrder, rv.ruleNumber";
	
	public RuleSubSectionDaoImpl() {
		super(RuleSubSection.class);
	}

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public RuleResultView getRuleView(Long ruleId) {
		Query query = em.createQuery(RULE_VIEW_QUERY);
		query.setParameter("ruleId", ruleId);
		
		return (RuleResultView) query.getSingleResult();
	}
	
	/**
	 * @depricated this has been depricated see some.other.bean.java
	 */
	@Deprecated
	@Override
	public List<RuleView> doRuleSearch(Long inspectionId, String ruleQuery, boolean excludeInactive) {
		StringBuilder sb = new StringBuilder();
		if (inspectionId != null) {
			sb.append(RULE_VIEW_SEARCH_INSPECTION_QUERY);
		} else {
			sb.append(RULE_VIEW_SEARCH_QUERY);
		}
		if (excludeInactive) {
			sb.append(RULE_VIEW_SEARCH_ACTIVE_CLAUSE);
		}
		if (StringUtils.isNotBlank(ruleQuery)) {
			sb.append(RULE_VIEW_SEARCH_RULE_NUMBER_CLAUSE);
		}
		sb.append(RULE_VIEW_SEARCH_ORDER_BY_CLAUSE);
		
		Query query = em.createQuery(sb.toString());
		if (inspectionId != null) {
			query.setParameter("inspectionId", inspectionId);
		}
		if (StringUtils.isNotBlank(ruleQuery)) {
			query.setParameter("ruleQuery", ruleQuery.toLowerCase() + "%");
			query.setMaxResults(20);
		}
		
		return (List<RuleView>) query.getResultList();
	}
	
	@Override
	public List<RuleResultView> doRuleSearch(String ruleQuery, boolean excludeInactive, List<RuleSubSection> ruleExcludes) {
		StringBuilder sb = new StringBuilder();
		sb.append(RULE_VIEW_LOOKUP_QUERY);
		if (StringUtils.isNotBlank(ruleQuery)) {
			sb.append(RULE_VIEW_SEARCH_RULE_NUMBER_CLAUSE);
		}
		if (excludeInactive) {
			sb.append(RULE_VIEW_SEARCH_ACTIVE_CLAUSE);
		}
		if (ruleExcludes != null && !ruleExcludes.isEmpty()) {
			sb.append(RULE_VIEW_SEARCH_EXCLUDES_CLAUSE);
		}
		sb.append(RULE_VIEW_SEARCH_ORDER_BY_CLAUSE);
		
		Query query = em.createQuery(sb.toString());
		if (StringUtils.isNotBlank(ruleQuery)) {
			query.setParameter("ruleQuery", ruleQuery.toLowerCase() + "%");
		}
		if (ruleExcludes != null && !ruleExcludes.isEmpty()) {
			List<Long> ids = new ArrayList<Long>();
			for (RuleSubSection rss : ruleExcludes) {
				ids.add(rss.getId());
			}
			query.setParameter("ruleExcludes", ids);
		}
		query.setMaxResults(20);
		
		return (List<RuleResultView>) query.getResultList();
	}
	
	@Override
	public List<FindingCategoryPickListValue> getFindingCategoriesForRule(Long ruleId) {
		String queryString = "select plv from RuleSubSection rss left join rss.findingCategories plv where plv.active = 'Y' and rss.id = :ruleId order by plv.sortOrder";
		Query query = em.createQuery(queryString);
		query.setParameter("ruleId", ruleId);
		
		return (List<FindingCategoryPickListValue>) query.getResultList();
	}
	
	@Override
	public List<PickListValue> getNoncomplianceLevelsForRule(Long ruleId) {
		String queryString = "select plv from RuleSubSection rss left join rss.nonComplianceLevels plv where plv.active = 'Y' and rss.id = :ruleId order by plv.sortOrder";
		Query query = em.createQuery(queryString);
		query.setParameter("ruleId", ruleId);
		
		return (List<PickListValue>) query.getResultList();
	}

	@Override
	/**
	 * CKS: work-around
	 */
	public RuleSubSection merge(RuleSubSection ruleSubSection) {
		return em.merge(ruleSubSection);
	}
	
}