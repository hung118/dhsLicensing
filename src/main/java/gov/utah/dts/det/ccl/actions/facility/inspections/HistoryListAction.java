package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.InspectionService;
import gov.utah.dts.det.ccl.sort.enums.InspectionSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "history_list.jsp")
})
public class HistoryListAction extends BaseFacilityAction implements Preparable {
	
	private InspectionService inspectionService;
	
	private CclListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new CclListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(InspectionSortBy.values())));
		lstCtrl.setSortBy(InspectionSortBy.getDefaultSortBy().name());
		lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
	}
	
	public String execute() {
		lstCtrl.setResults(inspectionService.getInspectionsForFacility(getFacility().getId(), lstCtrl.getRange(),
				InspectionSortBy.valueOf(lstCtrl.getSortBy()), true));
		
		return SUCCESS;
	}
	
	public void setInspectionService(InspectionService inspectionService) {
		this.inspectionService = inspectionService;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}