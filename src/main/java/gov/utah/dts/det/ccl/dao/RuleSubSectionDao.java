package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.model.view.RuleView;
import gov.utah.dts.det.ccl.view.RuleResultView;
import gov.utah.dts.det.dao.AbstractBaseDao;
import java.util.List;

public interface RuleSubSectionDao extends AbstractBaseDao<RuleSubSection, Long> {

	public RuleResultView getRuleView(Long ruleId);
	
	public List<RuleView> doRuleSearch(Long inspectionId, String ruleQuery, boolean excludeInactive);
	
	public List<RuleResultView> doRuleSearch(String ruleQuery, boolean excludeInactive, List<RuleSubSection> ruleExcludes);
	
	public List<FindingCategoryPickListValue> getFindingCategoriesForRule(Long ruleId);
	
	public List<PickListValue> getNoncomplianceLevelsForRule(Long ruleId);

//	public List<RuleView> doRuleSearch(Long facilityId, Long inspectionId, String ruleQuery);
	
	/**
	 * CKS: this is a workaround
	 * @param ruleSubSection
	 * @return
	 */
	public RuleSubSection merge(RuleSubSection ruleSubSection);
	
}