package gov.utah.dts.det.ccl.model.enums;

public enum FacilityEventType {

	ACTION_LOG_ENTRY("Action Log Entry", "ACTION LOG"),
	FACILITY_NOTE("Facility Note", "NOTE - FACILITY"),
	INSPECTION("Inspection", "INSPECTION"),
	INSPECTION_NOTE("Inspection Note", "NOTE - INSPECTION"),
	CMP_NOTE("CMP Note", "NOTE - CMP"),
	COMPLAINT("Complaint", "COMPLAINT"),
	COMPLAINT_NOTE("Complaint Note", "NOTE - COMPLAINT"),
//	UNLICENSED_COMPLAINT("Unlicensed Complaint", "UNLICENSED COMPLAINT"),
//	UNLICENSED_COMPLAINT_NOTE("Unlicensed Complaint Note", "NOTE - UNLICENSED_COMPLAINT"),
	VARIANCE("Variance", "VARIANCE"),
	VARIANCE_NOTE("Variance Note", "NOTE - VARIANCE"),
	INCIDENT_AND_INJURY("Incident and Injury", "INCIDENT_AND_INJURY"),
	INCIDENT_AND_INJURY_NOTE("Incident and Injury Note", "NOTE - INCIDENT_AND_INJURY"),
	DATA_CONVERSION_NOTE("Data Conversion Note", "NOTE - DATA_CONVERSION");
	
	private final String displayName;
	private final String typeName;
	
	private FacilityEventType(String displayName, String typeName) {
		this.displayName = displayName;
		this.typeName = typeName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public static FacilityEventType valueByTypeName(String facilityEventTypeName) {
		for (FacilityEventType type : FacilityEventType.values()) {
			if (type.typeName.equals(facilityEventTypeName)) {
				return type;
			}
		}
		throw new IllegalArgumentException(facilityEventTypeName + " is not a valid facility event type");
	}
}