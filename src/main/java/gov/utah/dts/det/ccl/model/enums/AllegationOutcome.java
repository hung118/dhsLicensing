package gov.utah.dts.det.ccl.model.enums;

public enum AllegationOutcome {

	SUBSTANTIATED('S', "Substantiated"),
	NOT_SUBSTANTIATED('N', "Not Substantiated");
	
	private final char character;
	private final String displayName;
	
	private AllegationOutcome(char character, String displayName) {
		this.character = character;
		this.displayName = displayName;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public static AllegationOutcome valueOf(char character) {
		for (AllegationOutcome outcome : AllegationOutcome.values()) {
			if (outcome.character == character) {
				return outcome;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid allegation outcome");
	}
}