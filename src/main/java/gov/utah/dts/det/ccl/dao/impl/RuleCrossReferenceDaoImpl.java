package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.RuleCrossReferenceDao;
import gov.utah.dts.det.ccl.model.RuleCrossReference;
import gov.utah.dts.det.ccl.service.util.ServiceUtils;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("ruleCrossReferenceDao")
public class RuleCrossReferenceDaoImpl extends AbstractBaseDaoImpl<RuleCrossReference, Long> implements RuleCrossReferenceDao {

	private static final String RULE_CROSS_REF_QUERY = "from RuleCrossReference rcr left join fetch rcr.oldRule left join fetch rcr.newRule ";
	private static final String RULE_CROSS_REF_COUNT_QUERY = "select count(*) from RuleCrossReference rcr ";
	
	@PersistenceContext
	private EntityManager em;
	
	public RuleCrossReferenceDaoImpl() {
		super(RuleCrossReference.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<RuleCrossReference> searchCrossReferences(String ruleNumFilter, SortBy sortBy, int page, int resultsPerPage) {
		StringBuilder sb = new StringBuilder(RULE_CROSS_REF_QUERY);
		buildSearchQueryString(sb, ruleNumFilter, sortBy);
		
		Query query = buildSearchQuery(sb.toString(), ruleNumFilter);
		ServiceUtils.addFirstAndMaxResults(query, resultsPerPage, page);
		
		return (List<RuleCrossReference>) query.getResultList();
	}
	
	@Override
	public int searchCrossReferencesCount(String ruleNumFilter) {
		StringBuilder sb = new StringBuilder(RULE_CROSS_REF_COUNT_QUERY);
		buildSearchQueryString(sb, ruleNumFilter, null);
		
		Query query = buildSearchQuery(sb.toString(), ruleNumFilter);
		
		Long results = (Long) query.getSingleResult();
		return results.intValue();
	}
	
	private void buildSearchQueryString(StringBuilder queryStr, String ruleNumFilter, SortBy sortBy) {
		if (StringUtils.isNotBlank(ruleNumFilter)) {
			queryStr.append(" where upper(rcr.oldRule.ruleNumber) like :ruleNumFilter or upper(rcr.newRule.ruleNumber) like :ruleNumFilter ");
		}
		
		ServiceUtils.addSortByClause(queryStr, sortBy, null);
	}
	
	private Query buildSearchQuery(String queryString, String ruleNumFilter) {
		Query query = em.createQuery(queryString);
		
		if (StringUtils.isNotBlank(ruleNumFilter)) {
			query.setParameter("ruleNumFilter", ruleNumFilter.toUpperCase() + "%");
		}
		
		return query;
	}
}