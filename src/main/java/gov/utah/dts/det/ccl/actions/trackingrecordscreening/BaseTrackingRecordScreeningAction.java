package gov.utah.dts.det.ccl.actions.trackingrecordscreening;

import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.actions.CclAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.service.PersonService;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningService;
import gov.utah.dts.det.service.ApplicationService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
public class BaseTrackingRecordScreeningAction extends ActionSupport implements CclAction {
  protected TrackingRecordScreeningService trackingRecordScreeningService;
  protected PickListService pickListService;
  protected ApplicationService applicationService;
  protected PersonService personService;

  protected TrackingRecordScreening trackingRecordScreening;
  protected Long screeningId;
  protected Long facilityId;
  protected Long personId;

  public Long getFacilityId() {
    return facilityId;
  }

  public void setFacilityId(Long facilityId) {
    this.facilityId = facilityId;
  }

  public void setPersonService(PersonService personService) {
    this.personService = personService;
  }

  public void setApplicationService(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  public void setPickListService(PickListService pickListService) {
    this.pickListService = pickListService;
  }

  public Long getScreeningId() {
    return screeningId;
  }

  public void setScreeningId(Long screeningId) {
    this.screeningId = screeningId;
  }

  public TrackingRecordScreening getTrackingRecordScreening() {
    if (trackingRecordScreening == null) {
      if (screeningId != null) {
        trackingRecordScreening = trackingRecordScreeningService.load(screeningId);
      }
      if (trackingRecordScreening == null) {
        trackingRecordScreening = new TrackingRecordScreening();
      }
    }
    return trackingRecordScreening;
  }

  public void setTrackingRecordScreening(TrackingRecordScreening trackingRecordScreening) {
    this.trackingRecordScreening = trackingRecordScreening;
  }

  public void setTrackingRecordScreeningService(TrackingRecordScreeningService trackingRecordScreeningService) {
    this.trackingRecordScreeningService = trackingRecordScreeningService;
  }

  public Long getPersonId() {
    return personId;
  }

  public void setPersonId(Long personId) {
    this.personId = personId;
  }

}
