package gov.utah.dts.det.ccl.actions.facility.information.details;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.FacilityTag;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.model.enums.FacilityType;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.ccl.view.ViewUtils;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-information", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "existing-primary-contacts-list", location = "existing_primary_contacts_list.jsp"),
	@Result(name = "input", location = "information_form.jsp"),
	@Result(name = "view", location = "information_detail.jsp")
})
public class InformationAction extends BaseFacilityAction implements Preparable {

	private Facility facility;
	private FacilityStatus status;
	private Date initialRegulationDate;
	private Person licensingSpecialist;
	private PickListValue reason;
	private Date effectiveDate;
	private FacilityTag deactivation;
	private List<FacilityStatus> statuses;
	private Set<Person> licensingSpecialists;
	private List<PickListValue> deactivationReasons;
	private Set<Person> cbsTechnicians;

	private UserService userService;

	@Override
	public void prepare() throws Exception {
		facility = super.getFacility();
	}

	@SkipValidation
	@Action(value = "primary-contacts-list")
	public String doPrimaryContactsList() {
		return "existing-primary-contacts-list";
	}

	@SkipValidation
	@Action(value = "view-information")
	public String doView() {
		if (!facility.isValid()) {
			if (hasFacilityPermission("edit-details", facility)) {
				return doEdit();
			}
		}
		List<PickListValue> deactivationReasons = pickListService.getValuesForPickList("Facility Deactivation Reasons", true);
		List<FacilityTag> deacts = facility.getTags(deactivationReasons);
		if (!deacts.isEmpty()) {
			deactivation = deacts.get(0);
		}

		return VIEW;
	}

