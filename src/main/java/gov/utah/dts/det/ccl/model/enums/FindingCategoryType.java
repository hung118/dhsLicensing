package gov.utah.dts.det.ccl.model.enums;

public enum FindingCategoryType {

	TA('T', "Technical Assistance", false, false),
	CITED('C', "Cited", false, true),
	REPEAT_CITED('R', "Repeat Cited", true, true);
	
	private final char character;
	private final String displayName;
	private final boolean displayCmp;
	private final boolean displayWarning;
	
	private FindingCategoryType(char character, String displayName, boolean displayCmp, boolean displayWarning) {
		this.character = character;
		this.displayName = displayName;
		this.displayCmp = displayCmp;
		this.displayWarning = displayWarning;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public boolean isDisplayCmp() {
		return displayCmp;
	}
	
	public boolean isDisplayWarning() {
		return displayWarning;
	}
	
	public static FindingCategoryType valueOf(char character) {
		for (FindingCategoryType type : FindingCategoryType.values()) {
			if (type.character == character) {
				return type;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid finding category type");
	}
}
