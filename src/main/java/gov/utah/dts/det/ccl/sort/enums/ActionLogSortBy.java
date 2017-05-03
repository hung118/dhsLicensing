package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ActionLogSortBy implements SortBy {

	ACTION_DATE("Action Date", "al.actionDate", SortDirection.DESCENDING),
	STAFF_MEMBER("Staff Member", "upper(al.modifiedBy.name.firstName) asc, upper(al.modifiedBy.name.lastName)", SortDirection.ASCENDING),
	ACTION("Action", "upper(al.actionType.value)", SortDirection.ASCENDING),
	ENTRY_DATE("Entry Date", "al.creationDate", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ActionLogSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static ActionLogSortBy getDefaultSortBy() {
		return ACTION_DATE;
	}
}