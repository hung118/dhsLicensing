package gov.utah.dts.det.ccl.actions.facility.complaints.intake;

import gov.utah.dts.det.ccl.model.ComplaintPerson;
import gov.utah.dts.det.query.ListControls;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "people-involved-list", type = "redirectAction", params = {"facilityId", "${facilityId}", "complaintId", "${complaintId}"}),
	@Result(name = "input", location = "person_involved_form.jsp"),
	@Result(name = "view", location = "people_involved_list.jsp")
})
public class PeopleInvolvedAction extends BaseIntakeAction implements Preparable {
	
	private ComplaintPerson personInvolved;
	
	private ListControls lstCtrl = new ListControls();
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}
	
	@SkipValidation
	@Action(value = "people-involved-list")
	public String doList() {
		loadPeopleInvolved();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}
	
	@SkipValidation
	@Action(value = "edit-person-involved")
	public String doEdit() {
		loadPeopleInvolved();
		loadPersonInvolved();
		
		return INPUT;
	}
	
	@SkipValidation
	@Action(value = "delete-person-involved")
	public String doDelete() {
		getComplaint().removePersonInvolved(personInvolved.getId());
		complaintService.saveIntake(getComplaint());
		
		return REDIRECT_VIEW;
	}
	
	public void prepareDoSave() {
		loadPeopleInvolved();
		loadPersonInvolved();
		complaintService.evict(personInvolved);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "personInvolved", message = "&zwnj;")
		}
	)
	@Action(value = "save-person-involved")
	public String doSave() {
		if (personInvolved.getComplaint() == null) {
			getComplaint().addPersonInvolved(personInvolved);
		}
		
		complaintService.saveIntake(getComplaint());
		return REDIRECT_VIEW;
	}
	
	@Override
	public void validate() {
		if (StringUtils.isBlank(personInvolved.getFirstName()) && StringUtils.isBlank(personInvolved.getLastName())) {
			addActionError("Either First or Last name is required.");
		}
	}
	
	private void loadPersonInvolved() {
		if (personInvolved != null && personInvolved.getId() != null) {
			personInvolved = getComplaint().getPersonInvolved(personInvolved.getId());
		}
	}
	
	private void loadPeopleInvolved() {
		List<ComplaintPerson> results = new ArrayList<ComplaintPerson>(getComplaint().getPeopleInvolved());
		lstCtrl.setResults(results);
	}
	
	public ComplaintPerson getPersonInvolved() {
		return personInvolved;
	}
	
	public void setPersonInvolved(ComplaintPerson personInvolved) {
		this.personInvolved = personInvolved;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
}