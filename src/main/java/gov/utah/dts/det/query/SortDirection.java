package gov.utah.dts.det.query;

public enum SortDirection {

	ASCENDING("asc"),
	DESCENDING("desc"),
	NONE("");
	
	private final String sortDirectionString;
	
	private SortDirection(String sortDirectionString) {
		this.sortDirectionString = sortDirectionString;
	}
	
	public String getSortDirectionString() {
		return sortDirectionString;
	}
}