package gov.utah.dts.det.ccl.model.enums;

public enum DownloadTimes {

	DAILY('D', "Daily"),
	QUARTERLY('Q', "Quarterly"),
	YEARLY('Y', "Yearly");
	
	private final char character;
	private final String label;
	
	private DownloadTimes(char character, String label) {
		this.character = character;
		this.label = label;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static DownloadTimes valueOf(char character) {
		for (DownloadTimes im : DownloadTimes.values()) {
			if (im.character == character) {
				return im;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid delivery method");
	}
}