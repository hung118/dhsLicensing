package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.dao.RuleSubSectionDao;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.model.enums.RuleCategory;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.RuleService;
import gov.utah.dts.det.ccl.view.ViewUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import org.springframework.dao.DataIntegrityViolationException;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/rules")
@Results({
	@Result(name = "input", location = "rule_subsection_form.jsp"),
	@Result(name = "success", location = "edit-subsection", type = "redirectAction", params = {"rule.id", "${rule.id}", "section.id", "${section.id}"})
})
public class RuleSubSectionAction extends ActionSupport implements Preparable, ParameterAware, SessionAware {

	private Map<String, String[]> parameterMap;
	private Map<String, Object> session;
	
	private RuleService ruleService;
	private PickListService pickListService;

	private RuleSubSectionDao ruleSubSectionDao;

	private Rule rule;
	private RuleSection section;
	private RuleSubSection subSection;
	private List<FindingCategoryPickListValue> formFindingCategories;
	private List<PickListValue> formNonComplianceLevels;
	private Long textFileId;
	private Long rationaleFileId;
	private Long enforcementFileId;
	private Long infoFileId;

	private List<PickListValue> findingCategories;
	private List<PickListValue> nonComLevels;
	
	@Override
	public void setParameters(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	@SkipValidation
	@Action(value = "edit-subsection")
	public String doEdit() {
		loadRuleSubSection();
		
		if (subSection != null) {
			formFindingCategories = new ArrayList<FindingCategoryPickListValue>(subSection.getFindingCategories());
			formNonComplianceLevels = new ArrayList<PickListValue>(subSection.getNonComplianceLevels());
		}
		
		return INPUT;
	}
	
	@SkipValidation
	@Action(value = "delete-subsection")
	public String doDelete() {
		loadRuleSubSection();
		try {
			section.getSubSections().remove(subSection);
			ruleService.saveRule(rule);
		} catch (DataIntegrityViolationException dive) {
			addActionError("Cannot delete rule.  It has been used on a checklist.");
		}
		
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "activate-subsection")
	public String doActivate() {
		if (subSection != null) {
			loadRuleSubSection();
			subSection.setActive(true);
			
			if (subSection.getCategory().getCharacter() == RuleCategory.PENDING.getCharacter()) {
				subSection.setCategory(RuleCategory.ACTIVE);
/* CKS:Feb 21, 2013 - I don't think we'll want to re-activate these, as this indicates they've been archived
			} else if (subSection.getCategory().getCharacter() == RuleCategory.INACTIVE.getCharacter()) {
				subSection.setCategory(RuleCategory.ACTIVE);
*/
			}
			
			ruleService.saveRule(rule);
		}
		
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "activate-subsection-all")
	public String doActivateAll() {
		
		if (subSection == null || section == null) {
			loadRuleSubSection();
		}
		
		if (section != null && section.getId() != null) {
			section.setActive(true);
			for (Iterator<RuleSubSection> iterator = section.getSubSections().iterator(); iterator.hasNext();) {
				RuleSubSection sub = iterator.next();
				if (sub.getCategory().getCharacter() == RuleCategory.PENDING.getCharacter()) {
					sub.setCategory(RuleCategory.ACTIVE);
/* CKS:Feb 21, 2013 - I don't think we'll want to re-activate these, as this indicates they've been archived
				} else if (sub.getCategory().getCharacter() == RuleCategory.INACTIVE.getCharacter()) {
					sub.setCategory(RuleCategory.ACTIVE);
*/
				}
			}
			ruleService.saveRuleSection(section);
		}
		
		return SUCCESS;
	}
	
	@Action(value = "deactivate-subsection-all")
	public String doDeactivateAll() {
		if (subSection == null || section == null) {
			loadRuleSubSection();
		}
		
		if (section != null && section.getId() != null) {
			section.setActive(false);
			for (Iterator<RuleSubSection> iterator = section.getSubSections().iterator(); iterator.hasNext();) {
				RuleSubSection sub = iterator.next();
				if (sub.getCategory().getCharacter() == RuleCategory.ACTIVE.getCharacter()) {
					sub.setCategory(RuleCategory.INACTIVE);
				} else if (sub.getCategory().getCharacter() == RuleCategory.PENDING.getCharacter()) {
					sub.setCategory(RuleCategory.INACTIVE);
				}
			}
			ruleService.saveRuleSection(section);
		}
		
		return SUCCESS;
	}

	
	@SkipValidation
	@Action(value = "deactivate-subsection")
	public String doDeactivate() {
		if (subSection != null) {
			loadRuleSubSection();
			subSection.setActive(false);
			
			if (subSection.getCategory().getCharacter() == RuleCategory.PENDING.getCharacter()) {
				subSection.setCategory(RuleCategory.INACTIVE);
			} else if (subSection.getCategory().getCharacter() == RuleCategory.ACTIVE.getCharacter()) {
				subSection.setCategory(RuleCategory.INACTIVE);
			}
			
			ruleService.saveRule(rule);
		}
		
		return SUCCESS;
	}
	
	public void prepareDoSave() throws Exception {
		loadRuleSubSection();
		ruleService.evict(subSection);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(message = "Rule: ")	
		}
	)
	@Action(value = "save-subsection")
	public String doSave() throws Exception {
		
		if (subSection.getId() == null) {
			//activate new sub sections
			subSection.setSection(section);
			subSection.setActive(true);
			section.getSubSections().add(subSection);
		}

		ruleService.evict(subSection);
		ruleService.evict(subSection.getSection());
		ruleService.evict(subSection.getSection().getRule());
		
		if (subSection.getCreatedBy() == null) {
			subSection.setCreatedBy(SecurityUtil.getUser().getUsername());
			subSection.setVersionDate(new java.util.Date());
		}
		
		manageFormCollections(subSection);
		ruleService.saveRuleSubSection(subSection, textFileId, enforcementFileId, rationaleFileId, infoFileId);
		
		loadRuleSubSection();

		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "reorder-subsections")
	public String doReorder() {
		loadRuleSubSection();
		Set<RuleSubSection> subSections = section.getSubSections();
		Iterator<RuleSubSection> itr = subSections.iterator();
		while (itr.hasNext()) {
			RuleSubSection subSection = itr.next();
			String key = "order-" + subSection.getId();
			String[] value = (String[]) parameterMap.get(key);
			if (StringUtils.isBlank(value[0])) {
				subSection.setSortOrder(null);
			} else {
				try {
					subSection.setSortOrder(Double.parseDouble(value[0]));
				} catch (NumberFormatException nfe) {
					addFieldError(key, "The value " + value[0] + " is not a valid sort order.  Please enter a numeric value.");
				}
			}
			ruleService.saveRuleSubSection(subSection);
		}
//		ruleService.saveRule(rule);
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		/* CKS: these don't apply to DHS
		 if (!subSection.isDontIssueFindings()) {
			if (formFindingCategories == null || formFindingCategories.isEmpty()) {
				addFieldError("formFindingCategories.id", "Rule: You must select at least one finding category.");
			}
			if (formNonComplianceLevels == null || formNonComplianceLevels.isEmpty()) {
				addFieldError("formNonComplianceLevels.id", "Rule: You must select at least one noncompliance level.");
			}
		}*/
	}
	
