package gov.utah.dts.det.ccl.model.enums;

public enum VarianceOutcome {

	APPROVED('A', "Approved"),
	DENIED('D', "Denied"),
	NOT_NECESSARY('N', "Not Necessary");
	
	private final char character;
	private final String displayName;
	
	private VarianceOutcome(char character, String displayName) {
		this.character = character;
		this.displayName = displayName;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public static VarianceOutcome valueOf(char character) {
		for (VarianceOutcome outcome : VarianceOutcome.values()) {
			if (outcome.character == character) {
				return outcome;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid variance outcome");
	}
}