package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.admin.view.KeyValuePair;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.InspectionSpecialist;
import gov.utah.dts.det.ccl.model.InspectionType;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.enums.SpecialistType;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.ccl.view.YesNoChoice;
import gov.utah.dts.det.util.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class BaseInspectionEditAction extends BaseInspectionAction {

	protected UserService userService;
	
	protected Inspection inspection;
	
	private Long licenseId;
	
	private Person primarySpecialist;
	private Person secondSpecialist;
	private Person thirdSpecialist;
	
	private PickListValue primaryType;
	protected Set<PickListValue> nonPrimaryTypes;
	
	private List<PickListValue> inspectionTypes;
	private Set<Person> licensingSpecialists;
	
	@Override
	public void validate() {
		if (secondSpecialist != null && secondSpecialist.equals(primarySpecialist)) {
			addFieldError("inspection.secondSpecialist", "Second specialist cannot be the same as the primary specialist.");
		}
		if (thirdSpecialist != null && (thirdSpecialist.equals(primarySpecialist) || thirdSpecialist.equals(secondSpecialist))) {
			addFieldError("inspection.thirdSpecialist", "Third specialist cannot be the same as the primary or second specialist.");
		}
		if (inspection.getArrivalTime() != null && inspection.getDepartureTime() != null && 
				inspection.getArrivalTime().compareTo(inspection.getDepartureTime()) >= 0) {
			addFieldError("inspection.departureTime", "Departure time must be after the arrival time.");
		}
		if (!inspection.getFollowUps().isEmpty()) {
			//if there are followups on this inspection make sure they don't deselect one of the follow up types.
			boolean followUpTypeSelected = false;
			for (InspectionType type : inspection.getTypes()) {
				if (applicationService.propertyContainsPickListValue(type.getInspectionType(), ApplicationPropertyKey.FOLLOW_UP_TYPES.getKey())) {
					followUpTypeSelected = true;
					break;
				}
			}
			if (!followUpTypeSelected) {
				addFieldError("inspection.nonPrimaryTypes", "Follow ups are attached to this inspection so a follow up type must be selected.");
			}
		}
	}
	
	protected void loadInspection() {
		inspection = super.getInspection();
		//load in the license
		if (inspection != null) {
			licenseId = inspection.getLicense().getId();
		}
		//load in the specialists
		if (inspection != null && !inspection.getSpecialists().isEmpty()) {
			for (InspectionSpecialist spec : inspection.getSpecialists()) {
				if (spec.getSpecialistType() == SpecialistType.PRIMARY) {
					primarySpecialist = spec.getSpecialist();
				} else if (spec.getSpecialistType() == SpecialistType.SECOND) {
					secondSpecialist = spec.getSpecialist();
				} else {
					thirdSpecialist = spec.getSpecialist();
				}
			}
		}
		//load the inspection types
		nonPrimaryTypes = new HashSet<PickListValue>();
		if (inspection != null && !inspection.getTypes().isEmpty()) {
			for (InspectionType type : inspection.getTypes()) {
				if (type.isPrimary()) {
					primaryType = type.getInspectionType();
				} else {
					nonPrimaryTypes.add(type.getInspectionType());
				}
			}
		}
	}
	
	protected void updateInspection() {
		if (licenseId != null && inspection.getLicense() == null) {
			License license = facilityService.loadLicenseById(licenseId);
			if (license != null) {
				inspection.setLicense(license);
			}
		}
		inspection.getSpecialists().clear();
		inspection.getSpecialists().add(new InspectionSpecialist(inspection, primarySpecialist, SpecialistType.PRIMARY));
		if (secondSpecialist != null) {
			inspection.getSpecialists().add(new InspectionSpecialist(inspection, secondSpecialist, SpecialistType.SECOND));
		}
		if (thirdSpecialist != null) {
			inspection.getSpecialists().add(new InspectionSpecialist(inspection, thirdSpecialist, SpecialistType.THIRD));
		}
		inspection.getTypes().clear();
		if (nonPrimaryTypes != null) {
			for (PickListValue type : nonPrimaryTypes) {
				inspection.getTypes().add(new InspectionType(inspection, type, false));
			}
		}
		//add the primary type last so that if the type happens to have been sent as both non-primary and primary it is set as the primary
		inspection.getTypes().add(new InspectionType(inspection, primaryType, true));
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public Inspection getInspection() {
		return inspection;
	}
	
	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}
	
	public Long getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(Long licenseId) {
		this.licenseId = licenseId;
	}

	public Person getPrimarySpecialist() {
		return primarySpecialist;
	}
	
	public void setPrimarySpecialist(Person primarySpecialist) {
		this.primarySpecialist = primarySpecialist;
	}
	
	public Person getSecondSpecialist() {
		return secondSpecialist;
	}
	
	public void setSecondSpecialist(Person secondSpecialist) {
		this.secondSpecialist = secondSpecialist;
	}
	
	public Person getThirdSpecialist() {
		return thirdSpecialist;
	}
	
	public void setThirdSpecialist(Person thirdSpecialist) {
		this.thirdSpecialist = thirdSpecialist;
	}
	
	public PickListValue getPrimaryType() {
		return primaryType;
	}
	
	public void setPrimaryType(PickListValue primaryType) {
		this.primaryType = primaryType;
	}
	
	public Set<PickListValue> getNonPrimaryTypes() {
		return nonPrimaryTypes;
	}
	
	public void setNonPrimaryTypes(Set<PickListValue> nonPrimaryTypes) {
		this.nonPrimaryTypes = nonPrimaryTypes;
	}
	
	public List<KeyValuePair> getTimes() {
		return DateUtils.getTimesFifteenMinuteIncrements();
	}
	
	public List<PickListValue> getInspectionTypes() {
		if (inspectionTypes == null) {
			
			/* TODO ...
			 * CKS [Jun 6, 2013] The edit screen bombs in certain situations (RM 23835) b/c the inspection types 
			 * are null when the screen builds. In this situation the pick list service is null and as a quick fix
			 * I get the pick list service from the context.   
			 */
			if (pickListService == null) {
				try {
					WebApplicationContext context = 
						WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
					pickListService = (PickListService) context.getBean(PickListService.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			inspectionTypes = pickListService.getValuesForPickList("Inspection Types", true);
			PickListValue complaintType = inspectionService.getComplaintInvestigationType();
			if (inspection == null || !inspection.hasInspectionType(complaintType)) {
				for (PickListValue type : inspectionTypes) {
					if (type.getId().equals(complaintType.getId())) {
						inspectionTypes.remove(type);
					}
				}
			}
		}
		return inspectionTypes;
	}
	
	public Set<Person> getLicensingSpecialists() {
		if (licensingSpecialists == null) {
			List<RoleType> roleTypes = new ArrayList<RoleType>();
			roleTypes.add(RoleType.ROLE_LICENSOR_SPECIALIST);
			licensingSpecialists = userService.getPeople(roleTypes, true, true, false);
		}
		return licensingSpecialists;
	}
	
	public List<YesNoChoice> getYesNoChoices() {
		return Arrays.asList(YesNoChoice.values());
	}
}