package gov.utah.dts.det.ccl.actions.trackingrecordscreening.requests;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningRequestService;
import gov.utah.dts.det.ccl.sort.enums.TrackingRecordScreeningRequestSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "requests_list.jsp")
})
public class RequestsListAction extends BaseTrackingRecordScreeningAction implements Preparable {
	
	private TrackingRecordScreeningRequestService trackingRecordScreeningRequestService;
	private CclListControls lstCtrl = new CclListControls();

	public void prepareDoList() {
		//set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(TrackingRecordScreeningRequestSortBy.values())));
		lstCtrl.setSortBy(TrackingRecordScreeningRequestSortBy.getDefaultSortBy().name());
	}
	
	@Action(value="list-requests")
	public String doList() {
		if (getScreeningId() != null) {
			lstCtrl.setResults(trackingRecordScreeningRequestService.getRequestsForScreening(getScreeningId(), TrackingRecordScreeningRequestSortBy.valueOf(lstCtrl.getSortBy())));
		}
		return SUCCESS;
	}

	public CclListControls getLstCtrl() {
		return lstCtrl;
	}

	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	public void setTrackingRecordScreeningRequestService(TrackingRecordScreeningRequestService trackingRecordScreeningRequestService) {
		this.trackingRecordScreeningRequestService = trackingRecordScreeningRequestService;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
	}
}
