package gov.utah.dts.det.ccl.actions.facility.complaints.intake;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.ComplaintComplainant;
import gov.utah.dts.det.ccl.model.enums.NameUsage;
import gov.utah.dts.det.ccl.view.YesNoChoice;

import java.util.Arrays;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-complainant", type = "redirectAction", params = {"facilityId", "${facilityId}", "complaintId", "${complaintId}"}),
	@Result(name = "input", location = "complainant_form.jsp"),
	@Result(name = "view", location = "complainant_detail.jsp")
})
public class ComplainantAction extends BaseIntakeAction implements Preparable {

	private ComplaintComplainant complainant;
	private boolean nameRefused = false;
	private boolean nameConfidential = false;
	private boolean confidentialIndefinitely = true;
	
	private List<PickListValue> complainantRelationships;
	
	@Override
	public void prepare() throws Exception {
		
	}

	@SkipValidation
	@Action(value = "view-complainant")
	public String doView() {
		complainant = super.getComplaint().getComplainant();
		if (complainant == null) {
			return INPUT;
		}
		
		return VIEW;
	}

	@SkipValidation
	@Action(value = "edit-complainant")
	public String doEdit() {
		complainant = super.getComplaint().getComplainant();
		if (complainant != null) {
			switch (complainant.getNameUsage()) {
				case NON_CONFIDENTIAL:
					nameRefused = false;
					nameConfidential = false;
					break;
				case NAME_REFUSED:
					nameRefused = true;
					nameConfidential = false;
					break;
				case CONFIDENTIAL_INDEFINITELY:
					nameRefused = false;
					nameConfidential = true;
					confidentialIndefinitely = true;
					break;
				case CONFIDENTIAL_UNTIL_END:
					nameRefused = false;
					nameConfidential = true;
					confidentialIndefinitely = false;
					break;
			}
		}
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		complainant = super.getComplaint().getComplainant();
		if (complainant != null) {
			complainant.getPerson().getAddress();
		}
		complaintService.evict(complainant);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "complainant", message = "&zwnj;")
		}
	)
	@Action(value = "save-complainant")
	public String doSave() {
		if (complainant.getId() == null) {
			super.getComplaint().setComplainant(complainant);
//			complainant.setComplaint(super.getComplaint());
		}
		
		if (nameRefused) {
			complainant.setNameUsage(NameUsage.NAME_REFUSED);
		} else if (nameConfidential && confidentialIndefinitely) {
			complainant.setNameUsage(NameUsage.CONFIDENTIAL_INDEFINITELY);
		} else if (nameConfidential && !confidentialIndefinitely) {
			complainant.setNameUsage(NameUsage.CONFIDENTIAL_UNTIL_END);
		} else {
			complainant.setNameUsage(NameUsage.NON_CONFIDENTIAL);
		}
		
		complaintService.saveIntake(super.getComplaint());
		return REDIRECT_VIEW;
	}
	
	@Override
	public void validate() {
		/*
		 * Redmine #26371.  Complainant name no longer required.
		 * 
		if (!nameRefused && (complainant.getPerson() == null || StringUtils.isEmpty(complainant.getPerson().getFirstName()))) {
			addFieldError("complainant.person.firstName", "First name is required.");
		}
		if (!nameRefused && (complainant.getPerson() == null || StringUtils.isEmpty(complainant.getPerson().getLastName()))) {
			addFieldError("complainant.person.firstName", "Last name is required.");
		}
		 */
	}
	
	public ComplaintComplainant getComplainant() {
		return complainant;
	}
	
	public void setComplainant(ComplaintComplainant complainant) {
		this.complainant = complainant;
	}

	public boolean isNameRefused() {
		return nameRefused;
	}
	
	public void setNameRefused(boolean nameRefused) {
		this.nameRefused = nameRefused;
	}
	
	public boolean isNameConfidential() {
		return nameConfidential;
	}
	
	public void setNameConfidential(boolean nameConfidential) {
		this.nameConfidential = nameConfidential;
	}
	
	public boolean isConfidentialIndefinitely() {
		return confidentialIndefinitely;
	}
	
	public void setConfidentialIndefinitely(boolean confidentialIndefinitely) {
		this.confidentialIndefinitely = confidentialIndefinitely;
	}
	
	public List<PickListValue> getComplainantRelationships() {
		if (complainantRelationships == null) {
			complainantRelationships = pickListService.getValuesForPickList("Complaint Relationship to Provider", true);
		}
		return complainantRelationships;
	}
	
	public List<YesNoChoice> getYesNoChoices() {
		return Arrays.asList(YesNoChoice.values());
	}
}