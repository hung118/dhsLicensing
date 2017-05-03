package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum SeriousNoncomplianceAlertSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "snv.facilityName", SortDirection.ASCENDING),
	LICENSE_TYPE("License Type", "snv.licenseType", SortDirection.ASCENDING),
	TOTAL_POINTS("Total Points", "snv.totalPoints", SortDirection.ASCENDING),
	TRIGGER_DATE("Date Trigger Reached", "snv.triggerDate", SortDirection.ASCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private SeriousNoncomplianceAlertSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static SeriousNoncomplianceAlertSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}