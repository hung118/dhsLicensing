package gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLtr15;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningLtr15Service;
import gov.utah.dts.det.ccl.sort.enums.TrackingRecordScreeningLettersSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-list", location = "list-ltr15", type = "redirectAction", params = {"screeningId", "${screeningId}"}),
	@Result(name = "success", location = "ltr15_list.jsp"),
	@Result(name = "input", location="ltr15_form.jsp")
})
public class TrackingRecordScreeningLtr15Action extends BaseTrackingRecordScreeningAction implements Preparable {
	
	private final String REDIRECT_LIST = "redirect-list";

	private TrackingRecordScreeningLtr15Service trackingRecordScreeningLtr15Service;
	private TrackingRecordScreeningLtr15 screeningLtr15;
	private Long ltr15Id;
	private PickListValue reason;
	private List<PickListValue> reasons;
	private CclListControls lstCtrl = new CclListControls();

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void prepareDoList() {
		//set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(TrackingRecordScreeningLettersSortBy.values())));
		lstCtrl.setSortBy(TrackingRecordScreeningLettersSortBy.getDefaultSortBy().name());
	}

	@Action(value="list-ltr15")
	public String doList() {
		if (getScreeningId() != null) {
			lstCtrl.setResults(trackingRecordScreeningLtr15Service.get15DayLettersForScreening(getScreeningId(), TrackingRecordScreeningLettersSortBy.valueOf(lstCtrl.getSortBy())));
		}
		return SUCCESS;
	}

	@Action(value="edit-ltr15")
	public String doEditLtr15() {
		if (ltr15Id != null) {
			screeningLtr15 = trackingRecordScreeningLtr15Service.load(ltr15Id);
		}
		if (screeningLtr15 == null) {
			screeningLtr15 = new TrackingRecordScreeningLtr15();
		}
		reason = screeningLtr15.getReason();
		return INPUT;
	}

	public void prepareDoSaveLtr15() {
		if (ltr15Id != null) {
			screeningLtr15 = trackingRecordScreeningLtr15Service.load(ltr15Id);
		}
		if (screeningLtr15 == null) {
			screeningLtr15 = new TrackingRecordScreeningLtr15();
		}
	}
	
	@Action(value="save-ltr15")
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "screeningLtr15.issuedDate", message = "Issued date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "screeningLtr15.issuedDate", message = "Issued date is required.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "reason", message = "Reason is required.", shortCircuit = true)
		}
	)
	public String doSaveLtr15() {
		// Calculate the due date
		Calendar cal = Calendar.getInstance();
		cal.setTime(screeningLtr15.getIssuedDate());
		cal.add(Calendar.DATE, 15);
		screeningLtr15.setDueDate(cal.getTime());
		screeningLtr15.setReason(reason);
		screeningLtr15.setTrackingRecordScreening(getTrackingRecordScreening());
		screeningLtr15 = trackingRecordScreeningLtr15Service.save(screeningLtr15);
		trackingRecordScreeningLtr15Service.evict(screeningLtr15);
		return REDIRECT_LIST;
	}

	@Action(value="delete-ltr15")
	public String doDeleteLtr15() {
		if (ltr15Id != null) {
			screeningLtr15 = trackingRecordScreeningLtr15Service.load(ltr15Id);
			if (screeningLtr15 != null) {
				trackingRecordScreeningLtr15Service.delete(ltr15Id);
				trackingRecordScreeningLtr15Service.evict(screeningLtr15);
			}
		}
		return REDIRECT_LIST;
	}

	public CclListControls getLstCtrl() {
		return lstCtrl;
	}

	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	public Long getLtr15Id() {
		return ltr15Id;
	}

	public void setLtr15Id(Long id) {
		this.ltr15Id = id;
	}

	public void setTrackingRecordScreeningLtr15Service(TrackingRecordScreeningLtr15Service service) {
		this.trackingRecordScreeningLtr15Service = service;
	}

	public PickListValue getReason() {
		return reason;
	}

	public void setReason(PickListValue reason) {
		this.reason = reason;
	}

	public List<PickListValue> getReasons() {
		if (reasons == null) {
			reasons = pickListService.getValuesForPickList("15-Day Letter Reasons", true);
		}
		return reasons;
	}

	public TrackingRecordScreeningLtr15 getScreeningLtr15() {
		return screeningLtr15;
	}


	public void setScreeningLtr15(TrackingRecordScreeningLtr15 screeningLtr15) {
		this.screeningLtr15 = screeningLtr15;
	}

}
