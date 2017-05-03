package gov.utah.dts.det.ccl.actions.facility.actionlognotes;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "actionlognotes_tab.jsp")
})
public class TabAction extends BaseFacilityAction {

	public String execute() {
		return SUCCESS;
	}
}