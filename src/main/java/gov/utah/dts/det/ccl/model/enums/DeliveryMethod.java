package gov.utah.dts.det.ccl.model.enums;

public enum DeliveryMethod {

	DIRECT_CONTACT('D', "Direct Contact"),
	VOICEMAIL('V', "Voicemail"),
	EMAIL('E', "Email"),
	WRITTEN_STATEMENT('S', "Written Statement"),
	ONLINE('O', "Online");
	
	private final char character;
	private final String label;
	
	private DeliveryMethod(char character, String label) {
		this.character = character;
		this.label = label;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static DeliveryMethod valueOf(char character) {
		for (DeliveryMethod im : DeliveryMethod.values()) {
			if (im.character == character) {
				return im;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid delivery method");
	}
}