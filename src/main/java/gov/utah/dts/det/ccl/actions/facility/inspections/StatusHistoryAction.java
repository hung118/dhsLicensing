package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.ccl.model.Inspection;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "status_history.jsp")
})
public class StatusHistoryAction extends BaseInspectionAction {

	public String execute() {
		return SUCCESS;
	}
	
	public Inspection getInspection() {
		return super.getInspection();
	}
}