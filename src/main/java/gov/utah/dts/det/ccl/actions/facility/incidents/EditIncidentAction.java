package gov.utah.dts.det.ccl.actions.facility.incidents;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "incident_base.jsp")
})
public class EditIncidentAction extends BaseIncidentAction {
	
	public String execute() {
		return SUCCESS;
	}
}