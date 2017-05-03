package gov.utah.dts.det.ccl.model.enums;

public enum SpecialistType {

	PRIMARY('P'),
	SECOND('S'),
	THIRD('T');
	
	private final char character;
	
	private SpecialistType(char character) {
		this.character = character;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public static SpecialistType valueOf(char character) {
		for (SpecialistType type : SpecialistType.values()) {
			if (type.character == character) {
				return type;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid specialist type");
	}
}