package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.model.enums.RuleCategory;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.RuleService;

import java.io.IOException;
import java.util.Iterator;
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
import org.springframework.dao.DataIntegrityViolationException;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/rules")
@Results({
	@Result(name = "input", location = "rule_section_form.jsp"),
	@Result(name = "success", location = "edit-section", type = "redirectAction", params = {"rule.id", "${rule.id}"})
})
public class RuleSectionAction extends ActionSupport implements Preparable, ParameterAware, SessionAware {

	private Map<String, String[]> parameterMap;
	private Map<String, Object> session;

	private RuleService ruleService;
	
	private Rule rule;
	private RuleSection section;
	private Long sectionFileId;
	
	@Override
	public void setParameters(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	@Action(value = "view-section", results = {
		@Result(name = "success", location = "rule_section_detail.jsp")
	})
	public String doView() {
		if (section == null) {
			return doEdit();
		}
		
		loadRuleSection();
		
		return SUCCESS;
	}
	
	@Action(value = "edit-section")
	public String doEdit() {
		loadRuleSection();
		return INPUT;
	}
	
	@Action(value = "delete-section")
	public String doDelete() {
		try {
			loadRuleSection();
			rule.getSections().remove(section);
			ruleService.saveRule(rule);
		} catch (DataIntegrityViolationException dive) {
			addActionError("Cannot delete section.  It has been used on a checklist.");
		}
		
		return SUCCESS;
	}
	
	@Action(value = "activate-section")
	public String doActivate() {
		if (section != null) {
			loadRuleSection();
			section.setActive(true);
			section.setCategory(RuleCategory.ACTIVE);
			
			for (Iterator<RuleSubSection> iterator = section.getSubSections().iterator(); iterator.hasNext();) {
				RuleSubSection sub = iterator.next();
				if (sub.getCategory().getCharacter() == RuleCategory.PENDING.getCharacter()) {
					sub.setCategory(RuleCategory.ACTIVE);
					sub.setActive(true);
/* CKS:Feb 21, 2013 - I don't think we'll want to re-activate these, as this indicates they've been archived
				} else if (sub.getCategory().getCharacter() == RuleCategory.INACTIVE.getCharacter()) {
					sub.setCategory(RuleCategory.ACTIVE);
					sub.setActive(true);
*/
				}
			}
			
			ruleService.saveRuleSection(section);
		}
		
		return SUCCESS;
	}
	
	@Action(value = "deactivate-section")
	public String doDeactivate() {
		if (section != null) {
			loadRuleSection();
			section.setActive(false);
			section.setCategory(RuleCategory.INACTIVE);
			
			for (Iterator<RuleSubSection> iterator = section.getSubSections().iterator(); iterator.hasNext();) {
				RuleSubSection sub = iterator.next();
				if (sub.getCategory().getCharacter() == RuleCategory.ACTIVE.getCharacter()) {
					sub.setCategory(RuleCategory.INACTIVE);
					sub.setActive(false);
				} else if (sub.getCategory().getCharacter() == RuleCategory.PENDING.getCharacter()) {
					sub.setCategory(RuleCategory.INACTIVE);
					sub.setActive(false);
				}
			}
			
			ruleService.saveRuleSection(section);
		}
		
		return SUCCESS;
	}
	
	public void prepareDoSave() throws Exception {
		loadRuleSection();
		ruleService.evict(section);
	}

	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "section", message = "Section: ")
	})
	@Action(value = "save-section")
	public String doSave() throws IOException {
		if (section.getRule() == null) {
			//activate new sections
			section.setRule(rule);
			section.setActive(true);
			rule.getSections().add(section);
		}
		
		if (section.getCreatedBy() == null) {
			section.setCreatedBy(SecurityUtil.getUser().getUsername());
			section.setVersionDate(new java.util.Date());
		}
		
		ruleService.evict(rule);
		ruleService.evict(section);
		
		ruleService.saveRuleSection(section, sectionFileId);
		return SUCCESS;
	}
	
	@Action(value = "reorder-sections")
	public String doReorder() {
		loadRuleSection();
		Set<RuleSection> ruleSections = rule.getSections();
		Iterator<RuleSection> itr = ruleSections.iterator();
		while (itr.hasNext()) {
			RuleSection section = itr.next();
			String key = "order-" + section.getId();
			String[] value = (String[]) parameterMap.get(key);
			if (StringUtils.isBlank(value[0])) {
				section.setSortOrder(null);
			} else {
				try {
					section.setSortOrder(Double.parseDouble(value[0]));
				} catch (NumberFormatException nfe) {
					addFieldError(key, "The value " + value[0] + " is not a valid sort order.  Please enter a numeric value.");
				}
			}
			ruleService.saveRuleSection(section);
		}
//		ruleService.saveRule(rule);
		return SUCCESS;
	}
	
//	@Action(value = "render-section", results = {
//		@Result(name = "success", location = "view-render-history", type = "redirectAction", params = {"rule.id", "${rule.id}", "section.id",
//				"${section.id}"})
//	})
//	public String doRenderSection() throws Exception {
//		ruleService.renderSection(section.getId());
//		return SUCCESS;
//	}
	
	@Action(value = "view-render-history", results = {
			@Result(name = "success", location = "section_render_history.jsp")
	})
	public String doViewRenderHistory() {
		//			sectionRenders = ruleService.getPreviousSectionRenders(section.getId());
		return SUCCESS;
	}

	private void loadRuleSection() {
		if (rule == null || rule.getId() == null) {
			throw new IllegalArgumentException("You must provide the rule id");
		}
		rule = ruleService.loadRuleByIdOrdered(rule.getId());
		if (rule == null) {
			throw new IllegalArgumentException("Could not find rule with given id");
		}
		
		if (section != null && section.getId() != null) {
			section = ruleService.loadRuleSectionById(section.getId());
			if (section == null) {
				throw new IllegalArgumentException("Could not find section with given id");
			}
			if (section.getGeneralInfoFile() != null) {
				this.sectionFileId = section.getGeneralInfoFile().getId();
			}
		}
		
		/*if (section != null && section.getId() != null) {
			for (RuleSection section : rule.getSections()) {
				if (section.getId().longValue() == this.section.getId().longValue()) {
					this.section = section;
					if (section.getGeneralInfoFile() != null) {
						this.sectionFileId = section.getGeneralInfoFile().getId();
					}
					break;
				}
			}
		}*/
	}
	
	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
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
	
	public Long getSectionFileId() {
		return sectionFileId;
	}
	
	public void setSectionFileId(Long sectionFileId) {
		this.sectionFileId = sectionFileId;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}