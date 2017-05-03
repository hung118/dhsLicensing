package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ExemptionSortBy implements SortBy {

	EXEMPTION("Exemption", "exemption.value", SortDirection.ASCENDING),
	START_DATE("Start Date", "startDate", SortDirection.DESCENDING),
	EXPIRATION_DATE("Expiration Date", "expirationDate", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ExemptionSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static ExemptionSortBy getDefaultSortBy() {
		return START_DATE;
	}
}