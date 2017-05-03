package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ComplaintSortBy implements SortBy {
	
	DATE_RECEIVED("Date Received", "cv.dateReceived", SortDirection.DESCENDING),
	DATE_FINALIZED("Date Finalized", "cv.dateFinalized", SortDirection.DESCENDING),
	TOTAL_INVEST_TIME("Total Invest Time", "cv.duration", SortDirection.DESCENDING),
	SUBSTANTIATED("Substantiated", "cv.substantiated", SortDirection.DESCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ComplaintSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static ComplaintSortBy getDefaultSortBy() {
		return DATE_RECEIVED;
	}
}