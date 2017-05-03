package gov.utah.dts.det.ccl.actions.trackingrecordscreening.cbscomm;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningConvictLtrService;
import gov.utah.dts.det.ccl.sort.enums.TrackingRecordScreeningConvictLtrSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "conviction_letters_list.jsp")
})
public class ConvictionLettersListAction extends BaseTrackingRecordScreeningAction implements Preparable {
	
	private TrackingRecordScreeningConvictLtrService letterService;
	private CclListControls lstCtrl = new CclListControls();
	private Long letterId;

	public void prepareDoList() {
		//set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(TrackingRecordScreeningConvictLtrSortBy.values())));
		lstCtrl.setSortBy(TrackingRecordScreeningConvictLtrSortBy.getDefaultSortBy().name());
	}
	
	@Action(value="list-conviction-letters")
	public String doList() {
		if (getScreeningId() != null) {
			lstCtrl.setResults(letterService.getConvictionLettersForScreening(getScreeningId(), TrackingRecordScreeningConvictLtrSortBy.valueOf(lstCtrl.getSortBy())));
		}
		return SUCCESS;
	}

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public CclListControls getLstCtrl() {
		return lstCtrl;
	}

	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	public void setTrackingRecordScreeningConvictLtrService(TrackingRecordScreeningConvictLtrService letterService) {
		this.letterService = letterService;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
	}
}
