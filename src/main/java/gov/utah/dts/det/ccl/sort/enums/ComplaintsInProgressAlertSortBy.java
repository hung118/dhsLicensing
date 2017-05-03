package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ComplaintsInProgressAlertSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fac.name", SortDirection.ASCENDING),
	FACILITY_TYPE("Facility Type", "fac.licenseType", SortDirection.ASCENDING),
	CITY("City", "fac.locationAddress.city", SortDirection.ASCENDING),
	COUNTY("County", "fac.locationAddress.county", SortDirection.ASCENDING),
	ZIP_CODE("Zip Code", "fac.locationAddress.zipCode", SortDirection.ASCENDING),
	LAST_ACTION_DATE("Last Action Date", "cipv.lastStateChangeDate", SortDirection.DESCENDING),
	DATE_RECEIVED("Date Received", "cipv.dateReceived", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ComplaintsInProgressAlertSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static ComplaintsInProgressAlertSortBy getDefaultSortBy() {
		return DATE_RECEIVED;
	}
}
