package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum TrackingRecordScreeningRequestSortBy implements SortBy {
	
	RECEIVED_DATE("Received Date", "t.receivedDate", SortDirection.DESCENDING),
	FROM_DATE("From Date", "t.fromDate", SortDirection.DESCENDING),
	TO_DATE("To Date", "t.toDate", SortDirection.DESCENDING),
	COUNTRY("Country", "t.country", SortDirection.ASCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private TrackingRecordScreeningRequestSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static TrackingRecordScreeningRequestSortBy getDefaultSortBy() {
		return RECEIVED_DATE;
	}
}