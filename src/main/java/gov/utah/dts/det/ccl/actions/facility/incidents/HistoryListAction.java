package gov.utah.dts.det.ccl.actions.facility.incidents;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.IncidentService;
import gov.utah.dts.det.ccl.sort.enums.IncidentSortBy;
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

	private IncidentService incidentService;
	
	private CclListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new CclListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(IncidentSortBy.values())));
		lstCtrl.setSortBy(IncidentSortBy.getDefaultSortBy().name());
		lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
	}
	
	public String execute() {
		lstCtrl.setResults(incidentService.getIncidentsForFacility(getFacility().getId(), lstCtrl.getRange(), 
				IncidentSortBy.valueOf(lstCtrl.getSortBy()), true));
		
		return SUCCESS;
	}
	
	public void setIncidentService(IncidentService incidentService) {
		this.incidentService = incidentService;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}