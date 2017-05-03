package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum TrackingRecordScreeningOscarSortBy implements SortBy {
	
	OSCAR_DATE("OSCAR Date", "t.oscarDate", SortDirection.DESCENDING),
	OSCAR_TYPE("Type", "t.oscarType.value", SortDirection.ASCENDING),
	OSCAR_STATE("State", "t.state", SortDirection.ASCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private TrackingRecordScreeningOscarSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static TrackingRecordScreeningOscarSortBy getDefaultSortBy() {
		return OSCAR_DATE;
	}
}