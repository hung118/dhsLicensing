package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.RuleDao;
import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("ruleDao")
public class RuleDaoImpl extends AbstractBaseDaoImpl<Rule, Long> implements RuleDao {
	
	private static final String RULE_QUERY = "from Rule r order by r.active desc, r.facilityType.value, r.number";
	
	private static final String RULE_SECTIONS_QUERY = "from Rule rule left join fetch rule.sections section " +
			"     left join fetch section.subSections subSection " +
			" where rule.facilityType.id = :facilityTypeId and rule.active = 'Y' and section.active = 'Y' " +
			"     and subSection.active = 'Y' " +
			" order by section.sectionBase, section.number, subSection.sortOrder, subSection.number";

	private static final String RULE_SECTIONS_QUERY_ORDERED = "from Rule rule left join fetch rule.sections section " +
			" where rule.id = :ruleId "+
			" order by section.sectionBase, section.number";

	@PersistenceContext
	private EntityManager em;

	public RuleDaoImpl() {
		super(Rule.class);
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public List<Rule> getAllRules() {
		Query query = em.createQuery(RULE_QUERY);
		
		return (List<Rule>) query.getResultList();
	}
	
	@Override
	public Rule getRuleForFacilityType(Long typeId) {
		Query query = em.createQuery(RULE_SECTIONS_QUERY);
		query.setParameter("facilityTypeId", typeId);
		
		return (Rule) query.getSingleResult();
	}
	

	//TODO: remove methods below this after the rule export is complete
	public List<RuleSection> getSectionsToConvert() {
		Query query = em.createQuery("select rs from RuleSection rs where rs.generalInfo is not null and rs.generalInfoFile is null");
		return (List<RuleSection>) query.getResultList();
	}
	
	public List<RuleSubSection> getSubSectionsToConvert() {
		Query query = em.createQuery("select rss from RuleSubSection rss where (rss.text is not null and rss.textFile is null) " +
				" or (rss.rationale is not null and rss.rationaleFile is null) " +
				" or (rss.enforcement is not null and rss.enforcementFile is null) " +
				" or (rss.info is not null and rss.infoFile is null) ");
		query.setMaxResults(100);
		return (List<RuleSubSection>) query.getResultList();
	}
	
	@Override
	public List<RuleSubSection> getSectionsToRender() {
		Query query = em.createQuery("select rss from RuleSubSection rss where (rss.text is not null or rss.rationale is not null or rss.enforcement is not null or rss.info is not null) and (rss.ruleText is null and rss.ruleDetails is null)");
		return (List<RuleSubSection>) query.getResultList();
	}
	
	public Rule getByRuleNumber(String ruleNumber) {
		Query query = em.createQuery("from Rule where number = :ruleNumber");
		query.setParameter("ruleNumber", ruleNumber);
		
		return (Rule) query.getSingleResult();
	}

	@Override
	public Rule loadOrdered(Long ruleId) {
		Query query = em.createQuery(RULE_SECTIONS_QUERY_ORDERED);
		query.setParameter("ruleId", ruleId);
		
		return (Rule) query.getSingleResult();
	}
	
	public void clearCache() {
		
		Session session = (Session) em.getDelegate();
		if (session != null) {
			SessionFactory factory = session.getSessionFactory();
			Cache cache = factory.getCache();

			cache.evictEntityRegions();
			cache.evictCollectionRegions();
			//cache.evictQueryRegions();	
		} else {
			System.err.println("RuleDaoImpl: clearCache called however the session object was null - thus, the cache could not be cleared");
		}
	}
}