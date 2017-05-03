package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ScreenedPeopleSortBy implements SortBy {
	
	FIRST_NAME("First Name", "sp.person.name.firstName", SortDirection.ASCENDING),
	LAST_NAME("Last Name", "sp.person.name.lastName", SortDirection.ASCENDING),
	BIRTHDAY("Birthday", "sp.person.birthday", SortDirection.DESCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ScreenedPeopleSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
		this.label = label;
		this.orderByString = orderByString;
		this.defaultSortDirection = defaultSortDirection;
	}
	
	@Override
	public String getKey() {
		return name();
	}
	
	@Override
	public String getLabel() {
		return label;
	}
	
	@Override
	public String getOrderByString() {
		return orderByString;
	}
	
	@Override
	public SortDirection getDefaultSortDirection() {
		return defaultSortDirection;
	}
	
	public static ScreenedPeopleSortBy getDefaultSortBy() {
		return FIRST_NAME;
	}
}