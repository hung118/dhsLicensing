package gov.utah.dts.det.ccl.model.enums;

public enum FacilityType {

	LICENSE_FOSTER_CARE('F', "License Foster Care"),
	LICENSE_SPECIFIC_CARE('S', "License Specific Care"),
	TREATMENT('T',"Treatment"),
	DSPD_CERTIFIED('D', "DSPD Certified");
	
	private final char character;
	private final String displayName;
	
	private FacilityType(char character, String displayName) {
		this.character = character;
		this.displayName = displayName;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public static FacilityType valueOf(char character) {
		for (FacilityType type : FacilityType.values()) {
			if (type.character == character) {
				return type;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid facility type");
	}
}