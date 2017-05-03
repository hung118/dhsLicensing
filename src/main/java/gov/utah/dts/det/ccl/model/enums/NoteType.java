package gov.utah.dts.det.ccl.model.enums;

import java.io.Serializable;

public enum NoteType implements Serializable {

	INSPECTION("Inspection"),
	FACILITY("Facility"),
	COMPLAINT("Complaint"),
	CMP("CMP"),
	VARIANCE("Variance"),
	INCIDENT_AND_INJURY("Incident & Injury"),
	UNLICENSED_COMPLAINT("Unlicensed Complaint"),
	BACKGROUND_SCREENING("Background Screening"),
	DATA_CONVERSION("Data Conversion");
	
	private final String noteTypeName;
	
	private NoteType(String noteTypeName) {
		this.noteTypeName = noteTypeName;
	}
	
	public String getNoteTypeName() {
		return noteTypeName;
	}
}