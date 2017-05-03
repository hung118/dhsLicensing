package gov.utah.dts.det.ccl.actions.trackingrecordscreening.dpsfbi;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningDpsFbi;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMain;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningDpsFbiService;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningMainService;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
    @Result(name = "redirect-edit", location = "edit-trsdpsfbi", type = "redirectAction", params = { "screeningId",
        "${screeningId}" }), @Result(name = "input", location = "dpsfbi_form.jsp") })
public class DpsFbiTabAction extends BaseTrackingRecordScreeningAction implements Preparable {
  private final String REDIRECT_EDIT = "redirect-edit";

  private TrackingRecordScreeningDpsFbi trackingRecordScreeningDpsFbi;
  private TrackingRecordScreeningDpsFbiService trackingRecordScreeningDpsFbiService;
  private TrackingRecordScreeningMain trackingRecordScreeningMain;
  private TrackingRecordScreeningMainService trackingRecordScreeningMainService;
  private List<PickListValue> billingTypes;

  @SkipValidation
  @Action(value = "edit-trsdpsfbi")
  public String doEdit() {
    if (screeningId != null) {
      trackingRecordScreeningDpsFbi = getTrackingRecordScreening().getTrsDpsFbi();
    }
    if (trackingRecordScreeningDpsFbi == null) {
      trackingRecordScreeningDpsFbi = new TrackingRecordScreeningDpsFbi();
      trackingRecordScreeningDpsFbi.setId(screeningId);
    }

    return INPUT;
  }

  public void prepareDoSave() {
    if (screeningId != null) {
      trackingRecordScreeningDpsFbi = getTrackingRecordScreening().getTrsDpsFbi();
      trackingRecordScreeningMain = getTrackingRecordScreening().getTrsMain();
    }
  }

  @Action(value = "save-trsDpsFbi")
  @Validations(conversionErrorFields = {
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.firstFbiRequestDate", message = "First FBI Request date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.firstFbiRejectedDate", message = "First FBI Request rejected date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.secondFbiRequestDate", message = "Second FBI Request date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.secondFbiRejectedDate", message = "Second FBI Request rejected date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.fbiNameCheckDate", message = "FBI Name Check date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.receivedFromFbiDate", message = "Received from FBI date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.livescanDate", message = "Livescan date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.toDpsForVerificationDate", message = "To DPS for Verification date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.dpsVerifiedDate", message = "DPS Verified date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.searchFee", message = "Search fee must be currency."),
      @ConversionErrorFieldValidator(fieldName = "trackingRecordScreeningDpsFbi.scanFee", message = "scanFee fee must be currency.") })
  public String doSave() {
    if (trackingRecordScreeningDpsFbi.getId() == null) {
      trackingRecordScreeningDpsFbi.setId(screeningId);
    }
    trackingRecordScreeningDpsFbiService.save(trackingRecordScreeningDpsFbi);

    // Redmine #25831. Update the Main tab ADAM/FBI Cleared date to the Received From FBI date
    if (trackingRecordScreeningMain == null) {
      trackingRecordScreeningMain = new TrackingRecordScreeningMain();
    }
    if (trackingRecordScreeningMain.getId() == null) {
      trackingRecordScreeningMain.setId(screeningId);
    }
    trackingRecordScreeningMain.setAdamFbiClearedDate(trackingRecordScreeningDpsFbi.getReceivedFromFbiDate());
    trackingRecordScreeningMainService.save(trackingRecordScreeningMain);
    // End Redmine #25831

    return REDIRECT_EDIT;
  }

  public void validate() {
    if (trackingRecordScreeningDpsFbi.getSecondFbiRequestDate() != null
        && trackingRecordScreeningDpsFbi.getFirstRejectedDate() == null) {
      addFieldError("trackingRecordScreeningDpsFbi.firstRejectedDate",
          "The First FBI rejected date is required in order to enter Second FBI request date.");
    }
    if (trackingRecordScreeningDpsFbi.getSecondRejectedDate() != null
        && trackingRecordScreeningDpsFbi.getFirstRejectedDate() == null) {
      addFieldError("trackingRecordScreeningDpsFbi.firstRejectedDate",
          "The First FBI rejected date is required in order to enter Second FBI rejected date.");
    }
    if (trackingRecordScreeningDpsFbi.getFirstFbiRequestDate() != null
        && trackingRecordScreeningDpsFbi.getFirstRejectedDate() != null
        && trackingRecordScreeningDpsFbi.getSecondFbiRequestDate() == null) {
      addFieldError("trackingRecordScreeningDpsFbi.secondFbiRequestDate", "Second FBI request date is required.");
    }
    // Validate Date ranges
    if (trackingRecordScreeningDpsFbi.getFirstFbiRequestDate() != null
        && trackingRecordScreeningDpsFbi.getFirstRejectedDate() != null
        && !trackingRecordScreeningDpsFbi.getFirstRejectedDate().after(trackingRecordScreeningDpsFbi.getFirstFbiRequestDate())) {
      addFieldError("trackingRecordScreeningDpsFbi.firstRejectedDate",
          "First rejected date must be after first FBI request date.");
    }
    if (trackingRecordScreeningDpsFbi.getFirstRejectedDate() != null
        && trackingRecordScreeningDpsFbi.getSecondFbiRequestDate() != null
        && !(trackingRecordScreeningDpsFbi.getSecondFbiRequestDate().after(trackingRecordScreeningDpsFbi.getFirstRejectedDate())           
        		|| trackingRecordScreeningDpsFbi.getSecondFbiRequestDate().equals(trackingRecordScreeningDpsFbi.getFirstRejectedDate()))) {
      addFieldError("trackingRecordScreeningDpsFbi.secondFbiRequestDate",
          "Second FBI request date must be equal or after first rejected date.");
    }
    if (trackingRecordScreeningDpsFbi.getSecondFbiRequestDate() != null
        && trackingRecordScreeningDpsFbi.getSecondRejectedDate() != null
        && !trackingRecordScreeningDpsFbi.getSecondRejectedDate().after(trackingRecordScreeningDpsFbi.getSecondFbiRequestDate())) {
      addFieldError("trackingRecordScreeningDpsFbi.firstRejectedDate",
          "Second rejected date must be after second FBI request date.");
    }
  }

  public TrackingRecordScreeningDpsFbi getTrackingRecordScreeningDpsFbi() {
    return trackingRecordScreeningDpsFbi;
  }

  public void setTrackingRecordScreeningDpsFbi(TrackingRecordScreeningDpsFbi trackingRecordScreeningDpsFbi) {
    this.trackingRecordScreeningDpsFbi = trackingRecordScreeningDpsFbi;
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

  public List<PickListValue> getBillingTypes() {
    if (billingTypes == null) {
      billingTypes = pickListService.getValuesForPickList("DPS/FBI Billing Codes", true);
    }
    return billingTypes;
  }

}
