package gov.utah.dts.det.ccl.view;

public enum YesNoChoice {

	YES("Yes", true),
	NO("No", false);
	
	private final String displayName;
	private final boolean value;
	
	private YesNoChoice(String displayName, boolean value) {
		this.displayName = displayName;
		this.value = value;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getValue() {
		return Boolean.toString(value);
	}
	
	public boolean getBooleanValue() {
		return value;
	}
	
	public static YesNoChoice valueOfBoolean(boolean choice) {
		if (choice) {
			return YES;
		}
		return NO;
	}
}