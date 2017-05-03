package gov.utah.dts.det.ccl.actions.facility.complaints.intake;

import gov.utah.dts.det.ccl.model.ComplaintIncident;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "incidents-list", type = "redirectAction", params = {"facilityId", "${facilityId}", "complaintId", "${complaintId}"}),
	@Result(name = "input", location = "incident_form.jsp"),
	@Result(name = "view", location = "incidents_list.jsp")
})
public class IncidentAction extends BaseIntakeAction implements Preparable {

	private ComplaintIncident incident;
	private Date incidentTime;
	
	private ListControls lstCtrl = new ListControls();
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}
	
	@SkipValidation
	@Action(value = "incidents-list")
	public String doList() {
		loadIncidents();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}

	@SkipValidation
	@Action(value = "edit-incident")
	public String doEdit() {
		loadIncidents();
		loadIncident();
		
		return INPUT;
	}

	@SkipValidation
	@Action(value = "delete-incident")
	public String doDelete() {
		getComplaint().removeIncident(incident.getId());
		complaintService.saveIntake(getComplaint());
		
		return REDIRECT_VIEW;
	}
	
	public void prepareDoSave() {
		loadIncidents();
		loadIncident();
		complaintService.evict(incident);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "incident", message = "&zwnj;")
		}
	)
	@Action(value = "save-incident")
	public String doSave() {
		//append the time value onto the incident date
		if (incident.getDate() != null) {
			incident.setDate(DateUtils.mergeDateTime(incident.getDate(), incidentTime));
		}
		
		if (incident.getComplaint() == null) {
			getComplaint().addIncident(incident);
		}
		
		complaintService.saveIntake(getComplaint());
		return REDIRECT_VIEW;
	}
	
	@Override
	public void validate() {
		if (incident.getDate() == null && StringUtils.isBlank(incident.getDateDescription())) {
			addFieldError("incident.dateDescription", "If the date is not known, please describe when the incident occurred.");
		}
		super.validate();
	}
	
	private void loadIncident() {
		if (incident != null && incident.getId() != null) {
			incident = getComplaint().getIncident(incident.getId());
		}
	}
	
	private void loadIncidents() {
		List<ComplaintIncident> results = new ArrayList<ComplaintIncident>(getComplaint().getIncidents());
		lstCtrl.setResults(results);
	}
	
	public ComplaintIncident getIncident() {
		return incident;
	}
	
	public void setIncident(ComplaintIncident incident) {
		this.incident = incident;
	}
	
	@TypeConversion(converter = "gov.utah.dts.det.ccl.view.converter.InspectionTimeConverter")
	@ConversionErrorFieldValidator(message = "Incident time is not a valid time. (HH:MM AM/PM)")
	public Date getIncidentTime() {
		return incidentTime;
	}
	
	public void setIncidentTime(Date incidentTime) {
		this.incidentTime = incidentTime;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}