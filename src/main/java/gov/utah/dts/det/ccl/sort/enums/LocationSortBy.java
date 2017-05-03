package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum LocationSortBy implements SortBy {

	ZIP_CODE("Zip Code", "loc.zipCode", SortDirection.ASCENDING),
	CITY("City", "loc.city", SortDirection.ASCENDING),
	COUNTY("County", "loc.county", SortDirection.ASCENDING),
	STATE("State", "loc.state", SortDirection.ASCENDING),
	REGION("Region", "region.name", SortDirection.ASCENDING),
	RR_AGENCY("R & R Agency", "rrAgency.value", SortDirection.ASCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private LocationSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static LocationSortBy getDefaultSortBy() {
		return ZIP_CODE;
	}
}