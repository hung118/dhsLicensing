package gov.utah.dts.det.ccl.actions.facility.information.owners;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-edit", location = "edit-ownership", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "redirect-view", location = "view-ownership", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "ownership_form.jsp"),
	@Result(name = "view", location = "ownership_detail.jsp")
})
public class OwnershipAction extends BaseFacilityAction implements Preparable {

	private PickListValue ownershipType;
	private String ownerName;
	
	private List<PickListValue> ownershipTypes;
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	@SkipValidation
	@Action(value = "ownership-section")
	public String doSection() {
		if (getFacility().getOwnershipType() == null) {
			initForm();
			return INPUT;
		}
		
		return VIEW;
	}

	@SkipValidation
	@Action(value = "view-ownership")
	public String doView() {
		if (getFacility().getOwnershipType() == null) {
			return REDIRECT_EDIT;
		}
		
		return VIEW;
	}

	@SkipValidation
	@Action(value = "edit-ownership")
	public String doEdit() {
		initForm();
		return INPUT;
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "ownershipType", message = "Ownership type is required.")
		}
	)
	@Action(value = "save-ownership")
	public String doSave() {
		getFacility().setOwnershipType(ownershipType);
		getFacility().setOwnerName(ownerName);
		
		facilityService.saveFacility(getFacility());
		
		return REDIRECT_VIEW;
	}
	
	@Override
	public void validate() {
		if (ownershipType != null && !facilityService.isPeopleOwnershipType(ownershipType) &&
				StringUtils.isBlank(ownerName)) {
			addFieldError("ownerName", "Owner name is required with this ownership type.");
		}
	}
	
	public void initForm() {
		ownershipType = getFacility().getOwnershipType();
		ownerName = getFacility().getOwnerName();
	}
	
	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public PickListValue getOwnershipType() {
		return ownershipType;
	}
	
	public void setOwnershipType(PickListValue ownershipType) {
		this.ownershipType = ownershipType;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public List<PickListValue> getOwnershipTypes() {
		if (ownershipTypes == null) {
			ownershipTypes = pickListService.getValuesForPickList("Ownership Type", true);
		}
		return ownershipTypes;
	}
}