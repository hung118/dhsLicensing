package gov.utah.dts.det.ccl.actions.caseloadmanagement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

@JsonSerialize(as = SortBy.class)
public enum CaseloadSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fcv.name", SortDirection.NONE),
	CITY("City", "fcv.locationAddress.city, fcv.name", SortDirection.NONE),
	ZIP_CODE("Zip Code", "fcv.locationAddress.zipCode, fcv.name", SortDirection.NONE),
	LICENSE_TYPE("License Type", "fcv.licenseType, fcv.name", SortDirection.NONE);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private CaseloadSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static CaseloadSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}