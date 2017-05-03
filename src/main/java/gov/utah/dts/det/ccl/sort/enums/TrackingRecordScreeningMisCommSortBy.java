package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum TrackingRecordScreeningMisCommSortBy implements SortBy {
	
	MIS_COMM_DATE("MIS Committee Date", "t.misCommDate", SortDirection.DESCENDING),
	MIS_COMM_TYPE("MIS Committee Type", "t.misCommType.value", SortDirection.ASCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private TrackingRecordScreeningMisCommSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static TrackingRecordScreeningMisCommSortBy getDefaultSortBy() {
		return MIS_COMM_DATE;
	}
}