package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum TrackingRecordScreeningActivitySortBy implements SortBy {
	
	ACTIVITY_DATE("Activity Date", "t.activityDate", SortDirection.DESCENDING),
	ACTIVITY_STATE("Description", "t.shortDescription", SortDirection.ASCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private TrackingRecordScreeningActivitySortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static TrackingRecordScreeningActivitySortBy getDefaultSortBy() {
		return ACTIVITY_DATE;
	}
}