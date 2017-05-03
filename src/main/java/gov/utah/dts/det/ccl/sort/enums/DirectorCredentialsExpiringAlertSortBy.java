package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum DirectorCredentialsExpiringAlertSortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fac.name", SortDirection.ASCENDING),
	FACILITY_TYPE("Facility Type", "fac.licenseType", SortDirection.ASCENDING),
	DIRECTORS_NAME("Director's Name", "dcev.director.name.firstName asc, dcev.director.name.lastName asc", SortDirection.NONE),
	CREDENTIAL_TYPE("Credential Type", "dcev.credentialType", SortDirection.ASCENDING),
	CREDENTIAL_EXPIRATION_DATE("Credential Expiration Date", "dcev.credentialExpirationDate", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private DirectorCredentialsExpiringAlertSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static DirectorCredentialsExpiringAlertSortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
}