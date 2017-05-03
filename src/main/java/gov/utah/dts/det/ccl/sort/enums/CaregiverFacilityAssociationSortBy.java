package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum CaregiverFacilityAssociationSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "cf.facilityInfoView.name", SortDirection.ASCENDING),
	CITY("City", "cf.facilityInfoView.locationAddress.city", SortDirection.ASCENDING),
	COUNTY("County", "cf.facilityInfoView.locationAddress.county", SortDirection.ASCENDING),
	BEGIN_DATE("Begin Date", "cf.beginDate", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private CaregiverFacilityAssociationSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static CaregiverFacilityAssociationSortBy getDefaultSortBy() {
		return BEGIN_DATE;
	}
}