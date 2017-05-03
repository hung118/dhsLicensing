package gov.utah.dts.det.ccl.actions.facility.information.details;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "details_subtab.jsp")
})
public class TabAction extends BaseFacilityAction {

	public String execute() {
		return SUCCESS;
	}
	
	public Facility getFacility() {
		return super.getFacility();
	}
}