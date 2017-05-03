package gov.utah.dts.det.ccl.model.enums;

public enum ScreeningConvictionLetterType {

	APPROVAL('A',"Approval"),
	DENIAL('D',"Denial");
	
	private final char character;
	private final String label;
	
	private ScreeningConvictionLetterType(char character, String label) {
		this.character = character;
		this.label = label;
	}
	
	public char getCharacter() {
		return character;
	}

	public String getLabel() {
		return label;
	}

	public static ScreeningConvictionLetterType valueOf(char character) {
		for (ScreeningConvictionLetterType type : ScreeningConvictionLetterType.values()) {
			if (type.character == character) {
				return type;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid convictions letter type");
	}
}