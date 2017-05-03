package gov.utah.dts.det.ccl.actions.facility.incidents;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.IncidentService;
import gov.utah.dts.det.ccl.view.ViewUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.security.access.AccessDeniedException;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "new_incident_form.jsp"),
	@Result(name = "success", location = "edit-incident", type = "redirectAction", params = {"namespace", "/facility/incidents", "facilityId", "${facilityId}", "incidentId", "${incident.id}"})
})
public class NewIncidentAction extends BaseIncidentEditAction {

	private IncidentService incidentService;
	
	private Incident incident;
	
	@SkipValidation
	@Action(value = "new-incident")
	public String doForm() {
		return INPUT;
	}
	
	//incident.incidentDate is validated in model...
	//these fields are also validated in incidentService Impl
	@Validations(
		visitorFields =
			@VisitorFieldValidator(fieldName = "incident", message = "&zwnj;"),
		requiredFields = {
			@RequiredFieldValidator(fieldName = "incident.childAge", message = "Age is required."),
			@RequiredFieldValidator(fieldName = "incident.child.gender", message = "Gender is required"),
			@RequiredFieldValidator(fieldName = "incident.injuryType", message = "Injury Type is required"),
			@RequiredFieldValidator(fieldName = "incident.death", message = "Please select whether a death occured"),
			@RequiredFieldValidator(fieldName = "incident.reportedOverPhone", message = "Licensing Specialist: Please select whether or not the provider called within 24 hours of the accident"),
			@RequiredFieldValidator(fieldName = "incident.sentWrittenReport", message = "Licensing Specialist: Please select whether or not the provider sent a written report within 5 days of the accident")
			},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "incident.child.firstName", message = "Client: First Name is required."),
			@RequiredStringValidator(fieldName = "incident.child.lastName", message = "Client: Last Name is required.")
		})
	@Action(value = "save-new-incident")
	public String doSave() {
		try {
			incident.setFacility(getFacility());
			incident = incidentService.createIncident(incident);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			
			return INPUT;
		} catch (AccessDeniedException ade) {
			addActionError(ade.getMessage());
			
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	@Override
	public void setIncidentService(IncidentService incidentService) {
		this.incidentService = incidentService;
	}
	
	@Override
	public Incident getIncident() {
		return incident;
	}
	
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
}