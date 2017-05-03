package gov.utah.dts.det.ccl.actions.facility.complaints;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.query.ListControls;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "progress_list.jsp")
})
public class ProgressListAction extends BaseFacilityAction {
	
	private ComplaintService complaintService;
	
	private ListControls lstCtrl = new ListControls();
	
	public String execute() {
		lstCtrl.setResults(complaintService.getComplaintsInProgress(getFacility().getId()));
		
		return SUCCESS;
	}
	
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}