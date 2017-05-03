package gov.utah.dts.det.ccl.actions.facility.incidents;

import gov.utah.dts.det.ccl.model.Incident;

import gov.utah.dts.det.ccl.view.ViewUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import gov.utah.dts.det.ccl.service.CclServiceException;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-incident-info", type = "redirectAction", params = {"facilityId", "${facilityId}", "incidentId", "${incidentId}"}),
	@Result(name = "input", location = "incident_form.jsp"),
	@Result(name = "view", location = "incident_detail.jsp")
})
public class IncidentAction extends BaseIncidentEditAction implements Preparable {
	
	protected Incident incident;
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	@Action(value = "view-incident-info")
	public String doView() {
		incident = super.getIncident();
		
		return VIEW;
	}
	
	@Action(value = "edit-incident-info")
	public String doEdit() {
		incident = super.getIncident();
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		incident = super.getIncident();
		incidentService.evict(incident);
	}
	
	@Validations(
		visitorFields = @VisitorFieldValidator(fieldName = "incident", message = "&zwnj;")
	)
	@Action(value = "save-incident-info")
	public String doSave() {
		try {
			incidentService.saveIncident(incident);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			
			return INPUT;
		} catch (AccessDeniedException ade) {
			addActionError(ade.getMessage());
			
			return INPUT;
		}
		
		return REDIRECT_VIEW;
	}
	
	@Override
	public Incident getIncident() {
		return incident;
	}
	
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
}