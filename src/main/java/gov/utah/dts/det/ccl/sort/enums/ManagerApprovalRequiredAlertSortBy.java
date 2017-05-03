package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ManagerApprovalRequiredAlertSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fac.name", SortDirection.ASCENDING),
	FACILITY_TYPE("Facility Type", "fac.licenseType", SortDirection.ASCENDING),
	TYPE("Type", "marv.primaryKey.objectType", SortDirection.ASCENDING),
	DATE("Date", "marv.objectDate", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ManagerApprovalRequiredAlertSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static ManagerApprovalRequiredAlertSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}