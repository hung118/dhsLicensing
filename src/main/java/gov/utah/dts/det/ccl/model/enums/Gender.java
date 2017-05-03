package gov.utah.dts.det.ccl.model.enums;

public enum Gender {

	FEMALE("Female", 'F'),
	MALE("Male", 'M'),
	UNKNOWN("Unknown", 'U');
	
	private final String displayName;
	private final char character;
	
	private Gender(String displayName, char character) {
		this.displayName = displayName;
		this.character = character;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public static Gender valueOf(char character) {
		for (Gender gender : Gender.values()) {
			if (gender.character == character) {
				return gender;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid gender");
	}
}