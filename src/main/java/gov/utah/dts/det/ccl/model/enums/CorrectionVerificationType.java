package gov.utah.dts.det.ccl.model.enums;

public enum CorrectionVerificationType {

	VERIFIED('V', "Correction Verified"),
	UNABLE_TO_VERIFY('U', "Unable to verify correction"),
	PROVIDER_CLOSED('C', "Not verified, provider closed"),
	VERIFICATION_PENDING('P', "Verification Pending"),
	RESCINDED('R', "Finding Rescinded"),
	UNDER_APPEAL('A', "Under Appeal");
	
	private final char character;
	private final String label;
	
	private CorrectionVerificationType(char character, String label) {
		this.character = character;
		this.label = label;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static CorrectionVerificationType valueOf(char character) {
		for (CorrectionVerificationType type : CorrectionVerificationType.values()) {
			if (type.character == character) {
				return type;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid finding correction verification type.");
	}
}