	@SkipValidation
	@Action(value = "edit-information")
	public String doEdit() {
		if (!isEditable()) {
			return doView();
		}
		status = facility.getStatus();
		initialRegulationDate = facility.getInitialRegulationDate();
		licensingSpecialist = facility.getLicensingSpecialist();

		return INPUT;
	}

	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "facility", message = "&zwnj;")
		},
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "facility.primaryPhone", message = "Primary phone is not a valid phone number. ((###) ### - ####)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.alternatePhone", message = "Alternate phone is not a valid phone number. ((###) ### - ####)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.fax", message = "Fax is not a valid phone number. ((###) ### - ####)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.safeProviderId", message = "SAFE Provider Id is not a valid number. (#######)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "facility.primaryPhone", message = "Primary phone is required."),
			@RequiredFieldValidator(fieldName = "facility.type", message = "Facility Type is required.")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "facility.email", message = "Email is required."),
			@RequiredStringValidator(fieldName = "facility.mailingAddress.addressOne", message = "Mailing Address: Address one is required."),
			@RequiredStringValidator(fieldName = "facility.mailingAddress.zipCode", message = "Mailing Address: Zip code is required.")
		}
	)
	@Action(value = "save-information")
	public String doSave() {
		if (isEditable()) {
			try {
				// redmine 29237 comment #19 - inactivate licenses if facility status is inactive.
				if (status == FacilityStatus.INACTIVE) {
					for (License l : facility.getLicenses()) {
						l.setStatus(applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.INACTIVE_LICENSE_STATUS.getKey()));
					}
				}
				
				if (status == FacilityStatus.REGULATED || status == FacilityStatus.IN_PROCESS) {
					if (facility.getStatus() == status) {
						if (initialRegulationDate != null && !initialRegulationDate.equals(facility.getInitialRegulationDate())) {
							facility.setInitialRegulationDate(initialRegulationDate);
						}
						if (licensingSpecialist != null) {
							// Update licensing specialist
							facilityService.updateLicensingSpecialist(facility, licensingSpecialist);
							facilityService.evict(facility);
						} else {
							// Save any other changes made to form
							facilityService.saveFacility(facility);
							facilityService.evict(facility);
						}
					} else {
						if (status == FacilityStatus.REGULATED) {
							facilityService.activateRegulatedFacility(facility, licensingSpecialist);
							facilityService.evict(facility);
						} else if (status == FacilityStatus.IN_PROCESS) {
							facilityService.setFacilityInProcess(facility, licensingSpecialist);
							facilityService.evict(facility);
						}
					}
				} else if (status == FacilityStatus.EXEMPT && facility.getStatus() != status) {
					facilityService.activateExemptFacility(facility);
					facilityService.evict(facility);
				} else if (status == FacilityStatus.INACTIVE && facility.getStatus() != status) {
					facilityService.deactivateFacility(facility, reason, effectiveDate);
					facilityService.evict(facility);
				}
			} catch (CclServiceException cse) {
				ViewUtils.addActionErrors(this, this, cse.getErrors());
			} catch (AccessDeniedException ade) {
				addActionError("You do not have access to make this change.");
			}

			if (hasErrors()) {
				return INPUT;
			}
		}

		return REDIRECT_VIEW;
	}

	@SkipValidation
	@Action(value = "cancel-deactivation")
	public String doCancelDeactivation() {
		try {
			facilityService.cancelFacilityDeactivation(facility);
			facilityService.evict(facility);
		} catch (AccessDeniedException ade) {
			addActionError("You do not have access to make this change.");
		}
		
		return REDIRECT_VIEW;
	}
	
	public void validate() {
		if (StringUtils.isBlank(facility.getName())) {
			if (facility.getType().equals(FacilityType.LICENSE_FOSTER_CARE) || facility.getType().equals(FacilityType.LICENSE_SPECIFIC_CARE)) {
				addFieldError("facility.name", "Last, First Name is required.");
			} else {
				addFieldError("facility.name", "Name is required.");
			}
		}
		if (status != null && (status == FacilityStatus.REGULATED || status == FacilityStatus.IN_PROCESS)) {
			if (licensingSpecialist == null && facility.getLicensingSpecialist() == null) {
				addFieldError("licensingSpecialist", "Licensing specialist is required.");
			}
			if (status == FacilityStatus.REGULATED && initialRegulationDate == null) {
				addFieldError("initialRegulationDate", "Initial Regulation Date is required.");
			}
		} else if (status != null && status == FacilityStatus.INACTIVE && (facility.getStatus() != FacilityStatus.INACTIVE && facility.getStatus() != FacilityStatus.IN_PROCESS)) {
			if (reason == null) {
				addFieldError("reason", "Reason is required.");
			}
		}
	}

	public boolean isEditable() {
		//TODO: implement editable
		return true;
	}

	@Override
	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public FacilityStatus getStatus() {
		return status;
	}
	
	public void setStatus(FacilityStatus status) {
		this.status = status;
	}
	
	public Person getLicensingSpecialist() {
		return licensingSpecialist;
	}
	
	public void setLicensingSpecialist(Person licensingSpecialist) {
		this.licensingSpecialist = licensingSpecialist;
	}
	
	public Date getInitialRegulationDate() {
		return initialRegulationDate;
	}
	
	public void setInitialRegulationDate(Date initialRegulationDate) {
		this.initialRegulationDate = initialRegulationDate;
	}
	
	public PickListValue getReason() {
		return reason;
	}
	
	public void setReason(PickListValue reason) {
		this.reason = reason;
	}
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public FacilityTag getDeactivation() {
		return deactivation;
	}
	
	public List<FacilityType> getFacilityTypes() {
		return Arrays.asList(FacilityType.values());
	}

	public List<FacilityPerson> getPrimaryContacts() {
		return getFacility().getPrimaryContacts();
	}

	public boolean isStatusEditable() {
		return SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST, RoleType.ROLE_LICENSOR_SPECIALIST) ||
			(SecurityUtil.hasAnyRole(RoleType.ROLE_BACKGROUND_SCREENING_MANAGER) && facility.getStatus() == FacilityStatus.REGULATED);
	}
	
	public List<FacilityStatus> getStatuses() {
		if (statuses == null) {
			statuses = new ArrayList<FacilityStatus>();
			if (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST, RoleType.ROLE_LICENSOR_SPECIALIST)) {
				statuses.add(FacilityStatus.REGULATED);
				// Do not allow regulated facilities to change to exempt status
				//statuses.add(FacilityStatus.EXEMPT);
				statuses.add(FacilityStatus.INACTIVE);
				if (getFacility().getStatus() == FacilityStatus.INACTIVE) {
					statuses.add(FacilityStatus.IN_PROCESS);
				}
			} else if (SecurityUtil.hasAnyRole(RoleType.ROLE_BACKGROUND_SCREENING_MANAGER) && facility.getStatus() == FacilityStatus.REGULATED) {
				statuses.add(FacilityStatus.REGULATED);
				statuses.add(FacilityStatus.INACTIVE);
			}
		}
		return statuses;
	}
	
	public Set<Person> getLicensingSpecialists() {
		if (licensingSpecialists == null) {
			licensingSpecialists = userService.getPeople(RoleType.ROLE_LICENSOR_SPECIALIST, true, true, true);
			if (licensingSpecialists == null) {
				licensingSpecialists = new HashSet<Person>();
			}
		}
		return licensingSpecialists;
	}
	
	public List<PickListValue> getDeactivationReasons() {
		if (deactivationReasons == null) {
			deactivationReasons = pickListService.getValuesForPickList("Facility Deactivation Reasons", true);
			if (deactivationReasons == null) {
				deactivationReasons = new ArrayList<PickListValue>();
			}
		}
		return deactivationReasons;
	}

	public Set<Person> getCbsTechnicians() {
		if (cbsTechnicians == null) {
			cbsTechnicians = userService.getPeople(RoleType.ROLE_BACKGROUND_SCREENING, true, true, true);
			if (cbsTechnicians == null) {
				cbsTechnicians = new HashSet<Person>();
			}
		}
		return cbsTechnicians;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}