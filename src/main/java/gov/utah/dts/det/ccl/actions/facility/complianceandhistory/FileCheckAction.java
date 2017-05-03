package gov.utah.dts.det.ccl.actions.facility.complianceandhistory;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.ccl.view.ListRange;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONException;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "file_check.jsp")
})
public class FileCheckAction extends BaseFacilityAction implements Preparable {

	private CclListControls lstCtrl = new CclListControls();
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new CclListControls();
		lstCtrl.setSortBy("none");
		lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
	}
	
	@Action(value = "file-check")
	public String doList() throws JSONException {
		lstCtrl.setResults(facilityService.getFileCheck(getFacility().getId(), lstCtrl.getRange()));
			
		return SUCCESS;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}