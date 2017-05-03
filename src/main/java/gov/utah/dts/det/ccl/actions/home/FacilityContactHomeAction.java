package gov.utah.dts.det.ccl.actions.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import gov.utah.dts.det.ccl.actions.home.alerts.BaseAlertAction;
import gov.utah.dts.det.ccl.actions.home.alerts.InspectionTypesIterator;
import gov.utah.dts.det.ccl.sort.enums.ContactFacilitiesSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONArray;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/home/facility_contact_home.jsp")
})
public class FacilityContactHomeAction extends BaseAlertAction {

	public void prepare() throws Exception {
		lstCtrl = new ListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ContactFacilitiesSortBy.values())));
		lstCtrl.setSortBy(ContactFacilitiesSortBy.getDefaultSortBy().name());
	}

	@Override
	public String execute() throws Exception {
		lstCtrl.setResults(facilityService.getContactFacilities(getUser().getPerson().getId(), ContactFacilitiesSortBy.valueOf(lstCtrl.getSortBy())));
		return SUCCESS;
	}

	public Iterator<String> getInspectionTypeIterator(JSONArray jsonArray) {
		return new InspectionTypesIterator(jsonArray);
	}
}