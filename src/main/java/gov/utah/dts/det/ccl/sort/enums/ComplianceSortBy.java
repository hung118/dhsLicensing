package gov.utah.dts.det.ccl.sort.enums;

import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.query.SortDirection;

public enum ComplianceSortBy implements SortBy {

	INSPECTION_TYPE("Inspection Type", "ncv.primaryInspectionType", SortDirection.ASCENDING),
	INSPECTION_DATE("Inspection Date", "ncv.inspectionDate", SortDirection.DESCENDING),
	CITED_FINDINGS("# of Cited Findings", "ncv.citedFindings", SortDirection.DESCENDING),
	REPEAT_CITED_FINDINGS("# of Repeat Cited Findings", "ncv.repeatCitedFindings", SortDirection.DESCENDING),
	CITED_POINTS("Total Cited NC Points", "ncv.citedNCPoints", SortDirection.DESCENDING),
	TA_FINDINGS("# of TA Findings", "ncv.taFindings", SortDirection.DESCENDING),
	TA_POINTS("Total TA NC Points", "ncv.taNCPoints", SortDirection.DESCENDING),
	TOTAL_POINTS("Total Cited & TA NC Points", "ncv.totalNCPoints", SortDirection.DESCENDING),
	CMP_AMOUNT("Total CMP Assessed", "ncv.cmpAmount", SortDirection.DESCENDING);
	
	private final String label;
	private final String orderByString;
	private final SortDirection defaultSortDirection;
	
	private ComplianceSortBy(String label, String orderByString, SortDirection defaultSortDirection) {
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
	
	public static ComplianceSortBy getDefaultSortBy() {
		return INSPECTION_DATE;
	}
}