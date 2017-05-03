package gov.utah.dts.det.ccl.actions.trackingrecordscreening.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningActivity;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningActivityService;
import gov.utah.dts.det.ccl.sort.enums.TrackingRecordScreeningActivitySortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.query.SortBy;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class for background screening Activity tab.
 * 
 * @author Hnguyen
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "activity-list", type = "redirectAction", params = {"screeningId", "${screeningId}" }),
	@Result(name = "success", location = "activity_list.jsp"),
	@Result(name = "input", location = "activity_form.jsp") 
})
public class ActivityAction extends BaseTrackingRecordScreeningAction implements Preparable {

	private TrackingRecordScreeningActivityService trackingRecordScreeningActivityService;
	private CclListControls lstCtrl = new CclListControls();
	private TrackingRecordScreeningActivity activity;

	public void prepareDoList() {
		// set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(TrackingRecordScreeningActivitySortBy.values())));
		lstCtrl.setSortBy(TrackingRecordScreeningActivitySortBy.getDefaultSortBy().name());
	}

	@Action(value = "activity-list")
	public String doList() {
		if (getScreeningId() != null) {
			lstCtrl.setResults(trackingRecordScreeningActivityService.getActivityForScreening(getScreeningId(),
							TrackingRecordScreeningActivitySortBy.valueOf(lstCtrl.getSortBy())));
		}
		return SUCCESS;
	}

	@Action(value = "new-activity")
	public String doForm() {
		if (activity == null) {
			activity = new TrackingRecordScreeningActivity();
			activity.setActivityDate(new Date());
		}
		return INPUT;
	}
	
	@Action(value = "edit-activity")
	public String doEdit() {
		loadActivity();
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		if (activity.getId() != null) { // update
			loadActivity();
		} else {	// insert
			activity.setTrackingRecordScreening(getTrackingRecordScreening());
		}
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "activity.activityDate", message = "Activity Date is required.")
		}
	)
	@Action(value = "save-activity")
	public String doSave() {
		trackingRecordScreeningActivityService.save(activity);
		return REDIRECT_VIEW;
	}
	
	@Action(value = "delete-activity")
	public String doDelete() {
		trackingRecordScreeningActivityService.delete(activity.getId());
		return REDIRECT_VIEW;
	}

	public void setTrackingRecordScreeningActivityService(TrackingRecordScreeningActivityService trackingRecordScreeningActivityService) {
		this.trackingRecordScreeningActivityService = trackingRecordScreeningActivityService;
	}

	public CclListControls getLstCtrl() {
		return lstCtrl;
	}

	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	public TrackingRecordScreeningActivity getActivity() {
		return activity;
	}

	public void setActivity(TrackingRecordScreeningActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void prepare() throws Exception {

	}
	
	private void loadActivity(){
		if (activity != null && activity.getId() != null) {
			activity = trackingRecordScreeningActivityService.load(activity.getId());
		}
	}
		
}
