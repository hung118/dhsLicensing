package gov.utah.dts.det.ccl.actions.admin.rules;

import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.service.RuleService;

import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseRuleAction extends ActionSupport {

	private RuleService ruleService;
	
	private Long ruleId;
	private Long sectionId;
	private Long subSectionId;
	
	protected Rule getRule() {
		Rule rule = null;
		if (ruleId != null) {
			rule = ruleService.loadRuleById(ruleId);
		} else {
			RuleSection section = getSection();
			rule = section.getRule();
		}
		return rule;
	}
	
	protected RuleSection getSection() {
		RuleSection section = null;
		if (sectionId != null) {
			section = ruleService.loadRuleSectionById(sectionId);
		} else {
			RuleSubSection subSection = getSubSection();
			section = subSection.getSection();
		}
		return section;
	}
	
	protected RuleSubSection getSubSection() {
		if (subSectionId != null) {
			return ruleService.loadRuleSubSectionById(subSectionId);
		}
		return null;
	}
	
	public Long getRuleId() {
		return ruleId;
	}
	
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	public Long getSectionId() {
		return sectionId;
	}
	
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	
	public Long getSubSectionId() {
		return subSectionId;
	}
	
	public void setSubSectionId(Long subSectionId) {
		this.subSectionId = subSectionId;
	}
	
	public List<Rule> getRules() {
		return ruleService.getRules();
	}
	
	public Set<RuleSection> getRuleSections() {
		Rule rule = getRule();
		if (rule != null) {
			return rule.getSections();
		}
		return null;
	}
	
	public Set<RuleSubSection> getRuleSubSections() {
		RuleSection section = getSection();
		if (section != null) {
			return section.getSubSections();
		}
		return null;
	}
}