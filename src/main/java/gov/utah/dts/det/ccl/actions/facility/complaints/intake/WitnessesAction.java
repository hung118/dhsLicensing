package gov.utah.dts.det.ccl.actions.facility.complaints.intake;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.query.ListControls;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "witnesses-list", type = "redirectAction", params = {"facilityId", "${facilityId}", "complaintId", "${complaintId}"}),
	@Result(name = "input", location = "witness_form.jsp"),
	@Result(name = "view", location = "witnesses_list.jsp")
})
public class WitnessesAction extends BaseIntakeAction implements Preparable {

	private Person witness;
	
	private ListControls lstCtrl = new ListControls();
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}
	
	@Action(value = "witnesses-list")
	public String doList() {
		loadWitnesses();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}
	
	@Action(value = "edit-witness")
	public String doEdit() {
		loadWitnesses();
		loadWitness();
		
		return INPUT;
	}
	
	@Action(value = "delete-witness")
	public String doDelete() {
		getComplaint().removeWitness(witness.getId());
		complaintService.saveIntake(getComplaint());
		
		return REDIRECT_VIEW;
	}
	
	public void prepareDoSave() {
		loadWitnesses();
		loadWitness();
		complaintService.evict(witness);
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "witness.firstName", message = "First name is required.")
		}	
	)
	@Action(value = "save-witness")
	public String doSave() {
		if (witness.getId() == null) {
			getComplaint().addWitness(witness);
		}
		
		complaintService.saveIntake(getComplaint());
		return REDIRECT_VIEW;
	}
	
	private void loadWitness() {
		if (witness != null && witness.getId() != null) {
			witness = getComplaint().getWitness(witness.getId());
		}
	}
	
	private void loadWitnesses() {
		List<Person> results = new ArrayList<Person>(getComplaint().getWitnesses());
		lstCtrl.setResults(results);
	}
	
	public Person getWitness() {
		return witness;
	}
	
	public void setWitness(Person witness) {
		this.witness = witness;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}