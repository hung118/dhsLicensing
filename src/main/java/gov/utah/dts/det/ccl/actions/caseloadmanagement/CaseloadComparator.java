package gov.utah.dts.det.ccl.actions.caseloadmanagement;

import gov.utah.dts.det.util.CompareUtils;

import java.util.Comparator;

public class CaseloadComparator implements Comparator<FacilityCaseloadView> {

	private CaseloadSortBy sortBy;
	
	public CaseloadComparator(CaseloadSortBy sortBy) {
		if (sortBy == null) {
			sortBy = CaseloadSortBy.getDefaultSortBy();
		} else {
			this.sortBy = sortBy;
		}
	}
	
	@Override
	public int compare(FacilityCaseloadView o1, FacilityCaseloadView o2) {
		if (sortBy == CaseloadSortBy.FACILITY_NAME) {
			return CompareUtils.nullSafeComparableCompare(o1.getName(), o2.getName(), false);
		} else if (sortBy == CaseloadSortBy.ZIP_CODE) {
			return CompareUtils.nullSafeComparableCompare(o1.getZipCode(), o2.getZipCode(), false);
		} else if (sortBy == CaseloadSortBy.LICENSE_TYPE) {
			return CompareUtils.nullSafeComparableCompare(o1.getType(), o2.getType(), false);
		} else {
			//default is sort by city
			return CompareUtils.nullSafeComparableCompare(o1.getCity(), o2.getCity(), false);
		}
	}
}