	private void loadRuleSubSection() {
		if (rule == null || rule.getId() == null) {
			throw new IllegalArgumentException("You must provide the rule id");
		}
		if (section == null || section.getId() == null) {
			throw new IllegalArgumentException("You must provide the rule section id");
		}
		rule = ruleService.loadRuleById(rule.getId());
		if (rule == null) {
			throw new IllegalArgumentException("Could not find rule with given id");
		}
		section = ruleService.loadRuleSectionById(section.getId());
		if (section == null) {
			throw new IllegalArgumentException("Could not find section with given id");
		}
		if (subSection != null && subSection.getId() != null) {
			subSection = ruleService.loadRuleSubSectionById(subSection.getId());
			if (subSection == null) {
				throw new IllegalArgumentException("Cound not find sub section with given id");
			}
			
/* CKS: not needed
			if (subSection.getTextFile() != null) {
				this.textFileId = subSection.getTextFile().getId();
			}
			if (subSection.getRationaleFile() != null) {
				this.rationaleFileId = subSection.getRationaleFile().getId();
			}
			if (subSection.getEnforcementFile() != null) {
				this.enforcementFileId = subSection.getEnforcementFile().getId();
			}
			if (subSection.getInfoFile() != null) {
				this.infoFileId = subSection.getInfoFile().getId();
			}
			if (subSection.getRuleText() != null) {
				subSection.getRuleText().getId();
			}
			if (subSection.getRuleDetails() != null) {
				subSection.getRuleDetails().getId();
			}
*/
		}
	}
	
