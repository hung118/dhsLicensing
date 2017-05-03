package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum CaregiverSortBy implements SortBy {

	FIRST_NAME("First Name", "p.name.firstName", SortDirection.ASCENDING),
	LAST_NAME("Last Name", "p.name.lastName", SortDirection.ASCENDING),
	MAIDEN_NAME("Maiden Name", "p.maidenName", SortDirection.ASCENDING),
	CITY("City", "p.address.city", SortDirection.ASCENDING),
	COUNTY("County", "p.address.county", SortDirection.ASCENDING),
	BIRTHDATE("Birthdate", "p.birthday", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private CaregiverSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static CaregiverSortBy getDefaultSortBy() {
		return FIRST_NAME;
	}
}