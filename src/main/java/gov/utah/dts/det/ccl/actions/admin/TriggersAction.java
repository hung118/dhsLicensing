package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.service.ApplicationService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/noncompliance")
@Results({
	@Result(name = "input", location = "triggers_form.jsp"),
	@Result(name = "success", location = "edit-triggers", type = "redirectAction")
})
public class TriggersAction extends ActionSupport {

	private ApplicationService applicationService;
	
	private Integer centerTrigger;
	private Integer homeTrigger;
	
	@Action(value = "edit-triggers")
	public String doEdit() throws Exception {
		centerTrigger = new Integer(applicationService.getApplicationPropertyValue(ApplicationPropertyKey.CENTER_NONCOMPLIANCE_TRIGGER.getKey()));
		homeTrigger = new Integer(applicationService.getApplicationPropertyValue(ApplicationPropertyKey.HOME_NONCOMPLIANCE_TRIGGER.getKey()));
		return INPUT;
	}

	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "centerTrigger", message = "Center trigger is required."),
			@RequiredFieldValidator(fieldName = "homeTrigger", message = "Home trigger is required.")
		}
	)
	@Action(value = "save-triggers")
	public String doSave() throws Exception {
		ApplicationProperty centerProp = applicationService.findApplicationPropertyByKey(
				ApplicationPropertyKey.CENTER_NONCOMPLIANCE_TRIGGER.getKey());
		ApplicationProperty homeProp = applicationService.findApplicationPropertyByKey(
				ApplicationPropertyKey.HOME_NONCOMPLIANCE_TRIGGER.getKey());
		centerProp.setValue(centerTrigger.toString());
		homeProp.setValue(homeTrigger.toString());
		
		applicationService.saveApplicationProperty(centerProp);
		applicationService.saveApplicationProperty(homeProp);
		
		return SUCCESS;
	}
	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	public Integer getCenterTrigger() {
		return centerTrigger;
	}
	
	public void setCenterTrigger(Integer centerTrigger) {
		this.centerTrigger = centerTrigger;
	}
	
	public Integer getHomeTrigger() {
		return homeTrigger;
	}
	
	public void setHomeTrigger(Integer homeTrigger) {
		this.homeTrigger = homeTrigger;
	}
}