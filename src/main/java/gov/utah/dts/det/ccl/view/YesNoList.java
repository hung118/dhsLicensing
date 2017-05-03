package gov.utah.dts.det.ccl.view;

public enum YesNoList {
  YES("Yes", "Y"), NO("No", "N");

  private final String displayName;
  private final String value;

  private YesNoList(String displayName, String value) {
    this.displayName = displayName;
    this.value = value;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getValue() {
    return value;
  }

  public static YesNoList retYesNoList(String choice) {
    if ("N".equalsIgnoreCase(choice)) {
      return NO;
    }
    return YES;
  }

}
