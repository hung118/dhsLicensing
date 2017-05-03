package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum InspectionSortBy implements SortBy {

	INSPECTION_DATE("Inspection Date", "iv.inspectionDate", SortDirection.DESCENDING, true),
	INSPECTION_TYPE("Primary Inspection Type", "iv.primaryInspectionType", SortDirection.ASCENDING, true),
	FOLLOW_UP_NEEDED("Follow-up Needed", "iv.followUpNeeded", SortDirection.DESCENDING, false);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	private final boolean basicSort;
	
	private InspectionSortBy(String label, String orderByString, SortDirection defaultSortDirection, boolean basicSort) {
		this.label = label;
		this.orderByString = orderByString;
		this.defaultSortDirection = defaultSortDirection;
		this.basicSort = basicSort;
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
	
	public boolean isBasicSort() {
		return basicSort;
	}
	
	public static InspectionSortBy getDefaultSortBy() {
		return INSPECTION_DATE;
	}
}