package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.quartz.QuartzJobInitializer;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.model.Rule;
import gov.utah.dts.det.ccl.model.enums.DownloadTimes;
import gov.utah.dts.det.ccl.service.RuleService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/rules")
@Results({
	@Result(name = "input", location = "rule_form.jsp"),
	@Result(name = "success", location = "edit-rule", type = "redirectAction")
})
public class RuleAction extends ActionSupport implements Preparable {

	protected final Log log = LogFactory.getLog(getClass());
	
	private RuleService ruleService;
	private PickListService pickListService;
	
	@Autowired(required = false)
	private QuartzJobInitializer quartzJobInitializer;
	
	private Rule rule;
	
	private List<PickListValue> facilityTypes;
	private List<Rule> rules;
	
	@Override
	public void prepare() {
		if (rule != null && rule.getId() != null) {
			rule = ruleService.loadRuleById(rule.getId());
		}
	}
	
	public void prepareDoSave() {
		prepare();
		if (rule == null) {
			rule = new Rule();
		}
		if (rule.getFacilityType() == null) { 
			rule.setFacilityType(getDefaultFacilityType());
		}
		ruleService.evict(rule);
	}
	
	@Action(value = "tab", results = {
		@Result(name = "success", location = "rules_tab.jsp")
	})
	public String doTab() {
		return SUCCESS;
	}
	
	@Action(value = "edit-rule")
	public String doEdit() {
		if (rule == null) {
			rule = new Rule();
		}
		if (rule.getFacilityType() == null) { 
			rule.setFacilityType(getDefaultFacilityType());
		}
		return INPUT;
	}
	
	@Action(value = "delete-rule")
	public String doDelete() {
		try {
			ruleService.deleteRule(rule);
		} catch (DataIntegrityViolationException dive) {
			addActionError("Cannot delete facility rule.  You must remove all sections first.");
		}
		return SUCCESS;
	}
	
	@Action(value = "activate-rule")
	public String doActivate() {
		if (rule != null) {
			rule.setActive(true);
			ruleService.saveRule(rule);
		}
		
		return SUCCESS;
	}
	
	@Action(value = "deactivate-rule")
	public String doDeactivate() {
		if (rule != null) {
			rule.setActive(false);
			ruleService.saveRule(rule);
		}
		
		return SUCCESS;
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "rule", message = "Facility Rule: ")
		}
	)
	@Action(value = "save-rule")
	public String doSave() throws SchedulerException, ParseException {
		if (rule.getId() == null) {
			//activate new rules
			rule.setActive(true);
		}
		
		if (rule.getFacilityType() == null) { 
			rule.setFacilityType(getDefaultFacilityType());
		}

		ruleService.saveRule(rule);

		if (quartzJobInitializer != null) {
			if (!rule.isActive()) {
				quartzJobInitializer.deleteJob(rule);
			} else {
				quartzJobInitializer.scheduleJob(rule);	
			}
		}
		
		return SUCCESS;
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
	
	public List<PickListValue> getFacilityTypes() {
		if (facilityTypes == null) {
			facilityTypes = new ArrayList<PickListValue>();
			PickListValue all = null;
			List<PickListValue> tempTypes = pickListService.getValuesForPickList("License Type", false);
			//filter all types that are inactive besides "All"
			for (PickListValue type : tempTypes) {
				if (type.isActive()) {
					facilityTypes.add(type);
				} else if (type.getValue().equalsIgnoreCase("All")) {
					all = type;
				}
			}
			if (all != null) {
				facilityTypes.add(0, all);
			}
		}
		return facilityTypes;
	}
	
	public List<Rule> getRules() {
		if (rules == null) {
			rules = ruleService.getRules();
		}
		return rules;
	}

	public List<DownloadTimes> getDownloadTimes() {
		return Arrays.asList(DownloadTimes.values());
	}
	
	
	public PickListValue getDefaultFacilityType() {
		if (facilityTypes == null) {
			getFacilityTypes();
		}
		PickListValue r = null;
		for (PickListValue v : this.facilityTypes) {
			if (v.getValue().equalsIgnoreCase("All")) {
				r = v;
				break;
			}
		}
		if (r == null && facilityTypes != null && facilityTypes.size() > 0) {
			r = facilityTypes.get(0);
		}
		return r;
	}

	public QuartzJobInitializer getQuartzJobInitializer() {
		return quartzJobInitializer;
	}

	public void setQuartzJobInitializer(QuartzJobInitializer quartzJobInitializer) {
		this.quartzJobInitializer = quartzJobInitializer;
	}
	
}