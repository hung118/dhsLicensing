package gov.utah.dts.det.ccl.actions.trackingrecordscreening;

import gov.utah.dts.det.ccl.actions.trackingrecordscreening.search.TrackingRecordScreeningSearchAction;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.service.TRSSearchCriteria;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "trackingRecordScreeningCreate", type = "tiles"),
	@Result(name = "success", location = "edit-tracking-record-screening", type = "redirectAction", params = { "namespace",
			"/trackingrecordscreening", "screeningId", "${trackingRecordScreening.id}", "facilityId", "${facilityId}" })

})
public class TrackingRecordScreeningAction extends BaseTrackingRecordScreeningAction implements SessionAware, Preparable {
	private Map<String, Object> session;
	private FacilityService facilityService;
	private boolean fromEditForm;
	private Long personId;

	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void prepareDoSave() {
		if (screeningId != null) {
			trackingRecordScreening = trackingRecordScreeningService.load(screeningId);
		}
	}

	@Validations(
			visitorFields = { 
					@VisitorFieldValidator(fieldName = "trackingRecordScreening", message = "&zwnj;") 
			}, 
			requiredFields = {
					@RequiredFieldValidator(fieldName = "trackingRecordScreening.birthday", message = "Birthdate is required"),
					@RequiredFieldValidator(fieldName = "trackingRecordScreening.facility.name", message = "Facility is required") }, 
			requiredStrings = {
					@RequiredStringValidator(fieldName = "trackingRecordScreening.ssnLastFour", message = "SSN last four is required"),
					@RequiredStringValidator(fieldName = "trackingRecordScreening.firstName", message = "First name is required"),
					@RequiredStringValidator(fieldName = "trackingRecordScreening.lastName", message = "Last name is required") 
			}
	)
	@Action(value = "saveTrackingRecordScreening")
	public String doSave() {
		if (trackingRecordScreening.getPerson() == null || trackingRecordScreening.getPerson().getId() == null) {
			Person person = new Person();
			person.setFirstName(trackingRecordScreening.getFirstName());
			person.setLastName(trackingRecordScreening.getLastName());
			person.setBirthday(trackingRecordScreening.getBirthday());
			personService.savePerson(person);
			trackingRecordScreening.setPerson(person);
		}
		super.trackingRecordScreening = trackingRecordScreeningService.save(trackingRecordScreening);
		return SUCCESS;
	}

	@SkipValidation
	@Action(value = "trackingRecordScreeningCreate")
	public String doCreate() {
		trackingRecordScreening = new TrackingRecordScreening();
		TRSSearchCriteria criteria = (TRSSearchCriteria) session.get(TrackingRecordScreeningSearchAction.TRS_CRITERIA_KEY);
		// personId will only be null if the create was issued from the New Person button on the Search Results screen
		if (personId == null) {
			if (criteria != null && !fromEditForm) {
				trackingRecordScreening.setFirstName(criteria.getFirstName());
				trackingRecordScreening.setLastName(criteria.getLastName());
				trackingRecordScreening.setBirthday(criteria.getBirthday());
				trackingRecordScreening.setSsnLastFour(criteria.getSsnLastFour());
			}
		} else {
			// This code will by reached if the user clicked on a New link on the Search Results screen
			Person person = personService.getPerson(personId);
			if (person != null) {
				trackingRecordScreening.setPerson(person);
				trackingRecordScreening.setFirstName(person.getFirstName());
				trackingRecordScreening.setLastName(person.getLastName());
				trackingRecordScreening.setBirthday(person.getBirthday());
			}
		}
		return INPUT;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public FacilityService getFacilityService() {
		return facilityService;
	}

	@Override
	public void validate() {

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public boolean isFromEditForm() {
		return fromEditForm;
	}

	public void setFromEditForm(boolean fromEditForm) {
		this.fromEditForm = fromEditForm;
	}

}
