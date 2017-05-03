package gov.utah.dts.det.ccl.actions.facility.information.license;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.FacilityTag;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.view.ViewUtils;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.service.ApplicationService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "conditional-statuses-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "conditional_status_form.jsp"),
	@Result(name = "view", location = "conditional_statuses_list.jsp")
})
public class ConditionalStatusAction extends BaseFacilityAction implements Preparable {

	private ApplicationService applicationService;
	
	private Long conditionalId;
	private FacilityTag conditional;
	
	private ListControls lstCtrl = new ListControls();
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
		lstCtrl.setResults(getFacility().getTags(applicationService.getPickListValueForApplicationProperty(
				ApplicationPropertyKey.TAG_CONDITIONAL.getKey())));
	}
	
	@Action(value = "conditional-statuses-list")
	public String doList() {
		lstCtrl.setShowControls(true);
		return VIEW;
	}
	
	@Action(value = "edit-conditional-status")
	public String doEdit() {
		loadConditionalStatus();
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadConditionalStatus();
		facilityService.evict(conditional);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "conditional", message = "&zwnj;") 
		}
	)
	@Action(value = "save-conditional-status")
	public String doSave() {
		try {
			facilityService.saveConditionalStatus(getFacility(), conditional);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			
			return INPUT;
		}
		
		return REDIRECT_VIEW;
	}
	
	@Action(value = "delete-conditional-status")
	public String doDelete() {
		getFacility().removeTag(conditionalId);
		
		facilityService.saveFacility(getFacility());
		
		return REDIRECT_VIEW;
	}
	
	public void loadConditionalStatus() {
		if (conditionalId != null) {
			conditional = getFacility().getTag(conditionalId);
		}
	}
	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	public Long getConditionalId() {
		return conditionalId;
	}
	
	public void setConditionalId(Long conditionalId) {
		this.conditionalId = conditionalId;
	}
	
	public FacilityTag getConditional() {
		return conditional;
	}
	
	public void setConditional(FacilityTag conditional) {
		this.conditional = conditional;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
}