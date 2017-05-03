package gov.utah.dts.det.ccl.actions.facility.complaints.intake;


import gov.utah.dts.det.ccl.model.Complaint;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "narrative_form.jsp"),
	@Result(name = "success", location = "view-narrative", type = "redirectAction", params = {"facilityId", "${facilityId}", "complaintId", "${complaintId}"})
})
public class NarrativeAction extends BaseIntakeAction implements Preparable {
	
	private Complaint complaint;
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	@Action(value = "view-narrative", results = {
		@Result(name = "success", location = "narrative_detail.jsp")
	})
	public String doView() {
		complaint = super.getComplaint();
		if (complaint == null) {
			return null;
		}
		if (StringUtils.isEmpty(complaint.getNarrative()) && hasPermission("save-intake")) {
			return doEdit();
		}
		
		return SUCCESS;
	}
	
	@Action(value = "edit-narrative")
	public String doEdit() {
		complaint = super.getComplaint();
		if (complaint == null) {
			return null;
		}
		if (!hasPermission("save-intake")) {
			return doView();
		}
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		complaint = super.getComplaint();
		complaintService.evict(complaint);
	}
	
	@Action(value = "save-narrative")
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "complaint.narrative", message = "Narrative: Narrative text is required.")
		}
	)
	public String doSave() {
		if (!hasPermission("save-intake")) {
			return doView();
		}
		complaintService.saveIntake(complaint);
		
		return SUCCESS;
	}
	
	@Override
	public Complaint getComplaint() {
		return complaint;
	}
	
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
}