package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum FollowUpInspectionsNeededAlertSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fac.name", SortDirection.ASCENDING),
	FACILITY_TYPE("Facility Type", "fac.licenseType", SortDirection.ASCENDING),
	CITY("City", "fac.locationAddress.city", SortDirection.ASCENDING),
	//COUNTY("County", "fac.address.county", SortDirection.ASCENDING),
	ZIP_CODE("Zip Code", "fac.locationAddress.zipCode", SortDirection.ASCENDING),
	INSPECTION_TYPE("Inspection Type", "ins.inspectionTypesJson", SortDirection.ASCENDING),
	INSPECTION_DATE("Inspection Date", "ins.inspectionDate", SortDirection.DESCENDING),
	EXPIRATION_DATE("Expiration Date", "fac.licenseExpirationDate", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private FollowUpInspectionsNeededAlertSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static FollowUpInspectionsNeededAlertSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}