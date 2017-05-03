package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

import java.util.ArrayList;
import java.util.List;

public enum BackgroundScreeningSortBy implements SortBy {

	LAST_NAME("Last Name", "upper(sv.lastName), upper(sv.firstName)", SortDirection.ASCENDING, true, true),
	FIRST_NAME("First Name", "upper(sv.firstName), upper(sv.lastName)", SortDirection.ASCENDING, true, true),
	SSN("Social Security #", "sv.ssn", SortDirection.ASCENDING, true, false),
	FACILITY_NAME("Facility Name", "upper(sv.facilityName)", SortDirection.ASCENDING, true, false),
	PM_RECEIVED_DATE("PM Date Received in Region", "sv.pmReceivedDate", SortDirection.DESCENDING, false, true),
	BCU_RECEIVED_DATE("Date Received in BCU", "sv.bcuReceivedDate", SortDirection.DESCENDING, true, true),
	SUBMITTED_TO_FBI_DATE("Date Submitted to FBI", "sv.submittedToFbiDate", SortDirection.DESCENDING, false, true),
	CHECKS_COMPLETED_DATE("Date All Checks Completed", "sv.allChecksCompletedDate", SortDirection.DESCENDING, true, true),
	STATUS("Status", "sv.status", SortDirection.ASCENDING, true, true);
	
	private static List<BackgroundScreeningSortBy> allFacilitiesSortBys;
	private static List<BackgroundScreeningSortBy> singleFacilitySortBys;
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	private final boolean allFacilitiesSort; //sorter is used on the search all bcu records search
	private final boolean singleFacilitySort; //sorter is used inside the background screening tab on an individual facility
	
	private BackgroundScreeningSortBy(String label, String orderByString, SortDirection defaultSortDirection, boolean allFacilitiesSort,
			boolean singleFacilitySort) {
		this.label = label;
		this.orderByString = orderByString;
		this.defaultSortDirection = defaultSortDirection;
		this.allFacilitiesSort = allFacilitiesSort;
		this.singleFacilitySort = singleFacilitySort;
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
	
	public static BackgroundScreeningSortBy getDefaultSortBy() {
		return LAST_NAME;
	}
	
	public static List<BackgroundScreeningSortBy> getAllFacilitiesSortBys() {
		if (allFacilitiesSortBys == null) {
			allFacilitiesSortBys = new ArrayList<BackgroundScreeningSortBy>();
			for (BackgroundScreeningSortBy sortBy : BackgroundScreeningSortBy.values()) {
				if (sortBy.allFacilitiesSort) {
					allFacilitiesSortBys.add(sortBy);
				}
			}
		}
		return allFacilitiesSortBys;
	}
	
	public static List<BackgroundScreeningSortBy> getSingleFacilitySortBys() {
		if (singleFacilitySortBys == null) {
			singleFacilitySortBys = new ArrayList<BackgroundScreeningSortBy>();
			for (BackgroundScreeningSortBy sortBy : BackgroundScreeningSortBy.values()) {
				if (sortBy.singleFacilitySort) {
					singleFacilitySortBys.add(sortBy);
				}
			}
		}
		return singleFacilitySortBys;
	}
}