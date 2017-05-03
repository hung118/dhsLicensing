package gov.utah.dts.det.ccl.actions.facility.complianceandhistory;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.enums.FacilityEventType;
import gov.utah.dts.det.ccl.sort.enums.HistorySortBy;
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
public class HistoryAction extends BaseFacilityAction implements Preparable {
	
	private CclListControls lstCtrl = new CclListControls();
	private List<FacilityEventType> eventTypes;
	private String clear;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(HistorySortBy.values())));
		lstCtrl.setSortBy(HistorySortBy.getDefaultSortBy().name());
		lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
	}
	
	public String execute() {
		if (clear != null) {
			eventTypes = new ArrayList<FacilityEventType>();
		} else {
			lstCtrl.setResults(facilityService.getFacilityHistory(getFacility().getId(), lstCtrl.getRange(), HistorySortBy.valueOf(lstCtrl.getSortBy()),
					eventTypes));
		}
		
		return SUCCESS;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public List<FacilityEventType> getEventTypes() {
		return eventTypes;
	}
	
	public void setEventTypes(List<FacilityEventType> eventTypes) {
		this.eventTypes = eventTypes;
	}
	
	public void setClear(String clear) {
		this.clear = clear;
	}
	
	public List<FacilityEventType> getFacilityEventTypes() {
		//remove CMP Note
		List<FacilityEventType> list = new ArrayList<FacilityEventType>();
		for (FacilityEventType e : Arrays.asList(FacilityEventType.values())) {
			if (!"CMP Note".equals(e.getDisplayName())) {
				list.add(e);
			}
		}
		
		return list;
	}
}