package gov.utah.dts.det.ccl.model.enums;

public enum RuleCategory {

	/* CKS: the field has be re-purposed, these no longer apply
	NONE('N', "None"),
	CBS_LIS('C', "CBS/LIS"),
	RATIO('R', "Ratio"),
	SUPERVISION('S', "Supervision"),
	LEGAL('L', "Arrest, Charge, or Conviction"),
	*/

	// These are for DHS - the above ones will no longer be used
	PENDING('P', "Pending"),
	ACTIVE('A', "Active"),
	INACTIVE('I', "Inactive");
	
	private final char character;
	private final String label;
	
	private RuleCategory(char character, String label) {
		this.character = character;
		this.label = label;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static RuleCategory valueOf(char character) {
		for (RuleCategory ce : RuleCategory.values()) {
			if (ce.character == character) {
				return ce;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid rule category");
	}
}