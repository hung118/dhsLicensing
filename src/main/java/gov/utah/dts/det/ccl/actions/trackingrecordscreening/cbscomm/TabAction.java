package gov.utah.dts.det.ccl.actions.trackingrecordscreening.cbscomm;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "cbscomm_tab.jsp")
})
public class TabAction extends BaseTrackingRecordScreeningAction {
	
	public String doExecute() {
		return SUCCESS;
	}
}
