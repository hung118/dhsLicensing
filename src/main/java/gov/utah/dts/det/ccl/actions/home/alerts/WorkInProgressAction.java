package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.sort.enums.WorkInProgressAlertSortBy;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONArray;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/work_in_progress_section.jsp")
})
public class WorkInProgressAction extends BaseAlertAction implements Preparable {

	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(WorkInProgressAlertSortBy.values())));
		lstCtrl.setSortBy(WorkInProgressAlertSortBy.getDefaultSortBy().name());
	}
	
	@Override
	public String execute() throws Exception {
		lstCtrl.setResults(facilityService.getWorkInProgress(getUser().getPerson().getId(), WorkInProgressAlertSortBy.valueOf(lstCtrl.getSortBy())));
		return SUCCESS;
	}
	
	public Iterator<String> getInspectionTypeIterator(JSONArray jsonArray) {
		return new InspectionTypesIterator(jsonArray);
	}
}