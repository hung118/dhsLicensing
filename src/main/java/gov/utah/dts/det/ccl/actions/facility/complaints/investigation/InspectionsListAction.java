package gov.utah.dts.det.ccl.actions.facility.complaints.investigation;

import gov.utah.dts.det.ccl.actions.facility.complaints.BaseComplaintAction;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "inspections_list.jsp")
})
public class InspectionsListAction extends BaseComplaintAction {

	public String execute() {
		return SUCCESS;
	}
}