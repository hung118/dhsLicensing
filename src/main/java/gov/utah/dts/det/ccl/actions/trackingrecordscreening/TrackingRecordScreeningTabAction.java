package gov.utah.dts.det.ccl.actions.trackingrecordscreening;

import gov.utah.dts.det.ccl.model.TrackingRecordScreening;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "trackingRecordScreeningEdit", type = "tiles") })
public class TrackingRecordScreeningTabAction extends BaseTrackingRecordScreeningAction {

  private String tab;
  private String act;
  private String ns;

  @Action(value = "open-tab")
  public String doTab() {
    return SUCCESS;
  }

  public String getTab() {
    return tab;
  }

  public void setTab(String tab) {
    this.tab = tab;
  }

  public String getAct() {
    return act;
  }

  public void setAct(String act) {
    this.act = act;
  }

  public String getNs() {
    return ns;
  }

  public void setNs(String ns) {
    this.ns = ns;
  }

  public TrackingRecordScreening getTrackingRecordScreening() {
    return super.getTrackingRecordScreening();
  }
}
