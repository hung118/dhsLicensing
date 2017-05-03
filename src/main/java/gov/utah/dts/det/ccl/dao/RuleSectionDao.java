package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.Collection;
import java.util.List;

public interface RuleSectionDao extends AbstractBaseDao<RuleSection, Long> {

	public RuleSection getSectionWithSubSections(Long sectionId);
	
	public List<RuleSection> getSectionWithSubSections(Long ruleId, int sectionBase, int sectionNumber) throws Exception;
	
	public List<RuleSection> getActiveSectionsForRule(Long ruleId);
	
	public List<String[]> getActiveRuleSectionTitles(String ruleNumber);
	
	List<RuleSection> loadSections(Collection<Integer> selections);
}