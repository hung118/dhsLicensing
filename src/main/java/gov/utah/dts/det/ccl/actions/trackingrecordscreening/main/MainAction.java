package gov.utah.dts.det.ccl.actions.trackingrecordscreening.main;

import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningDpsFbi;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMain;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningDpsFbiService;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningMainService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
    @Result(name = "redirect-edit", location = "edit-trsMain", type = "redirectAction", params = { "screeningId",
        "${screeningId}" }), @Result(name = "input", location = "main_form.jsp") })
public class MainAction extends BaseTrackingRecordScreeningAction implements Preparable {
  private final String REDIRECT_EDIT = "redirect-edit";

  private TrackingRecordScreeningDpsFbi trackingRecordScreeningDpsFbi;
  private TrackingRecordScreeningDpsFbiService trackingRecordScreeningDpsFbiService;
  private TrackingRecordScreeningMain trackingRecordScreeningMain;
  private TrackingRecordScreeningMainService trackingRecordScreeningMainService;
  private boolean viewOnly = false;
  
  @SkipValidation
  @Action(value = "edit-trsMain")
  public String doEdit() {
    if (screeningId != null) {
      trackingRecordScreeningMain = getTrackingRecordScreening().getTrsMain();
    }
    if (trackingRecordScreeningMain == null) {
      trackingRecordScreeningMain = new TrackingRecordScreeningMain();
    }
    return INPUT;
  }

  public void prepareDoSave() {
    if (screeningId != null) {
      trackingRecordScreeningMain = getTrackingRecordScreening().getTrsMain();
      trackingRecordScreeningDpsFbi = getTrackingRecordScreening().getTrsDpsFbi();
    }
  }

  @Validations(requiredFields = { @RequiredFieldValidator(type = ValidatorType.SIMPLE, fieldName = "trackingRecordScreeningMain.dateReceived", message = "Date received is required.") })
  @Action(value = "save-trsMain")
  public String doSave() {
    if (trackingRecordScreeningMain.getId() == null) {
      trackingRecordScreeningMain.setId(screeningId);
    }
    trackingRecordScreeningMainService.save(trackingRecordScreeningMain);

    if (trackingRecordScreeningMain.getAdamFbiClearedDate() != null) {
      if (trackingRecordScreeningDpsFbi == null) {
        trackingRecordScreeningDpsFbi = new TrackingRecordScreeningDpsFbi();
      }
      if (trackingRecordScreeningDpsFbi.getId() == null) {
        trackingRecordScreeningDpsFbi.setId(screeningId);
      }
      trackingRecordScreeningDpsFbi.setReceivedFromFbiDate(trackingRecordScreeningMain.getAdamFbiClearedDate());
      trackingRecordScreeningDpsFbiService.save(trackingRecordScreeningDpsFbi);
    }
    return REDIRECT_EDIT;
  }

  public TrackingRecordScreeningMain getTrackingRecordScreeningMain() {
    return trackingRecordScreeningMain;
  }

  public void setTrackingRecordScreeningMain(TrackingRecordScreeningMain trackingRecordScreeningMain) {
    this.trackingRecordScreeningMain = trackingRecordScreeningMain;
  }

  public void setTrackingRecordScreeningDpsFbiService(TrackingRecordScreeningDpsFbiService trackingRecordScreeningDpsFbiService) {
    this.trackingRecordScreeningDpsFbiService = trackingRecordScreeningDpsFbiService;
  }

  public void setTrackingRecordScreeningMainService(TrackingRecordScreeningMainService trackingRecordScreeningMainService) {
    this.trackingRecordScreeningMainService = trackingRecordScreeningMainService;
  }

  @Override
  public void prepare() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void validate() {
    super.validate();
    if (trackingRecordScreeningMain.getApprovalMailedDate() != null) {
      if (trackingRecordScreeningMain.getLisaClearedDate() == null || trackingRecordScreeningMain.getAmisClearedDate() == null
          || trackingRecordScreeningMain.getDateReceived() == null) {
        addFieldError("trackingRecordScreeningMain.approvalMailedDate",
            "Approval Date requires: LiSA, AMIS, and Date Received fields entered");
      }
    }
  }

public boolean isViewOnly() {
	if (SecurityUtil.hasAnyRole(RoleType.ROLE_ACCESS_PROFILE_VIEW)) {
		viewOnly = true;
	}
	return viewOnly;
}

public void setViewOnly(boolean viewOnly) {
	this.viewOnly = viewOnly;
}

}
