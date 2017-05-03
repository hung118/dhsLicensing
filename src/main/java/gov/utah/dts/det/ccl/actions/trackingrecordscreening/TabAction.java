package gov.utah.dts.det.ccl.actions.trackingrecordscreening;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "trackingrecord_tab.jsp") })
public class TabAction extends BaseTrackingRecordScreeningAction {

  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }

}
