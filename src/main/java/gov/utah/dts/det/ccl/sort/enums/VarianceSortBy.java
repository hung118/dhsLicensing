package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum VarianceSortBy implements SortBy {
	
	REQUEST_DATE("Request Date", "v.requestDate", SortDirection.DESCENDING),
	OUTCOME("Outcome", "v.outcome", SortDirection.ASCENDING),
	RULE_NUMBER("Rule Number", "v.rule.ruleView.ruleNumber", SortDirection.ASCENDING),
	START_DATE("Start Date", "v.startDate", SortDirection.DESCENDING),
	END_DATE("End Date", "v.endDate", SortDirection.DESCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private VarianceSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static VarianceSortBy getDefaultSortBy() {
		return REQUEST_DATE;
	}
}