package gov.utah.dts.det.ccl.model.enums;

public enum AppealOutcome {

	UPHELD('U', "Upheld"),
	RESCINDED('R', "Recinded");
	
	private final char character;
	private final String displayName;
	
	private AppealOutcome(char character, String displayName) {
		this.character = character;
		this.displayName = displayName;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public static AppealOutcome valueOf(char character) {
		for (AppealOutcome outcome : AppealOutcome.values()) {
			if (outcome.character == character) {
				return outcome;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid appeal outcome");
	}
}