package gov.utah.dts.det.ccl.actions.facility.complaints.intake;

import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.enums.DeliveryMethod;
import gov.utah.dts.det.util.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import gov.utah.dts.det.admin.model.PickListValue;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-information", type = "redirectAction", params = {"facilityId", "${facilityId}", "complaintId", "${complaintId}"}),
	@Result(name = "input", location = "information_form.jsp"),
	@Result(name = "view", location = "information_detail.jsp")
})
public class InformationAction extends BaseIntakeAction implements Preparable {

	private Complaint complaint;
	private Date dateReceived;
	private Date timeReceived;
	
	@Override
	public void prepare() throws Exception {
			
	}
	
	@Action(value = "information-section")
	public String doSection() {
		complaint = super.getComplaint();
		return VIEW;
	}
	
	@Action(value = "view-information")
	public String doView() {
		complaint = super.getComplaint();
		return VIEW;
	}
	
	@Action(value = "edit-information")
	public String doEdit() {
		complaint = super.getComplaint();
		if (!hasPermission("save-intake")) {
			return REDIRECT_VIEW;
		}
		dateReceived = complaint.getDateReceived();
		timeReceived = complaint.getDateReceived();
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		complaint = super.getComplaint();
		complaintService.evict(complaint);
	}
	
	@Action(value = "save-information")
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
	public String doSave() {
		if (hasPermission("save-intake")) {
			complaint.setDateReceived(DateUtils.mergeDateTime(dateReceived, timeReceived));
			complaint.getScreening().setScreeningDate(DateUtils.mergeDateTime(dateReceived, timeReceived));
			complaintService.saveIntake(complaint);
		}
		
		return REDIRECT_VIEW;
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
	
	@Override
	public Complaint getComplaint() {
		return complaint;
	}
	
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	
	public List<DeliveryMethod> getDeliveryMethods() {
		return Arrays.asList(DeliveryMethod.values());
	}
	
	@Override
	public List<PickListValue> getConclusionTypes() {
		return super.getConclusionTypes();
	}
}