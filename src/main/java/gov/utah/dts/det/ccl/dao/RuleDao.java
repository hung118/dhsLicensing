package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.List;

import javax.persistence.EntityManager;

public interface RuleDao extends AbstractBaseDao<Rule, Long> {

	public List<Rule> getAllRules();
	
	public Rule getRuleForFacilityType(Long typeId);
	
	public Rule loadOrdered(Long ruleId);
	
	public List<RuleSection> getSectionsToConvert();
	
	public List<RuleSubSection> getSubSectionsToConvert();
	
	public List<RuleSubSection> getSectionsToRender();
	
	public Rule getByRuleNumber(String ruleNumber);
	
	public void clearCache();
}