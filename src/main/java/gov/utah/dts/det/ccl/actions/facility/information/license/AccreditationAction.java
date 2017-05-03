package gov.utah.dts.det.ccl.actions.facility.information.license;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Accreditation;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.query.ListControls;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "accreditations-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "accreditation_form.jsp"),
	@Result(name = "view", location = "accreditations_list.jsp")
})
public class AccreditationAction extends BaseFacilityAction implements Preparable {

	private Accreditation accreditation;
	
	private ListControls lstCtrl = new ListControls();
	
	private List<PickListValue> agencies;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}
	
	@Action(value = "accreditations-list")
	public String doList() {
		loadAccreditations();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}
	
	@Action(value = "edit-accreditation")
	public String doEdit() {
		loadAccreditations();
		loadAccreditation();
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadAccreditations();
		loadAccreditation();
		facilityService.evict(accreditation);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "accreditation", message = "&zwnj;")
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "accreditation", message = "Accreditation start date must be before accreditation expiration date")
		}
	)
	@Action(value = "save-accreditation")
	public String doSave() {
		if (accreditation.getFacility() == null) {
			getFacility().addAccreditation(accreditation);
		}
		
		facilityService.saveFacility(getFacility());
		return REDIRECT_VIEW;
	}
	
	@Action(value = "delete-accreditation")
	public String doDelete() {
		getFacility().removeAccreditation(accreditation.getId());
		facilityService.saveFacility(getFacility());
		
		return REDIRECT_VIEW;
	}
	
	private void loadAccreditation() {
		if (accreditation != null && accreditation.getId() != null) {
			accreditation = getFacility().getAccreditation(accreditation.getId());
		}
	}
	
	private void loadAccreditations() {
		List<Accreditation> results = new ArrayList<Accreditation>(getFacility().getAccreditations());
		lstCtrl.setResults(results);
	}
	
	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public Accreditation getAccreditation() {
		return accreditation;
	}
	
	public void setAccreditation(Accreditation accreditation) {
		this.accreditation = accreditation;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public List<PickListValue> getAgencies() {
		if (agencies == null) {
			agencies = pickListService.getValuesForPickList("Accreditation", true);
		}
		return agencies;
	}
}