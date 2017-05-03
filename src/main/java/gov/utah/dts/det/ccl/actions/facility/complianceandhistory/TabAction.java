package gov.utah.dts.det.ccl.actions.facility.complianceandhistory;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

@SuppressWarnings("serial")
public class TabAction extends BaseFacilityAction {
	
	@Action(value = "tab", results = {
		@Result(name = "success", location = "complianceandhistory_tab.jsp")
	})
	public String doTab() {
		return SUCCESS;
	}
	
	@Action(value = "compliance-tab", results = {
		@Result(name = "success", location = "compliance_subtab.jsp")
	})
	public String doComplianceSubtab() {
		return SUCCESS;
	}
	
	@Action(value = "history-tab", results = {
		@Result(name = "success", location = "history_subtab.jsp")
	})
	public String doHistorySubtab() {
		return SUCCESS;
	}
	
	@Action(value = "rule-violations-tab", results = {
		@Result(name = "success", location = "rule_violations.jsp")
	})
	public String doRuleViolationsSubtab() {
		return SUCCESS;
	}
}