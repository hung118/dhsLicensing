package gov.utah.dts.det.ccl.actions.facility.complaints;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.ComplaintScreening;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.UnlicensedComplaint;
import gov.utah.dts.det.ccl.model.enums.DeliveryMethod;
import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;
import gov.utah.dts.det.util.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "new_complaint_form.jsp"),
	@Result(name = "success", location = "tab", type = "redirectAction", params = {"namespace", "/facility/complaints/intake", "facilityId", "${facilityId}", "complaintId", "${complaint.id}"})
})
public class NewComplaintAction extends BaseFacilityAction implements Preparable {

	private ComplaintService complaintService;
	private List<PickListValue> conclusionTypes;
	private PickListValue conclusionType;
	private Complaint complaint;
	private Date dateReceived;
	private Date timeReceived;

	@Override
	public void prepare() throws Exception {
		if (isLicensed()) {
			complaint = new Complaint();
		} else {
			complaint = new UnlicensedComplaint();
		}
	}

	@SkipValidation
	@Action(value = "new-complaint")
	public String doForm() {
		Date now = new Date();
		dateReceived = now;
		timeReceived = now;
		return INPUT;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "dateReceived", message = "Date received is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "timeReceived", message = "Time received is not a valid time. (HH:MM AM/PM)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "dateReceived", message = "Date received is required.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "timeReceived", message = "Time received is required.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "complaint.deliveryMethod", message = "Delivery method is required.")
		}
	)
	@Action(value = "save-new-complaint")
	public String doSave() {
		complaint.setFacility(getFacility());
		complaint.setDateReceived(DateUtils.mergeDateTime(dateReceived, timeReceived));
		
		//insert it
		complaint = complaintService.createComplaint(complaint);
		
		//add some junk and save it.
		complaint.setScreening(new ComplaintScreening());
		complaint.getScreening().setScreeningDate(DateUtils.mergeDateTime(dateReceived, timeReceived));
		complaint.getScreening().setConclusionType(conclusionType);
		complaint = complaintService.saveComplaint(complaint);
		
		return SUCCESS;
	}

	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}

	@Override
	public Facility getFacility() {
		return super.getFacility();
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
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

	public boolean isLicensed() {
		return getFacility().getStatus().equals(FacilityStatus.REGULATED);
	}

	public List<DeliveryMethod> getDeliveryMethods() {
		return Arrays.asList(DeliveryMethod.values());
	}

	public List<PickListValue> getConclusionTypes() {
		if (conclusionTypes == null) {
			conclusionTypes = pickListService.getValuesForPickList("Conclusion", true);
		}
		return conclusionTypes;
	}

	public void setConclusionType(PickListValue conclusionType) {
		this.conclusionType = conclusionType;
	}

}