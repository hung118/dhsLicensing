package gov.utah.dts.det.ccl.actions.facility.information.owners;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.query.ListControls;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "owners-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "owner_form.jsp"),
	@Result(name = "view", location = "owners_list.jsp")
})
public class OwnersAction extends BaseFacilityAction implements Preparable {

	private FacilityPerson owner;
	private boolean primary = false;
	
	private ListControls lstCtrl = new ListControls();
	
	private PickListValue primaryOwnerType;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}
	
	@Action(value = "owners-list")
	public String doList() {
		loadOwners();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}
	
	@Action(value = "edit-owner")
	public String doEdit() {
		loadOwners();
		loadOwner();
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		loadOwners();
		loadOwner();
		facilityService.evict(owner);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "owner", message = "&zwnj;")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "owner.person.address.addressOne", message = "Address is required."),
			@RequiredStringValidator(fieldName = "owner.person.address.zipCode", message = "Zip code is required.")
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "owner", message = "Start date must be before End date.")
		}
	)
	@Action(value = "save-owner")
	public String doSave() {
		if (owner.getId() == null) {
			owner.setFacility(getFacility());
		}
		
		facilityService.saveOwner(owner, primary);
		
		return REDIRECT_VIEW;
	}
	
	private void loadOwner() {
		if (owner != null && owner.getId() != null) {
			owner = facilityService.loadFacilityPerson(getFacility().getId(), owner.getId(), getPrimaryOwnerTypeId(), getSecondaryOwnerTypeId());
			if (owner != null && owner.getType() != null && owner.getType().getId().longValue() == getPrimaryOwnerType().getId().longValue()) {
				primary = true;
			}
		}
	}
	
	private void loadOwners() {
		lstCtrl.setResults(facilityService.getOwners(getFacility().getId()));
	}

	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public FacilityPerson getOwner() {
		return owner;
	}
	
	public void setOwner(FacilityPerson owner) {
		this.owner = owner;
	}
	
	public boolean isPrimary() {
		return primary;
	}
	
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public PickListValue getPrimaryOwnerType() {
		if (primaryOwnerType == null) {
			primaryOwnerType = facilityService.getPrimaryOwnerPersonType();
		}
		return primaryOwnerType;
	}
	
	private Long getPrimaryOwnerTypeId() {
		return getPrimaryOwnerType().getId();
	}
	
	private Long getSecondaryOwnerTypeId() {
		return facilityService.getSecondaryOwnerPersonType().getId();
	}
}