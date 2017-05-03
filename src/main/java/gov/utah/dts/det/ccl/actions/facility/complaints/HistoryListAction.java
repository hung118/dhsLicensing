package gov.utah.dts.det.ccl.actions.facility.complaints;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.ccl.sort.enums.ComplaintSortBy;
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
	
	private ComplaintService complaintService;
	
	private CclListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new CclListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ComplaintSortBy.values())));
		lstCtrl.setSortBy(ComplaintSortBy.getDefaultSortBy().name());
		lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
	}
	
	public String execute() {
		lstCtrl.setResults(complaintService.getComplaintsForFacility(getFacility().getId(), lstCtrl.getRange(),
				ComplaintSortBy.valueOf(lstCtrl.getSortBy()), true));
		
		return SUCCESS;
	}
	
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}