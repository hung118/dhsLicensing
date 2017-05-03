package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum VarianceAlertSortBy implements SortBy {
	
	REQUEST_DATE("Request Date", "v.requestDate", SortDirection.ASCENDING),
	FACILITY("Facility", "v.facilityName", SortDirection.ASCENDING),
	RULE_NUMBER("Rule Number", "v.ruleNumber", SortDirection.ASCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private VarianceAlertSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static VarianceAlertSortBy getDefaultSortBy() {
		return REQUEST_DATE;
	}
}