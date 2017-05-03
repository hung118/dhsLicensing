package gov.utah.dts.det.ccl.actions.trackingrecordscreening.search;

import gov.utah.dts.det.ccl.service.TRSSearchCriteria;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningService;
import gov.utah.dts.det.ccl.sort.enums.TrsSearchSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/trackingrecordscreening/search")
@Results({ @Result(name = "input", location = "trsSearch", type = "tiles"),
	@Result(name = "success", location = "trsSearchResults", type = "tiles") })
public class TrackingRecordScreeningSearchAction extends ActionSupport implements Preparable, SessionAware {
	public static final String TRS_CRITERIA_KEY = "trackingrecordscreening.search.criteria";

	private TrackingRecordScreeningService trackingRecordScreeningService;

	private String firstName;
	private String lastName;
	private Long facilityId;
	private Date birthday;
	private String ssnLastFour;

	private ListControls lstCtrl;
	private Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void prepare() throws Exception {
		// set up the sort component
		lstCtrl = new ListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(TrsSearchSortBy.getAllTRSSortBys()));
		lstCtrl.setSortBy(TrsSearchSortBy.LAST_NAME.name());
		lstCtrl.setResultsPerPage(25);
	}

	@SkipValidation
	@Action(value = "search-form")
	public String doSearchForm() {
		session.remove(TRS_CRITERIA_KEY);
		return INPUT;
	}

	@Action(value = "search-results")
	public String doSearch() {
		session.remove(TRS_CRITERIA_KEY);
		TRSSearchCriteria criteria = new TRSSearchCriteria(firstName, lastName, birthday, ssnLastFour);
		lstCtrl.setResults(trackingRecordScreeningService.searchTrackingRecordScreenings(criteria,
				TrsSearchSortBy.valueOf(lstCtrl.getSortBy()), lstCtrl.getPage() - 1, lstCtrl.getResultsPerPage()));
		
		if (lstCtrl.getResults() != null) {
			lstCtrl.setNumOfResults(trackingRecordScreeningService.searchTRSCount(criteria));
		} else {
			lstCtrl.setNumOfResults(0);
		}
		
		session.put(TRS_CRITERIA_KEY, criteria);
		return SUCCESS;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
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

	public ListControls getLstCtrl() {
		return lstCtrl;
	}

	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	public void setTrackingRecordScreeningService(TrackingRecordScreeningService trackingRecordScreeningService) {
		this.trackingRecordScreeningService = trackingRecordScreeningService;
	}

	@Override
	public void validate() {
		super.validate();
		if (StringUtils.isBlank(ssnLastFour) && StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName) && birthday == null) {
			addFieldError("firstName", "At least one search field is required");
		} else if (StringUtils.isNotBlank(ssnLastFour)) {
			if (ssnLastFour.length() == 4) {
				try {
					Integer.parseInt(ssnLastFour);
				} catch (NumberFormatException e) {
					addFieldError("ssnLastFour", "SSN last four must be numeric");
				}
			} else {
				addFieldError("ssnLastFour", "SSN last four must be exactly 4 digits");
			}
		}
	}

}
