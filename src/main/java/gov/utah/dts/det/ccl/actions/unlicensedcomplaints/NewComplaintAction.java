package gov.utah.dts.det.ccl.actions.unlicensedcomplaints;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Address;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Phone;
import gov.utah.dts.det.ccl.model.UnlicensedComplaint;
import gov.utah.dts.det.ccl.model.enums.FacilityType;
import gov.utah.dts.det.ccl.model.enums.DeliveryMethod;
import gov.utah.dts.det.ccl.model.view.ComplaintView;
import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.util.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "unlicensed-complaints.complaint-form", type = "tiles"),
	@Result(name = "success", location = "open-tab", type = "redirectAction", params = {"namespace", "/facility", "tab", "complaints",
			"act", "tab", "ns", "/complaints/intake", "facilityId", "${facility.id}", "complaintId", "${newComplaintId}"})
})
public class NewComplaintAction extends BaseFacilityAction implements Preparable {

	private ComplaintService complaintService;
	
	private String ownerName;
	private String facName;
	private String address;
	private String city;
	private String county;
	private String zipCode;
	private String phone;
	
	private Facility facility;
	private UnlicensedComplaint complaint;
	private Person owner;
	private Date dateReceived;
	private Date timeReceived;
	
	private Long newComplaintId;
	
	private List<FacilityType> facilityTypes;

	public void prepare() throws Exception {
		if (facility != null && facility.getLocationAddress() != null) {
			facility.setMailingAddress(facility.getLocationAddress());
		}
	}

	@Action(value = "new-complaint")
	public String doNewComplaint() {
		Date now = new Date();
		dateReceived = now;
		timeReceived = now;
		
		facility = new Facility();
		facility.setName(StringUtils.isNotEmpty(facName) ? facName : "Unknown");
		
		Phone primPhone = new Phone();
		primPhone.setPhoneNumber(phone);
		facility.setPrimaryPhone(primPhone);
		
		if (StringUtils.isNotEmpty(ownerName)) {
			owner = new Person();
			String[] names = ownerName.split(" ", 2);
			if (names.length > 0) {
				owner.setFirstName(names[0]);
			}
			if (names.length > 1) {
				owner.setLastName(names[1]);
			}
		}

		Address facAddr = new Address();
		facAddr.setAddressOne(address);
		facAddr.setCity(city);
		facAddr.setZipCode(zipCode);

		facility.setLocationAddress(facAddr);

		return INPUT;
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "dateReceived", message = "Date received is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "timeReceived", message = "Time received is not a valid time. (HH:MM AM/PM)", shortCircuit = true)
		},
		visitorFields = {
			@VisitorFieldValidator(fieldName = "facility", message = "&zwnj;"),
			@VisitorFieldValidator(fieldName = "complaint", message = "&zwnj;"),
			@VisitorFieldValidator(fieldName = "facility.locationAddress", message = "&zwnj;"),
			@VisitorFieldValidator(fieldName = "facility.mailingAddress", message = "&zwnj;")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "dateReceived", message = "Date received is required.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "timeReceived", message = "Time received is required.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "complaint.deliveryMethod", message = "Delivery method is required."),
			@RequiredFieldValidator(fieldName = "facility.mailingAddress", message = "")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "facility.locationAddress.addressOne", message = "Facility address one is required."),
			@RequiredStringValidator(fieldName = "facility.locationAddress.city", message = "Facility city is required."),
			@RequiredStringValidator(fieldName = "facility.locationAddress.zipCode", message = "Facility zip code is required.", shortCircuit = true),
			@RequiredStringValidator(fieldName = "facility.locationAddress.state", message = "Facility state is required.")
		}
	)
	@Action(value = "save-complaint")
	public String doSaveComplaint() {
		complaint.setDateReceived(DateUtils.mergeDateTime(dateReceived, timeReceived));
		complaint.setDisclosureStatementRead(false);
		if (owner != null && StringUtils.isBlank(owner.getFirstName()) && StringUtils.isBlank(owner.getLastName())) {
			owner = null;
		}
		
		facility = facilityService.createUnlicensedFacility(facility, owner, complaint);
		List<ComplaintView> complaints = complaintService.getComplaintsForFacility(facility.getId(), null, null, false);
		newComplaintId = complaints.get(0).getId();
		
		return SUCCESS;
	}
	
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getFacName() {
		return facName;
	}

	public void setFacName(String facName) {
		this.facName = facName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Facility getFacility() {
		return facility;
	}
	
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	public UnlicensedComplaint getComplaint() {
		return complaint;
	}
	
	public void setComplaint(UnlicensedComplaint complaint) {
		this.complaint = complaint;
	}
	
	public Person getOwner() {
		return owner;
	}
	
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	
	public Date getDateReceived() {
		return dateReceived;
	}
	
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	
	@TypeConversion(converter = "gov.utah.dts.det.ccl.view.converter.InspectionTimeConverter")
	public Date getTimeReceived() {
		return timeReceived;
	}
	
	public void setTimeReceived(Date timeReceived) {
		this.timeReceived = timeReceived;
	}
	
	public Long getNewComplaintId() {
		return newComplaintId;
	}
	
	public List<FacilityType> getFacilityTypes() {
		if (facilityTypes == null) {
			facilityTypes = Arrays.asList(FacilityType.values());
		}
		return facilityTypes;
	}
	
	public List<DeliveryMethod> getDeliveryMethods() {
		return Arrays.asList(DeliveryMethod.values());
	}

}