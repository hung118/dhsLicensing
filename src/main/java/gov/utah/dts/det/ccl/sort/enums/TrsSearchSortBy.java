package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

import java.util.ArrayList;
import java.util.List;

public enum TrsSearchSortBy implements SortBy {

	LAST_NAME("Last Name", "tsv.lastName, tsv.firstName, tsv.personId, tsv.approvalDate desc, tsv.facilityName", null, true), 
	FIRST_NAME("First Name", "tsv.firstName, tsv.lastName, tsv.personId, tsv.approvalDate desc", null, true), 
	FACILITY_NAME("Facility Name", "tsv.facilityName, tsv.lastName, tsv.firstName, tsv.personId, tsv.approvalDate desc", null, true), 
	BIRTHDATE("Birthdate", "tsv.birthday asc, tsv.lastName, tsv.firstName, tsv.personId, tsv.approvalDate desc, tsv.facilityName", null, true), 
	APPROVAL_DATE("Approval Date", "tsv.approvalDate desc, tsv.lastName, tsv.firstName, tsv.personId, tsv.facilityName", null, true), 
	ALIASES("Alias", "tsv.alias, tsv.lastName, tsv.firstName, tsv.personId, tsv.approvalDate desc, tsv.facilityName", null, true), 
	PERSON_IDENTIFIER("Person Identifier", "tsv.personIdentifier, tsv.lastName, tsv.firstName, tsv.personId, tsv.approvalDate desc, tsv.facilityName", null, true);

	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	private final boolean allTRSSort;

	private static List<TrsSearchSortBy> allTRSSortBys;

	private TrsSearchSortBy(String label, String orderByString, SortDirection defaultSortDirection, boolean allTRSSort) {
		this.label = label;
		this.orderByString = orderByString;
		this.defaultSortDirection = defaultSortDirection;
		this.allTRSSort = allTRSSort;
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

	public static TrsSearchSortBy getDefaultSortBy() {
		return APPROVAL_DATE;
	}

	public static List<TrsSearchSortBy> getAllTRSSortBys() {
		if (allTRSSortBys == null) {
			allTRSSortBys = new ArrayList<TrsSearchSortBy>();
			for (TrsSearchSortBy sortBy : TrsSearchSortBy.values()) {
				if (sortBy.allTRSSort) {
					allTRSSortBys.add(sortBy);
				}
			}
		}
		return allTRSSortBys;
	}

}
