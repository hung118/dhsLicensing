package gov.utah.dts.det.ccl.actions.facility.screenings;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.dao.SearchException;
import gov.utah.dts.det.ccl.model.view.TRSSearchView;
import gov.utah.dts.det.ccl.service.TRSSearchCriteria;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningService;
import gov.utah.dts.det.ccl.sort.enums.TrackingRecordScreeningSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({ @Result(name = "input", location = "search_form.jsp"), @Result(name = "success", location = "search_results.jsp") })
public class SearchAction extends BaseFacilityAction implements Preparable, SessionAware {

  public static final String CRITERIA_KEY = "facility.trs.search.criteria";

  private TrackingRecordScreeningService trackingRecordScreeningService;
  private String firstName;
  private String lastName;
  private Date birthday;
  private String ssnLastFour;

  private Long personId;

  private ListControls lstCtrl;

  private Map<String, Object> session;

  @Override
  public void prepare() throws Exception {
    // set up the sort component
    lstCtrl = new ListControls();
    lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(TrackingRecordScreeningSortBy.values())));
    lstCtrl.setSortBy(TrackingRecordScreeningSortBy.getDefaultSortBy().name());
    lstCtrl.setResultsPerPage(25);
  }

  @Override
  public void setSession(Map<String, Object> session) {
    this.session = session;
  }

  @Action(value = "search-form")
  public String doSearchForm() {
    session.remove(CRITERIA_KEY);
    return INPUT;
  }

  public String execute() {
    session.remove(CRITERIA_KEY);
    TRSSearchCriteria criteria = new TRSSearchCriteria(firstName, lastName, birthday, ssnLastFour);
    try {
      List<TRSSearchView> screenings = trackingRecordScreeningService.searchTrackingRecordScreenings(criteria,
          TrackingRecordScreeningSortBy.valueOf(lstCtrl.getSortBy()), lstCtrl.getPage() - 1, lstCtrl.getResultsPerPage());

      lstCtrl.setResults(screenings);
      lstCtrl.setNumOfResults(trackingRecordScreeningService.searchTRSCount(criteria));
    } catch (SearchException se) {
      addActionError(se.getMessage());

      return INPUT;
    }
    session.put(CRITERIA_KEY, criteria);

    return SUCCESS;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getSsnLastFour() {
    return ssnLastFour;
  }

  public void setSsnLastFour(String ssnLastFour) {
    this.ssnLastFour = ssnLastFour;
  }

  public Long getPersonId() {
    return personId;
  }

  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  public ListControls getLstCtrl() {
    return lstCtrl;
  }

  public void setLstCtrl(ListControls lstCtrl) {
    this.lstCtrl = lstCtrl;
  }

  public void setTrackingRecordScreeningService(TrackingRecordScreeningService trackingRecordScreeningService) {
    this.trackingRecordScreeningService = trackingRecordScreeningService;
  }
}