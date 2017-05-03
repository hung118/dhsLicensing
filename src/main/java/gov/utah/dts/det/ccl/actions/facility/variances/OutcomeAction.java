package gov.utah.dts.det.ccl.actions.facility.variances;

import gov.utah.dts.det.ccl.actions.facility.variances.reports.OutcomeNotificationLetter;
import gov.utah.dts.det.ccl.model.EMessage;
import gov.utah.dts.det.ccl.model.EmailAttachment;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;
import gov.utah.dts.det.util.MailDispatcher;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-outcome", type = "redirectAction", params = {"facilityId", "${facilityId}", "varianceId", "${varianceId}"}),
	@Result(name = "input", location = "outcome_form.jsp")
})
public class OutcomeAction extends BaseVarianceAction {

	@Action(value = "save-outcome")
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "variance.reviewDate", message = "Review date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "variance.startDate", message = "Start date is not a valid date. (MM/DD/YYYY)"),
			@ConversionErrorFieldValidator(fieldName = "variance.endDate", message = "End date is not a valid date. (MM/DD/YYYY)")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "variance.reviewDate", message = "Review date is reqired.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "variance.outcome", message = "Review outcome is required.")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "variance.directorResponse", message = "Response is required.")	
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "variance", message = "Start date must be before end date.")
		}
	)
	public String doSave() {
		varianceService.saveVarianceOutcome(variance);
		reloadVariance();

		// Email notification
		if (variance.isFinalized()) {
			sendEmailNotifications();
		}
		
		return REDIRECT_VIEW;
	}
	
	private void sendEmailNotifications() {
		EMessage message = new EMessage();
		String ruleNumber = "R501-"+variance.getRule().getGeneratedRuleNumber();
		message.setSubject("Re: Rule "+ruleNumber+" Variance Request");
		message.setHeader("Utah Department of Human Services Office of Licensing");
		if (getRequest().getRequestURL() != null) {
			int end = getRequest().getRequestURL().indexOf("/facility");
			if (end > 0) {
				String url = getRequest().getRequestURL().substring(0, end);
				message.setHeaderUrl(url);
			}
		}
		StringBuilder sb = null;
		// Set the email/letter salutation
		if (!variance.getFacility().getPrimaryContacts().isEmpty()) {
			List<String> names = new ArrayList<String>();
			for(FacilityPerson p : variance.getFacility().getPrimaryContacts()) {
				names.add(p.getPerson().getFirstAndLastName());
			}
			String nameString = org.apache.commons.lang.StringUtils.join(names, "/");
			message.setSalutation("Dear "+nameString+",");
		} else {
			message.setSalutation("Dear "+variance.getFacility().getName()+",");
		}
 		message.setFooter(Boolean.TRUE);
		ByteArrayOutputStream ba = null;
		String filename = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		sb = new StringBuilder();
		if (variance.getOutcome().equals(VarianceOutcome.APPROVED) || variance.getOutcome().equals(VarianceOutcome.DENIED)) {
			sb.append("The Department of Human Services, Office of Licensing, has reviewed your request dated "+df.format(variance.getRequestDate())+" ");
			sb.append("requesting a variance of rule "+ruleNumber+" and has " + variance.getOutcome().getDisplayName().toUpperCase());
			sb.append(" your request.<br/><br/>");
	       	if (variance.getOutcome().equals(VarianceOutcome.APPROVED)) {
	       		filename = ruleNumber+"_Variance_Approval.pdf";
	       	} else {
	       		filename = ruleNumber+"_Variance_Denied.pdf";
	       	}
		} else {
			sb.append("The Department of Human Services, Office of Licensing, has reviewed your request dated "+df.format(variance.getRequestDate())+" ");
			sb.append("requesting a variance of rule "+ruleNumber+" and has deemed your request " + variance.getOutcome().getDisplayName().toUpperCase());
			sb.append(".<br/><br/>");
       		filename = ruleNumber+"_Variance_Not_Necessary.pdf";
		}
		sb.append("Please see the attached document for details.");
       	message.setMessage(sb.toString());
       	
       	// Do the notification letter generation processing.
		ba = OutcomeNotificationLetter.generate(variance, getRequest());
		// Check to see if generated pdf stream contains information
		if (ba != null) {
			// Add pdf attachment
	        byte[] bytes = ba.toByteArray();
	        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
	        EmailAttachment attachment = new EmailAttachment(filename, dataSource);
	        message.addAttachment(attachment);
	        try { 
	        	ba.close(); 
	        	ba = null; 
	        } catch(Exception ex) { }
		}
        // Add Facility Contact as email recipient
        if (StringUtils.isNotBlank(variance.getFacility().getEmail())) {
        	message.addRecipient(variance.getFacility().getEmail());
        }
        // Add the Variance Request last modifying person as a carbon copy recipient
        if (variance.getModifiedBy() != null && StringUtils.isNotBlank(variance.getModifiedBy().getEmail())) {
        	message.addCC(variance.getModifiedBy().getEmail());
        }
		// Add the Licensing Specialist as a blind copy recipient
		if (variance.getLicensorModifiedBy() != null && StringUtils.isNotBlank(variance.getLicensorModifiedBy().getEmail())) {
			message.addBCC(variance.getLicensorModifiedBy().getEmail());
		}
		// Add the Licensing Supervisor as a blind copy recipient
		if (variance.getSupervisorModifiedBy() != null && StringUtils.isNotBlank(variance.getSupervisorModifiedBy().getEmail())) {
			message.addBCC(variance.getSupervisorModifiedBy().getEmail());
		}
		// Add the Licensing Director as a blind copy recipient
		if (variance.getDirectorModifiedBy() != null && StringUtils.isNotBlank(variance.getDirectorModifiedBy().getEmail())) {
			message.addBCC(variance.getDirectorModifiedBy().getEmail());
		}
        MailDispatcher.send(message);
	}
	
	@Override
	public void validate() {
		if (variance.getOutcome() != null) {
			if (variance.getOutcome() == VarianceOutcome.APPROVED) {
				if (variance.getStartDate() == null) {
					addFieldError("variance.startDate", "Start date is required.");
				}
				if (variance.getEndDate() == null) {
					addFieldError("variance.endDate", "End date is required.");
				}
			}
		}
	}
	
	public List<VarianceOutcome> getOutcomes() {
		return Arrays.asList(VarianceOutcome.values());
	}

}