	private void manageFormCollections(RuleSubSection subSection) {
		ViewUtils.managePickListCollection(formFindingCategories, subSection.getFindingCategories());
		ViewUtils.managePickListCollection(formNonComplianceLevels, subSection.getNonComplianceLevels());
	}
	
	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}
	
	public Rule getRule() {
		return rule;
	}
	
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	
	public RuleSection getSection() {
		return section;
	}
	
	public void setSection(RuleSection section) {
		this.section = section;
	}

	public RuleSubSection getSubSection() {
		return subSection;
	}
	
	public void setSubSection(RuleSubSection subSection) {
		this.subSection = subSection;
	}
	
	public List<FindingCategoryPickListValue> getFormFindingCategories() {
		return formFindingCategories;
	}
	
	public void setFormFindingCategories(List<FindingCategoryPickListValue> formFindingCategories) {
		this.formFindingCategories = formFindingCategories;
	}
	
	public List<PickListValue> getFormNonComplianceLevels() {
		return formNonComplianceLevels;
	}
	
	public void setFormNonComplianceLevels(List<PickListValue> formNonComplianceLevels) {
		this.formNonComplianceLevels = formNonComplianceLevels;
	}
	
	public Long getTextFileId() {
		return textFileId;
	}
	
	public void setTextFileId(Long textFileId) {
		this.textFileId = textFileId;
	}
	
	public Long getRationaleFileId() {
		return rationaleFileId;
	}
	
	public void setRationaleFileId(Long rationaleFileId) {
		this.rationaleFileId = rationaleFileId;
	}
	
	public Long getEnforcementFileId() {
		return enforcementFileId;
	}
	
	public void setEnforcementFileId(Long enforcementFileId) {
		this.enforcementFileId = enforcementFileId;
	}
	
	public Long getInfoFileId() {
		return infoFileId;
	}
	
	public void setInfoFileId(Long infoFileId) {
		this.infoFileId = infoFileId;
	}
	
	public List<PickListValue> getFindingCategories() {
		if (findingCategories == null) {
			findingCategories = pickListService.getValuesForPickList("Findings Categories", true);
		}
		return findingCategories;
	}
	
	public List<PickListValue> getNonComLevels() {
		if (nonComLevels == null) {
			nonComLevels = pickListService.getValuesForPickList("Noncompliance Levels", true);
		}
		return nonComLevels;
	}
	
	public List<RuleCategory> getCategories() {
		return Arrays.asList(RuleCategory.values());
	}

	public RuleSubSectionDao getRuleSubSectionDao() {
		return ruleSubSectionDao;
	}

	public void setRuleSubSectionDao(RuleSubSectionDao ruleSubSectionDao) {
		this.ruleSubSectionDao = ruleSubSectionDao;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}