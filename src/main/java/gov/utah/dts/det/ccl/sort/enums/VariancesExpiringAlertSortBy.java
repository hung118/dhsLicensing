package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum VariancesExpiringAlertSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fac.name", SortDirection.ASCENDING),
	FACILITY_TYPE("Facility Type", "fac.licenseType", SortDirection.ASCENDING),
	RULE_NUMBER("Rule #", "vev.ruleNumber", SortDirection.ASCENDING),
	VARIANCE_START_DATE("Variance Start Date", "vev.startDate", SortDirection.DESCENDING),
	VARIANCE_END_DATE("Variance End Date", "vev.endDate", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private VariancesExpiringAlertSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static VariancesExpiringAlertSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}