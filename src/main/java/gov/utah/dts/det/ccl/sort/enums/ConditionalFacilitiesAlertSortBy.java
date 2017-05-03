package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ConditionalFacilitiesAlertSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fac.name", SortDirection.ASCENDING),
	FACILITY_TYPE("Facility Type", "fac.licenseType", SortDirection.ASCENDING),
	CONDITIONAL_START_DATE("Conditional License Start Date", "cfv.startDate", SortDirection.DESCENDING),
	CONDITIONAL_END_DATE("Conditional License Expiration Date", "cfv.expirationDate", SortDirection.DESCENDING),
	LICENSING_SPECIALIST_NAME("Licensing Specialist Name", "fac.licensingSpecialist.name.firstName asc, fac.licensingSpecialist.name.lastName asc", SortDirection.NONE);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ConditionalFacilitiesAlertSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static ConditionalFacilitiesAlertSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}