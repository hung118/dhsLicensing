package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum TrackingRecordScreeningLetterSortBy implements SortBy {
	
	LETTER_DATE("Letter Date", "t.letterDate", SortDirection.DESCENDING),
	CREATION_DATE("Creation Date", "t.creationDate", SortDirection.DESCENDING),
	LETTER_TYPE("Letter Type", "t.letterType", SortDirection.ASCENDING);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private TrackingRecordScreeningLetterSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static TrackingRecordScreeningLetterSortBy getDefaultSortBy() {
		return CREATION_DATE;
	}
}