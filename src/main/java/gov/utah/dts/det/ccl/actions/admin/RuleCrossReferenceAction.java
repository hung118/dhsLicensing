package gov.utah.dts.det.ccl.actions.admin;


import gov.utah.dts.det.ccl.model.RuleCrossReference;
import gov.utah.dts.det.ccl.service.RuleService;
import gov.utah.dts.det.ccl.sort.enums.RuleCrossReferenceSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.dao.DataIntegrityViolationException;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import gov.utah.dts.det.ccl.actions.CclAction;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/rulecrossreference")
@Results({
	@Result(name = "redirect-view", location = "cross-reference-list", type = "redirectAction"),
	@Result(name = "input", location = "cross_reference_form.jsp"),
	@Result(name = "view", location = "cross_reference_list.jsp")
})
public class RuleCrossReferenceAction extends ActionSupport implements Preparable, CclAction {

	private RuleService ruleService;
	
	private RuleCrossReference ref;
	private String ruleNumFilter;
	
	private ListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		if (ref != null && ref.getId() != null) {
			this.ref = ruleService.loadCrossReferenceById(ref.getId());
		}
		
		//set up the list controls
		lstCtrl = new ListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(RuleCrossReferenceSortBy.values())));
		lstCtrl.setSortBy(RuleCrossReferenceSortBy.getDefaultSortBy().name());
		lstCtrl.setResultsPerPage(25);
	}
	
	@Action(value = "tab", results = {
		@Result(name = "success", location = "cross_reference_tab.jsp")
	})
	public String doTab() {
		return SUCCESS;
	}
	
	@Action(value = "cross-reference-list")
	public String doList() {
		lstCtrl.setResults(ruleService.searchCrossReferences(ruleNumFilter, RuleCrossReferenceSortBy.valueOf(lstCtrl.getSortBy()),
				lstCtrl.getPage() - 1, lstCtrl.getResultsPerPage()));
		
		lstCtrl.setNumOfResults(ruleService.searchCrossReferencesCount(ruleNumFilter));
		
		return VIEW;
	}
	
	@Action(value = "edit-cross-reference")
	public String doEdit() {
		return INPUT;
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "ref", message = "&zwnj;")
		}
	)
	@Action(value = "save-cross-reference")
	public String doSave() {
		try {
			ruleService.saveCrossReference(ref);
		} catch (DataIntegrityViolationException dive) {
			addActionError("This combination of new rule and old rule already exists.");
			
			return INPUT;
		}
		
		return REDIRECT_VIEW;
	}
	
	@Action(value = "delete-cross-reference")
	public String doDelete() {
		ruleService.deleteCrossReference(ref);
		
		return REDIRECT_VIEW;
	}
	
	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}
	
	public RuleCrossReference getRef() {
		return ref;
	}
	
	public void setRef(RuleCrossReference ref) {
		this.ref = ref;
	}
	
	public String getRuleNumFilter() {
		return ruleNumFilter;
	}
	
	public void setRuleNumFilter(String ruleNumFilter) {
		this.ruleNumFilter = ruleNumFilter;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public List<RuleCrossReferenceSortBy> getSortBys() {
		return Arrays.asList(RuleCrossReferenceSortBy.values());
	}
}