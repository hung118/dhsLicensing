package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum StatewideUnlicensedComplaintsSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fsv.facilityName", SortDirection.ASCENDING),
	PRIMARY_OWNER("Owner's Name", "fsv.ownerName", SortDirection.ASCENDING),
	DATE_RECEIVED("Date Received", "c.dateReceived", SortDirection.DESCENDING),
	ADDRESS("Address", "fsv.locationAddress.addressOne", SortDirection.ASCENDING),
	CITY("City", "fsv.locationAddress.city", SortDirection.ASCENDING),
	COUNTY("County", "fsv.locationAddress.county", SortDirection.ASCENDING),
	ZIP_CODE("Zip Code", "fsv.locationAddress.zipCode", SortDirection.ASCENDING),
	STATUS("Status", "fsv.status", SortDirection.ASCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private StatewideUnlicensedComplaintsSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static StatewideUnlicensedComplaintsSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}