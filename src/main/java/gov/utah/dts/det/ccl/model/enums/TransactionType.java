package gov.utah.dts.det.ccl.model.enums;

public enum TransactionType {

	PAYMENT('P'),
	REDUCTION('R');
	
	private final char character;
	
	private TransactionType(char character) {
		this.character = character;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public static TransactionType valueOf(char character) {
		for (TransactionType outcome : TransactionType.values()) {
			if (outcome.character == character) {
				return outcome;
			}
		}
		throw new IllegalArgumentException(character + " is not a valid transaction type");
	}
}