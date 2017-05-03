package gov.utah.dts.det.ccl.actions.facility.information.staff;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.view.TrackingRecordScreeningApprovalsView;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.query.ListControls;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-edit", location = "edit-director", type = "redirectAction", params = {"facilityId", "${facilityId}", "director.id", "${director.id}"}),
	@Result(name = "redirect-edit-director", location = "edit-director", type = "redirectAction", params = {"facilityId", "${facilityId}", "personId", "${personId}"}),
	@Result(name = "redirect-view", location = "directors-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "screened-people-list", location = "directors_screened_people_list.jsp"),
	@Result(name = "input", location = "director_form.jsp"),
	@Result(name = "view", location = "directors_list.jsp")
})
public class DirectorsAction extends BaseFacilityAction implements Preparable {

	private Long personId;
	private FacilityPerson director;
	private boolean first = false;

	private ListControls lstCtrl = new ListControls();
	
	private PickListValue firstDirectorType;
		
	private JsonResponse response;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}
	
	@Action(value = "directors-list")
	public String doList() {
		loadDirectors();
		lstCtrl.setShowControls(true);
		
		return VIEW;
	}
	
	@Action(value = "directors-screened-people-list")
	public String doScreenedPeopleList() {
		if (getFacility() != null && getFacility().getId() != null) {
			lstCtrl.setResults(facilityService.loadScreenedPeopleForFacility(getFacility().getId(), true, false));
		} else {
			lstCtrl.setResults(new ArrayList<TrackingRecordScreeningApprovalsView>());
		}
		
		return "screened-people-list";
	}
	
	@Action(
		value = "add-director", 
		results = {
			@Result(name = "input", location = "screened_people_list.jsp")
		}
	)
	public String doAddDirector() {
		if (personId != null) {
			TrackingRecordScreeningApprovalsView person = facilityService.loadScreenedPersonForFacility(getFacility().getId(), personId);
			if (person == null) {
				//person has not been screened for this or associated facilities.  Return an error.
				addActionError("The selected person has not been screened for this facility or associated facilities.");
				return doScreenedPeopleList();
			}
		} else {
			addActionError("Person id must not be null.");
			return doScreenedPeopleList();
		}
		
		//person has been screened
		return "redirect-edit-director"; 
	}
	
	@Action(value = "edit-director")
	public String doEditDirector() {
		loadDirector();
		
		return INPUT;
	}
	
	public void prepareDoSaveDirector() {
		loadDirectors();
		loadDirector();
		facilityService.evict(director);
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "director.startDate", message = "Start date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "director.endDate", message = "End date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "director.startDate", message = "Start date is required.", shortCircuit = true)
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "director", message = "Start date must be before End date.")
		}
	)
	@Action(value = "save-director")
	public String doSaveDirector() {
		if (director.getId() == null || director.getFacility() == null) {
			director.setFacility(getFacility());
		}
		
		facilityService.saveDirector(director, first);
		return REDIRECT_VIEW;
	}
	
	private void loadDirector() {
		if (director != null && director.getId() != null) {
			director = facilityService.loadFacilityPerson(getFacility().getId(), director.getId(), getFirstDirectorTypeId(), getSecondDirectorTypeId());
			if (director != null) {
				if (director.getType() != null && director.getType().getId().longValue() == getFirstDirectorTypeId().longValue()) {
					first = true;
				}
			}
		} else if (personId != null) {
			TrackingRecordScreeningApprovalsView screening = facilityService.loadScreenedPersonForFacility(getFacility().getId(), personId);
			if (screening != null) {
				director = new FacilityPerson();
				director.setPerson(screening.getPerson());
			}
		}
		if (director == null) {
			director = new FacilityPerson();
		}
	}
	
	private void loadDirectors() {
		lstCtrl.setResults(facilityService.getDirectors(getFacility().getId()));
	}
	
	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public FacilityPerson getDirector() {
		return director;
	}
	
	public void setDirector(FacilityPerson director) {
		this.director = director;
	}
	
	public boolean isFirst() {
		return first;
	}
	
	public void setFirst(boolean first) {
		this.first = first;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public PickListValue getFirstDirectorType() {
		if (firstDirectorType == null) {
			firstDirectorType = facilityService.getFirstDirectorPersonType();
		}
		return firstDirectorType;
	}

	public JsonResponse getResponse() {
		return response;
	}

	public void setResponse(JsonResponse response) {
		this.response = response;
	}

	private Long getFirstDirectorTypeId() {
		return getFirstDirectorType().getId();
	}
	
	private Long getSecondDirectorTypeId() {
		return facilityService.getSecondDirectorPersonType().getId();
	}

}