package gov.utah.dts.det.ccl.model.enums;

public enum CondSanc {

	CONDITIONAL("Conditional", 'C'),
	SANCTION("Sanction", 'S');
	
	private final String displayName;
	private final char character;
	
	private CondSanc(String displayName, char character) {
		this.displayName = displayName;
		this.character = character;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public static CondSanc valueOf(char character) {
		for (CondSanc condSanc : CondSanc.values()) {
			if (condSanc.character == character) {
				return condSanc;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid condSanc");
	}
}