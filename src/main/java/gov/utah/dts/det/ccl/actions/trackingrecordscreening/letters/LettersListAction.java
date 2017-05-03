package gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningLetterService;
import gov.utah.dts.det.ccl.sort.enums.TrackingRecordScreeningLetterSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "letters_list.jsp")
})
public class LettersListAction extends BaseTrackingRecordScreeningAction implements Preparable {
	
	private TrackingRecordScreeningLetterService letterService;
	private CclListControls lstCtrl = new CclListControls();
	private Long letterId;

	public void prepareDoList() {
		//set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(TrackingRecordScreeningLetterSortBy.values())));
		lstCtrl.setSortBy(TrackingRecordScreeningLetterSortBy.getDefaultSortBy().name());
	}
	
	@Action(value="list-letters")
	public String doList() {
		if (getScreeningId() != null) {
			lstCtrl.setResults(letterService.getLettersForScreening(getScreeningId(), TrackingRecordScreeningLetterSortBy.valueOf(lstCtrl.getSortBy())));
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

	public void setTrackingRecordScreeningLetterService(TrackingRecordScreeningLetterService letterService) {
		this.letterService = letterService;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
	}
}
