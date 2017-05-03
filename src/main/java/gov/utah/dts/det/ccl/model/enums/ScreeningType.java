package gov.utah.dts.det.ccl.model.enums;

public enum ScreeningType {

	INITIAL('I', "Initial"),
	RENEWAL('R', "Renewal");
	
	private final char character;
	private final String displayName;
	
	private ScreeningType(char character, String displayName) {
		this.character = character;
		this.displayName = displayName;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public static ScreeningType valueOf(char character) {
		for (ScreeningType type : ScreeningType.values()) {
			if (type.character == character) {
				return type;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid screening type");
	}
}