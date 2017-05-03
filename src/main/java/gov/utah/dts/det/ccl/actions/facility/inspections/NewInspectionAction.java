package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.service.ComplaintService;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "new_inspection_form.jsp"),
	@Result(name = "success", location = "edit-inspection-record", type = "redirectAction", params = {"namespace", "/facility/inspections", "facilityId", "${facilityId}", "inspectionId", "${inspection.id}"})
})
public class NewInspectionAction extends BaseInspectionEditAction {

	private ComplaintService complaintService;
	
	private Long complaintId;
	
	@SkipValidation
	@Action(value = "new-inspection")
	public String doForm() {
		if (complaintId != null) {
			PickListValue complaintType = inspectionService.getComplaintInvestigationType();
			inspection = new Inspection();
			setPrimaryType(complaintType);
		}
		
		return INPUT;
	}
	

	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "licenseId", message = "License is required."),
			@RequiredFieldValidator(fieldName = "primarySpecialist", message = "Primary specialist is required."),
			@RequiredFieldValidator(fieldName = "primaryType", message = "Primary inspection type is required.")
		},
		visitorFields = {
			@VisitorFieldValidator(fieldName = "inspection", message = "&zwnj;")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "inspection.findingsComment", message = "Findings are required.")
		}
	)
	@Action(value = "save-new-inspection")
	public String doSave() {
		inspection.setFacility(getFacility());
		if (complaintId != null) {
			Complaint complaint = complaintService.loadById(complaintId);			
			inspection.getComplaints().add(complaint);
		}
		
		super.updateInspection();
		inspectionService.createInspection(inspection);
	
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		super.validate();
	}
	
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	public Long getComplaintId() {
		return complaintId;
	}
	
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	
	@Override
	public Facility getFacility() {
		return super.getFacility();
	}

	@Override
	public Inspection getInspection() {
		return super.getInspection();
	}
	
	/**
	 * Determines whether the current inspection is a complaint investigation and returns the complaint investigation pick list value if
	 * it is.  If it is not a complaint investigation it will return null.  This is so the front end is able to force the complaint
	 * investigation inspection type to remain selected.
	 * 
	 * @return The complaint investigation pick list value if this inspection is a complaint investigation, otherwise null.
	 */
	public PickListValue getComplaintTypeIfComplaintInvestigation() {
		if (getInspection() != null) {
			PickListValue complaintType = inspectionService.getComplaintInvestigationType();
			if (getInspection().hasInspectionType(complaintType)) {
				return complaintType;
			}
		}
		return null;
	}
	
	public List<License> getActiveLicenses() {
		return getFacility().getActiveLicenses();
	}
}