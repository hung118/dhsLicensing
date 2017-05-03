package gov.utah.dts.det.ccl.actions.trackingrecordscreening.cbscomm;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCbsComm;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningCbsCommService;
import gov.utah.dts.det.ccl.view.YesNoList;

import java.util.Arrays;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

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
    @Result(name = "redirect-edit", location = "edit-cbscomm", type = "redirectAction", params = { "screeningId", "${screeningId}" }), 
    @Result(name = "success", location = "cbscomm_form.jsp"),
    @Result(name = "input", location = "cbscomm_form.jsp") })
public class CbsCommAction extends BaseTrackingRecordScreeningAction implements Preparable {

  private final String REDIRECT_EDIT = "redirect-edit";

  private TrackingRecordScreeningCbsCommService trackingRecordScreeningCbsCommService;
  private TrackingRecordScreeningCbsComm cbsComm;
  private List<PickListValue> oahDecisions;

  @Override
  public void prepare() throws Exception {
    // TODO Auto-generated method stub
  }

  @Action(value = "edit-cbscomm")
  public String doEdit() {
    cbsComm = getTrackingRecordScreening().getCbsComm();
    if (cbsComm == null) {
      cbsComm = new TrackingRecordScreeningCbsComm();
    }

    return INPUT;
  }

  public void prepareDoSave() {
    cbsComm = getTrackingRecordScreening().getCbsComm();
    if (cbsComm == null) {
      cbsComm = new TrackingRecordScreeningCbsComm();
    }
  }

  @Action(value = "save-cbscomm")
  @Validations(conversionErrorFields = {
      @ConversionErrorFieldValidator(fieldName = "cbsComm.oahRequestDate", message = "OAH request date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
      @ConversionErrorFieldValidator(fieldName = "cbsComm.decisionDate", message = "CBS committee decision date is not a valid date. (MM/DD/YYYY)", shortCircuit = true) })
  public String doSave() {
    if (cbsComm.getId() == null) {
      cbsComm.setId(getTrackingRecordScreening().getId());
    }

    trackingRecordScreeningCbsCommService.save(cbsComm);

    return REDIRECT_EDIT;
  }

  public TrackingRecordScreeningCbsComm getCbsComm() {
    return cbsComm;
  }

  public void setCbsComm(TrackingRecordScreeningCbsComm cbsComm) {
    this.cbsComm = cbsComm;
  }

  public List<PickListValue> getOahDecisions() {
    if (oahDecisions == null) {
      oahDecisions = pickListService.getValuesForPickList("OAH Decisions", true);
    }
    return oahDecisions;
  }

  public void setTrackingRecordScreeningCbsCommService(TrackingRecordScreeningCbsCommService service) {
    this.trackingRecordScreeningCbsCommService = service;
  }

  public List<YesNoList> getYesNoList() {
    return Arrays.asList(YesNoList.values());
  }
}
