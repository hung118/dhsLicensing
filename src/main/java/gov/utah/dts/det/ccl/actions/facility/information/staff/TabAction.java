package gov.utah.dts.det.ccl.actions.facility.information.staff;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "staff_subtab.jsp")
})
public class TabAction extends BaseFacilityAction {

	public String execute() {
		return SUCCESS;
	}
}