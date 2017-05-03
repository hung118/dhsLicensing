package gov.utah.dts.det.ccl.actions.trackingrecordscreening.miscomm;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * Tab action for background screening MIS committee tab.
 * 
 * @author Hnguyen
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "miscomm_tab.jsp")
})
public class TabAction extends BaseFacilityAction {
	
	public String execute() {
		return SUCCESS;
	}
}