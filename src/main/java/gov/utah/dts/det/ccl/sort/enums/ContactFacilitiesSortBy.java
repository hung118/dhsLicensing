package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ContactFacilitiesSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fcv.facilityName", SortDirection.ASCENDING),
	FACILITY_TYPE("Facility Type", "fcv.licenseType", SortDirection.ASCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ContactFacilitiesSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static ContactFacilitiesSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}