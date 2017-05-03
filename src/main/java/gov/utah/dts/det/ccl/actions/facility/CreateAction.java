package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.FacilityType;
import gov.utah.dts.det.ccl.service.FacilityService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.UserService;
import java.util.ArrayList;
import java.util.Set;

@Results({
	@Result(name = "input", location = "facilityCreate", type = "tiles"),
	@Result(name = "success", location = "edit-facility", type = "redirectAction", params = {"facilityId", "${facility.id}"})
})
public class CreateAction extends ActionSupport {

	private static final long serialVersionUID = 5135090511088617360L;

	private FacilityService facilityService;
	private PickListService pickListService;
	//private RegionService regionService;
	protected UserService userService;
	
	private Facility facility;
	
	private Person owner;
	private Person licensingSpecialist;
	
	private Map<String, Object> response;
	
	private List<FacilityType> facilityTypes;
	private List<PickListValue> licenseTypes;
	private List<Person> licensingSpecialists;
	
	@SkipValidation
	@Action(value = "create-facility")
	public String doCreate() {
		if (facility == null) {
			facility = new Facility();
		}
		if (facility.getType() == null) {
			facility.setType(FacilityType.LICENSE_FOSTER_CARE);
		}
		return INPUT;
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "facility", message = "&zwnj;")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "facility.type", message = "Facility type is required.")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "facility.mailingAddress.addressOne", message = "Mailing Address: Address one is required."),
			@RequiredStringValidator(fieldName = "facility.mailingAddress.zipCode", message = "Mailing Address: Zip code is required."),
			@RequiredStringValidator(fieldName = "facility.locationAddress.addressOne", message = "Location Address: Address one is required."),
			@RequiredStringValidator(fieldName = "facility.locationAddress.zipCode", message = "Location Address: Zip code is required.")           
		}
	)
	@Action(value = "save-new-facility")
	public String doSave() {
		facility = facilityService.createLicensedFacility(facility, licensingSpecialist);

		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if (StringUtils.isBlank(facility.getName())) {
			if (facility.getType().equals(FacilityType.LICENSE_FOSTER_CARE) || facility.getType().equals(FacilityType.LICENSE_SPECIFIC_CARE)) {
				addFieldError("facility.name", "Last, First Name is required.");
			} else {
				addFieldError("facility.name", "Name is required.");
			}
		}
		if (facility.getPrimaryPhone() == null || StringUtils.isBlank(facility.getPrimaryPhone().getPhoneNumber())) {
			addFieldError("facility.primaryPhone", "Primary phone number is required.");
		}
		if (facility.getLocationAddress() == null || StringUtils.isBlank(facility.getLocationAddress().getAddressOne())) {
			addFieldError("facility.locationAddress.addressOne", "Location Address address one is required.");
		}
		if (facility.getLocationAddress() == null || StringUtils.isBlank(facility.getLocationAddress().getZipCode())) {
			addFieldError("facility.locationAddress.zipCode", "Location Address zip code is required.");
		}
		if (facility.getEmail() == null || StringUtils.isBlank(facility.getEmail())) {
			addFieldError("facility.getEmail()", "Email address is required.");
		}
		if (licensingSpecialist == null) {
			addFieldError("licensingSpecialist", "Licensing Specialist is required");
		}
	}
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
		
	public Facility getFacility() {
		return facility;
	}
	
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	public Person getOwner() {
		return owner;
	}
	
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	
	public Person getLicensingSpecialist() {
		return licensingSpecialist;
	}

	public void setLicensingSpecialist(Person licensingSpecialist) {
		this.licensingSpecialist = licensingSpecialist;
	}
		
	public Map<String, Object> getResponse() {
		return response;
	}
	
	public List<FacilityType> getFacilityTypes() {
		if (facilityTypes == null) {
			facilityTypes = Arrays.asList(FacilityType.values());
		}
		return facilityTypes;
	}
	
	public List<PickListValue> getLicenseTypes() {
		if (licenseTypes == null) {
			licenseTypes = pickListService.getValuesForPickList("License Type", true);
		}
		return licenseTypes;
	}
	
	/*
	public List<Person> getLicensingSpecialists() {
		if (licensingSpecialists == null) {
			licensingSpecialists = regionService.getSpecialistsInAllRegions();
		}
		return licensingSpecialists;
	}
	*/
	
	public List<Person> getLicensingSpecialists() {
		if (licensingSpecialists == null) {
			licensingSpecialists = new ArrayList<Person>();
			List<RoleType> roleTypes = new ArrayList<RoleType>();
			roleTypes.add(RoleType.ROLE_LICENSOR_SPECIALIST);
			Set set = userService.getPeople(roleTypes, true, true, false);
			
			licensingSpecialists.addAll(set);
		}
		return licensingSpecialists;
	}
}