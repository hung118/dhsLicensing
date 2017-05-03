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
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;


@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-edit", location = "edit-contact", type = "redirectAction", params = {"facilityId", "${facilityId}", "personId", "${personId}"}),
	@Result(name = "redirect-edit-contact", location = "edit-contact", type = "redirectAction", params = {"facilityId", "${facilityId}", "personId", "${personId}"}),
	@Result(name = "redirect-view", location = "contacts-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "screened-people-list", location = "contacts_screened_people_list.jsp"),
	@Result(name = "input", location = "contact_form.jsp"),
	@Result(name = "view", location = "contacts_list.jsp")
})
public class ContactsAction extends BaseFacilityAction implements Preparable {
	
	private static String SCREENED_PEOPLE_LIST = "screened-people-list";
	private static String REDIRECT_EDIT_CONTACT = "redirect-edit-contact";

	private Long personId;
	private FacilityPerson contact;
	private boolean primary = false;

	private ListControls lstCtrl = new ListControls();
	
	private PickListValue primaryContactType;
	
	private JsonResponse response;

	@Override
	public void prepare() throws Exception {
		lstCtrl.setShowControls(false);
	}

	@Action(value = "contacts-list")
	public String doList() {
		loadContacts();
		lstCtrl.setShowControls(true);

		return VIEW;
	}

	@Action(value = "contacts-screened-people-list")
	public String doScreenedPeopleList() {
		if (getFacility() != null && getFacility().getId() != null) {
			lstCtrl.setResults(facilityService.loadScreenedPeopleForFacility(getFacility().getId(), false, true));
		} else {
			lstCtrl.setResults(new ArrayList<TrackingRecordScreeningApprovalsView>());
		}
		
		return SCREENED_PEOPLE_LIST;
	}

	@Action(value = "add-contact")
	public String doAddContact() {
		return REDIRECT_EDIT_CONTACT;
	}

	@Action(value = "edit-contact")
	public String doEditContact() {
		loadContact();

		return INPUT;
	}

	public void prepareDoSaveContact() {
		loadContacts();
		loadContact();
		facilityService.evict(contact);
	}

	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "contact.person.firstName", message = "First name is required."),
			@RequiredStringValidator(fieldName = "contact.person.lastName", message = "Last name is required.")
		},
		emails = {
			@EmailValidator(type = ValidatorType.SIMPLE, fieldName = "contact.person.email", message = "Email is not a proper email value.", shortCircuit = true)
		}
	)
	@Action(value = "save-contact")
	public String doSaveContact() {
		if (contact.getId() == null || contact.getFacility() == null) {
			contact.setFacility(getFacility());
		}
		
		if(primary) {
			contact.setType(facilityService.getPrimaryContactPersonType());
		} else {
			//regular contact
			contact.setType(facilityService.getSecondaryContactPersonType());
		}
		
		facilityService.saveContact(contact);

		// Save button is clicked
		return REDIRECT_VIEW;
	}

	private void loadContact() {
		if (contact != null && contact.getId() != null) {
			contact = facilityService.loadFacilityPerson(getFacility().getId(), contact.getId(), getSecondaryContactPersonTypeId(), getPrimaryContactTypeId());
			if (contact != null && contact.getType() != null && contact.getType().getId().longValue() == getPrimaryContactTypeId().longValue()) {
				primary = true;
			}
		} else if(personId != null) {
			TrackingRecordScreeningApprovalsView screening = facilityService.loadScreenedPersonForFacility(getFacility().getId(), personId);
			if (screening != null) {
				contact = new FacilityPerson();
				contact.setPerson(screening.getPerson());
			}
		} else {
			contact = new FacilityPerson();
		}	
	}

	private void loadContacts() {
		lstCtrl.setResults(facilityService.getContacts(getFacility().getId()));
	}

	@Override
	public Facility getFacility() {
		return super.getFacility();
	}
	
	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	public JsonResponse getResponse() {
		return response;
	}

	public void setResponse(JsonResponse response) {
		this.response = response;
	}

	public FacilityPerson getContact() {
		return contact;
	}

	public void setContact(FacilityPerson contact) {
		this.contact = contact;
	}
	
	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public PickListValue getPrimaryContactType() {
		if (primaryContactType == null) {
			primaryContactType = facilityService.getPrimaryContactPersonType();
		}
		return primaryContactType;
	}

	private Long getPrimaryContactTypeId() {
		return getPrimaryContactType().getId();
	}

	private Long getSecondaryContactPersonTypeId() {
		return facilityService.getSecondaryContactPersonType().getId();
	}
	
}
