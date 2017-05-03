package gov.utah.dts.det.ccl.actions.facility.screenings;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningService;
import gov.utah.dts.det.ccl.sort.enums.TrackingRecordScreeningSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "screenings_list.jsp") })
public class ScreeningsListAction extends BaseFacilityAction implements Preparable {

  private TrackingRecordScreeningService trackingRecordScreeningService;

  private CclListControls lstCtrl = new CclListControls();

  @Override
  public void prepare() throws Exception {
    // set up the list controls
    lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(TrackingRecordScreeningSortBy.values())));
    lstCtrl.setSortBy(TrackingRecordScreeningSortBy.getDefaultSortBy().name());
    lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
    lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
  }

  public String execute() {
    lstCtrl.setResults(trackingRecordScreeningService.getScreeningsForFacility(getFacilityId(), lstCtrl.getRange(),
        TrackingRecordScreeningSortBy.valueOf(lstCtrl.getSortBy())));

    return SUCCESS;

  }

  public CclListControls getLstCtrl() {
    return lstCtrl;
  }

  public void setLstCtrl(CclListControls lstCtrl) {
    this.lstCtrl = lstCtrl;
  }

  public void setTrackingRecordScreeningService(TrackingRecordScreeningService trackingRecordScreeningService) {
    this.trackingRecordScreeningService = trackingRecordScreeningService;
  }
}