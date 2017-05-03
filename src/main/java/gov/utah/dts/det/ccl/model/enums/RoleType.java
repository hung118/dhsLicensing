package gov.utah.dts.det.ccl.model.enums;

import java.io.Serializable;

public enum RoleType implements Serializable {

	ROLE_SUPER_ADMIN("Super Admin", true),
	ROLE_ADMIN_MANAGER("Admin Manager", true),
	
	ROLE_OFFICE_SPECIALIST("Office Specialist", true),
	ROLE_LICENSOR_SPECIALIST("Licensor Specialist", true), //ROLE_LICENSING_SPECIALIST("Licensing Specialist", true),
	ROLE_LICENSOR_MANAGER("Licensor Manager", true), //ROLE_LEAD_LICENSOR("Lead Licensor", true),
	ROLE_LICENSING_DIRECTOR("Licensing Director", true),

	ROLE_BACKGROUND_SCREENING_MANAGER("Background Screening Manager", true),
	ROLE_BACKGROUND_SCREENING("Background Screening", true),
	
	ROLE_FACILITY_PROVIDER("Facility Provider", false), // was ROLE_FACILITY_CONTACT("Facility Contact", false);
	ROLE_DWS_PARTNER_AGENCY("DWS Partner Agency", false), // being removed? if removed, won't wok for Redmine 29237
	ROLE_CCR_R_PARTNER_AGENCY("CCR&R Partner Agency", false), // being removed?
	ROLE_FOOD_PROGRAM("Food Program", false),	// being removed?
	ROLE_ACCESS_PROFILE_VIEW("Access Profile View", false);
	
	/*
	 * Redmine #26372.  Removed ROLE_COMPLAINT_SCREENING_MANAGER and ROLE_COMPLAINT_INVESTIGATOR
	 */ 
	//ROLE_COMPLAINT_SCREENING_MANAGER("Complaint Screening Manager", true),
	//ROLE_COMPLAINT_INVESTIGATOR("Complaint Investigator", true),

	
	// removed ROLE_REGIONAL_PROGRAM_MANAGER("Regional Program Manager", true),
	// removed ROLE_PROFICIENCY_MANAGER("Proficiency Manager", true),
	// removed ROLE_PLAYGROUND_SPECIALIST("Playground Specialist", true),
	// removed ROLE_DWS_PARTNER_AGENCY("DWS Partner Agency", false),
	// removed ROLE_CCR_R_PARTNER_AGENCY("CCR&R Partner Agency", false),
	// removed ROLE_FOOD_PROGRAM("Food Program", false),
	// removed ROLE_PLAYGROUND_MANAGER("Playground Manager", true),
	
	private final String displayName;
	private final boolean internal;
	
	private RoleType(String displayName, boolean internal) {
		this.displayName = displayName;
		this.internal = internal;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public boolean isInternal() {
		return internal;
	}
}