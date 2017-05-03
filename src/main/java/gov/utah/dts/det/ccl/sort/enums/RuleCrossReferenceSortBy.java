package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum RuleCrossReferenceSortBy implements SortBy {

	OLD_RULE("Old Rule", "rcr.oldRule.ruleNumber", SortDirection.ASCENDING),
	NEW_RULE("New Rule", "rcr.newRule.ruleNumber", SortDirection.ASCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private RuleCrossReferenceSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static RuleCrossReferenceSortBy getDefaultSortBy() {
		return OLD_RULE;
	}
}