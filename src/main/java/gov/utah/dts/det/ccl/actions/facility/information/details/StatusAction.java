package gov.utah.dts.det.ccl.actions.facility.information.details;

import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityTag;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.ccl.view.ViewUtils;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;
import java.util.*;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.security.access.AccessDeniedException;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "redirectAction", location = "status-section", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "status_form.jsp")
})
public class StatusAction extends BaseFacilityAction implements Preparable {

	private UserService userService;
	
	private FacilityStatus status;
	private Date initialRegulationDate;
	private Person licensingSpecialist;
	private PickListValue reason;
	private Date effectiveDate;
	private FacilityTag deactivation;

	private List<FacilityStatus> statuses;
	private Set<Person> licensingSpecialists;
	private List<PickListValue> deactivationReasons;
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	@SkipValidation
	@Action(value = "status-section", results = {
		@Result(name = "success", location = "status_detail.jsp")
	})
	public String doView() {
		List<PickListValue> deactivationReasons = pickListService.getValuesForPickList("Facility Deactivation Reasons", true);
		
		List<FacilityTag> deacts = getFacility().getTags(deactivationReasons);
		if (!deacts.isEmpty()) {
			deactivation = deacts.get(0);
		}
		
		return SUCCESS;
	}

	@SkipValidation
	@Action(value = "edit-status")
	public String doEdit() {
		initDetails();
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		initDetails();
	}

	@Action(value = "save-status")
	public String doSave() {
		try {
			if (status == FacilityStatus.REGULATED || status == FacilityStatus.IN_PROCESS) {
				if (getFacility().getStatus() == status) {
					if (licensingSpecialist != null) {
						facilityService.updateLicensingSpecialist(getFacility(), licensingSpecialist);
					}
					if (initialRegulationDate != null) {
						facilityService.updateInitialRegulationDate(getFacility(), initialRegulationDate);
					}
				} else {
					if (status == FacilityStatus.REGULATED) {
						facilityService.activateRegulatedFacility(getFacility(), licensingSpecialist);
					} else if (status == FacilityStatus.IN_PROCESS) {
						facilityService.setFacilityInProcess(getFacility(), licensingSpecialist);
					}
				}
			} else if (status == FacilityStatus.EXEMPT && getFacility().getStatus() != status) {
				facilityService.activateExemptFacility(getFacility());
			} else if (status == FacilityStatus.INACTIVE && getFacility().getStatus() != status) {
				facilityService.deactivateFacility(getFacility(), reason, effectiveDate);
			}
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
		} catch (AccessDeniedException ade) {
			addActionError("You do not have access to make this change.");
		}
		
		if (hasErrors()) {
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	@Action(value = "cancel-deactivation")
	public String doCancelDeactivation() {
		try {
			facilityService.cancelFacilityDeactivation(getFacility());
		} catch (AccessDeniedException ade) {
			addActionError("You do not have access to make this change.");
		}
		
		if (hasErrors()) {
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	
	public void validate() {
		if (status != null && (status == FacilityStatus.REGULATED || status == FacilityStatus.IN_PROCESS)) {
			if (licensingSpecialist == null && getFacility().getLicensingSpecialist() == null) {
				addFieldError("licensingSpecialist", "Licensing specialist is required.");
			}
		} else if (status != null && status == FacilityStatus.INACTIVE && (getFacility().getStatus() != FacilityStatus.INACTIVE &&
				getFacility().getStatus() != FacilityStatus.IN_PROCESS)) {
			if (reason == null) {
				addFieldError("reason", "Reason is required.");
			}
		}
	}
	
	private void initDetails() {
		statuses = new ArrayList<FacilityStatus>();
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST)) {
			statuses.add(FacilityStatus.REGULATED);
			statuses.add(FacilityStatus.EXEMPT);
			statuses.add(FacilityStatus.INACTIVE);
			if (getFacility().getStatus() == FacilityStatus.INACTIVE) {
				statuses.add(FacilityStatus.IN_PROCESS);
			}
		} else if (SecurityUtil.hasAnyRole(RoleType.ROLE_BACKGROUND_SCREENING_MANAGER) && getFacility().getStatus() == FacilityStatus.REGULATED) {
			statuses.add(FacilityStatus.REGULATED);
			statuses.add(FacilityStatus.INACTIVE);
		}
		
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST)) {
			licensingSpecialists = userService.getPeople(RoleType.ROLE_LICENSOR_SPECIALIST, true, true, false);
			licensingSpecialist = getFacility().getLicensingSpecialist();
		} else {
			licensingSpecialists = new HashSet<Person>();
		}
		
		status = getFacility().getStatus();
		
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN)) {
			initialRegulationDate = getFacility().getInitialRegulationDate();
		}
	}
	
	public boolean isStatusEditable() {
		return SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN, RoleType.ROLE_OFFICE_SPECIALIST) ||
			(getFacility().getStatus() == FacilityStatus.REGULATED &&
					SecurityUtil.hasAnyRole(RoleType.ROLE_BACKGROUND_SCREENING_MANAGER));
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public Facility getFacility() {
		return super.getFacility();
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
	
	public List<FacilityStatus> getStatuses() {
		return statuses;
	}
	
	public Set<Person> getLicensingSpecialists() {
		return licensingSpecialists;
	}
	
	public List<PickListValue> getDeactivationReasons() {
		if (deactivationReasons == null) {
			deactivationReasons = pickListService.getValuesForPickList("Facility Deactivation Reasons", true);
		}
		return deactivationReasons;
	}
}