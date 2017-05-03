package gov.utah.dts.det.ccl.view.facility;

public enum FacilityStatus {

	REGULATED("Active Regulated"),
	CERTIFIED_PROVIDER("Certified Provider"),
	EXEMPT("Active Exempt"),
	IN_PROCESS("Inactive - In Process"),
	INACTIVE("Inactive");
	
	private final String label;
	
	private FacilityStatus(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}