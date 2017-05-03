package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum CmpSortBy implements SortBy {
	
	TRANSACTION_DATE("Received Date", "cmp.date", SortDirection.DESCENDING),
	FACILITY_NAME("Facility Name", "cmp.facility.name", SortDirection.ASCENDING),
	CHECK_DATE("Check Date", "cmp.checkDate", SortDirection.DESCENDING),
	CHECK_OWNER("Check Owner", "cmp.checkOwner", SortDirection.ASCENDING),
	AMOUNT("Amount", "cmp.amount", SortDirection.ASCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private CmpSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static CmpSortBy getDefaultSortBy() {
		return TRANSACTION_DATE;
	}
}