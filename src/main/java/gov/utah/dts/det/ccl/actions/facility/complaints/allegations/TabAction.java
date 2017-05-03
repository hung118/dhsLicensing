package gov.utah.dts.det.ccl.actions.facility.complaints.allegations;

import gov.utah.dts.det.ccl.actions.facility.complaints.BaseComplaintAction;
import gov.utah.dts.det.ccl.model.Complaint;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "allegations_subtab.jsp")
})
public class TabAction extends BaseComplaintAction {

	public String execute() {
		return SUCCESS;
	}
	
	public Complaint getComplaint() {
		return super.getComplaint();
	}
}