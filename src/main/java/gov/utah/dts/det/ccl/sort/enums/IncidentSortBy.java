package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum IncidentSortBy implements SortBy {

	DATE("Date", "iv.date", SortDirection.DESCENDING),
	CHILD_NAME("Child's Name", "upper(iv.childName)", SortDirection.ASCENDING),
	CHILD_AGE("Child's Age", "iv.childAge", SortDirection.ASCENDING),
	BODY_PART("Body Part Injured", "iv.bodyPartsInjured", SortDirection.ASCENDING),
	DEATH("Death?", "iv.death", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private IncidentSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static IncidentSortBy getDefaultSortBy() {
		return DATE;
	}
}