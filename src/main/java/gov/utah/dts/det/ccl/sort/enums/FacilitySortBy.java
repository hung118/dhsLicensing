package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

import java.util.ArrayList;
import java.util.List;

public enum FacilitySortBy implements SortBy {

	FACILITY_NAME("Facility Name", "fsv.facilityNameUpper", SortDirection.ASCENDING, true, true),
	PRIMARY_OWNER("Primary Owner's Name", "fsv.ownerName", SortDirection.ASCENDING, true, false),
	DIRECTOR("Director's Name", "fsv.directorName", SortDirection.ASCENDING, true, false),
	CITY("City", "fsv.locationAddress.city", SortDirection.ASCENDING, true, true),
	COUNTY("County", "fsv.locationAddress.county", SortDirection.ASCENDING, true, true),
	ZIP_CODE("Zip Code", "fsv.locationAddress.zipCode", SortDirection.ASCENDING, true, true),
	FACILITY_TYPE("Facility Type", "fsv.status asc, fsv.licenseType asc, fsv.licenseSubtype asc, fsv.facilityType asc", SortDirection.NONE, true, true),
	INITIAL_REGULATION_DATE("Initial Regulation Date", "fsv.initialRegulationDate", SortDirection.DESCENDING, true, false),
	EXPIRATION_DATE("Expiration Date", "fsv.licenseExpirationDate", SortDirection.DESCENDING, true, false),
	SLOTS("Total Slots", "fsv.totalSlots", SortDirection.ASCENDING, true, true),
	SLOTS_AGE_TWO("Total Slots < Age 2", "fsv.slotsAgeTwo", SortDirection.ASCENDING, true, true),
	FACILITY_ID_NO("Facility Id Number", "fsv.facilityIdNumber", SortDirection.ASCENDING, true, false);
	
	private static List<FacilitySortBy> publicSortBys;
	private static List<FacilitySortBy> partnerSortBys;
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	private final boolean openToPartners;
	private final boolean openToPublic;
	
	private FacilitySortBy(String label, String orderByString, SortDirection defaultSortDirection, boolean openToPartners,
			boolean openToPublic) {
		this.label = label;
		this.orderByString = orderByString;
		this.defaultSortDirection = defaultSortDirection;
		this.openToPartners = openToPartners;
		this.openToPublic = openToPublic;
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
	
	public boolean isOpenToPartners() {
		return openToPartners;
	}
	
	public boolean isOpenToPublic() {
		return openToPublic;
	}
	
	public static FacilitySortBy getDefaultSortBy() {
		return FACILITY_NAME;
	}
	
	public static List<FacilitySortBy> getPublicSortBys() {
		if (publicSortBys == null) {
			publicSortBys = new ArrayList<FacilitySortBy>();
			for (FacilitySortBy sortBy : FacilitySortBy.values()) {
				if (sortBy.openToPublic) {
					publicSortBys.add(sortBy);
				}
			}
		}
		return publicSortBys;
	}
	
	public static List<FacilitySortBy> getPartnerSortBys() {
		if (partnerSortBys == null) {
			partnerSortBys = new ArrayList<FacilitySortBy>();
			for (FacilitySortBy sortBy : FacilitySortBy.values()) {
				if (sortBy.openToPartners) {
					partnerSortBys.add(sortBy);
				}
			}
		}
		return partnerSortBys;
	}
}