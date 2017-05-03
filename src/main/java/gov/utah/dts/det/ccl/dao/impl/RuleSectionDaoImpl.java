package gov.utah.dts.det.ccl.dao.impl;

import gov.utah.dts.det.ccl.dao.RuleSectionDao;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.dao.AbstractBaseDaoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository("ruleSectionDao")
public class RuleSectionDaoImpl extends AbstractBaseDaoImpl<RuleSection, Long> implements RuleSectionDao {
	
	protected final Log log = LogFactory.getLog(getClass());

	private static final String SECTION_WITH_SUB_SECTIONS_QUERY = "from RuleSection section left join fetch section.subSections subSection " +
			" where section.id = :sectionId and section.rule.active = 'Y' and section.active = 'Y' and subSection.active = 'Y' " +
			" order by section.sortOrder, section.number, subSection.sortOrder, subSection.number";
	
	 private static final String SECTION_WITH_SUB_SECTIONS_QUERY_VIA_NUMBER = "from RuleSection section " +
				" where section.rule.id = :ruleId " +
				" and section.sectionBase = :sectionBase " +
				" and section.number = :sectionNumber " +
				" and section.rule.active = 'Y' " +
				//" and section.active = 'Y' " +
				" order by section.versionDate desc";

	private static final String ACTIVE_SECTIONS_FOR_RULE_QUERY = "from RuleSection section where section.rule.id = :ruleId and " +
			" section.active = 'Y' order by section.sortOrder, section.number";
	
	private static final String GET_SECTIONS_NATIVE = "select distinct(SECTION_TITLE), SECTION_BASE " +
			"from CCLRULESECTION " +
			"where RULEID = (select RULEID from CCLRULE where RULENUMBER = ?)" +
			"and ISACTIVE = 'Y' " +
			"order by 2";

	public RuleSectionDaoImpl() {
		super(RuleSection.class);
	}

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public RuleSection getSectionWithSubSections(Long sectionId) {
		Query query = em.createQuery(SECTION_WITH_SUB_SECTIONS_QUERY);
		query.setParameter("sectionId", sectionId);
		
		return (RuleSection) query.getSingleResult();
	}
	
	public List<RuleSection> getSectionWithSubSections(Long ruleId, int sectionBase, int sectionNumber) throws Exception {
		Query query = em.createQuery(SECTION_WITH_SUB_SECTIONS_QUERY_VIA_NUMBER);
		query.setParameter("ruleId", ruleId);
		query.setParameter("sectionBase", sectionBase);
		query.setParameter("sectionNumber", sectionNumber);
		
		List<RuleSection> list = null;
		try {
			list = query.getResultList();
		} catch (Exception e) {
			log.error(e);
		}
		
		return list;
	}
	
	@Override
	public List<RuleSection> getActiveSectionsForRule(Long ruleId) {
		Query query = em.createQuery(ACTIVE_SECTIONS_FOR_RULE_QUERY);
		query.setParameter("ruleId", ruleId);
		
		return (List<RuleSection>) query.getResultList();
	}
	
	public List<String[]> getActiveRuleSectionTitles(String ruleNumber) {
		Query q = em.createNativeQuery(GET_SECTIONS_NATIVE);
		q.setParameter(1, ruleNumber);
		List<Object[]> objects = q.getResultList();
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] _obj = objects.get(i);
			if (_obj[0] != null && _obj[1] != null) {
				String[] array = {_obj[0].toString(), _obj[1].toString()};
				list.add(array);
			} else {
				log.debug("RuleSectionDaoImpl: i="+i+" has null(s)");
			}
		}
		
		return list;
	}

	@Override
	public List<RuleSection> loadSections(Collection<Integer> selections) {
		String hql = "FROM RuleSection s WHERE s.sectionBase IN(";
		for (Iterator<Integer> iterator = selections.iterator(); iterator.hasNext();) {
			hql += iterator.next()+",";
		}
		hql = hql.substring(0, hql.length()-1);
		hql += ") ORDER BY s.sectionBase";
		
		return em.createQuery(hql, RuleSection.class).getResultList();
	}
	
}