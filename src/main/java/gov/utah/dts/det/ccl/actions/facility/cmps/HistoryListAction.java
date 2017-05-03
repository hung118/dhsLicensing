package gov.utah.dts.det.ccl.actions.facility.cmps;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.CmpService;
import gov.utah.dts.det.ccl.sort.enums.CmpSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "history_list.jsp")
})
public class HistoryListAction extends BaseFacilityAction implements Preparable {
	
	private CmpService cmpService;
		
	private CclListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new CclListControls();
		
		// remove Facility Name, don't need it here
		List<SortBy> temp = new ArrayList<SortBy>();
		for (SortBy s : Arrays.asList(CmpSortBy.values())) {
			if (!"Facility Name".equals(s.getLabel())) {
				temp.add(s);
			}
		}
		lstCtrl.setSortBys(temp);
		
		lstCtrl.setSortBy(CmpSortBy.getDefaultSortBy().name());
		lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
	}
	
	public String execute() {
		lstCtrl.setResults(cmpService.getCmpsForFacility(getFacility().getId(), lstCtrl.getRange(),
				CmpSortBy.valueOf(lstCtrl.getSortBy()), true));
		
		return SUCCESS;
	}
	
	public void setCmpService(CmpService cmpService) {
		this.cmpService = cmpService;
	}
		
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}