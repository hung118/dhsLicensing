package gov.utah.dts.det.ccl.model.enums;

public enum NameUsage {

	NAME_REFUSED('R', "Anonymous"),
	NON_CONFIDENTIAL('N', "Name may be used"),
	CONFIDENTIAL_INDEFINITELY('I', "Confidential Indefinitely"),
	CONFIDENTIAL_UNTIL_END('E', "Confidential until the end of the investigation");
	
	private final char character;
	private final String label;
	
	private NameUsage(char character, String label) {
		this.character = character;
		this.label = label;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static NameUsage valueOf(char character) {
		for (NameUsage ct : NameUsage.values()) {
			if (ct.character == character) {
				return ct;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid name usage");
	}
}