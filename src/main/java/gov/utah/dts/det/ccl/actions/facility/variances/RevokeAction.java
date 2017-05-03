package gov.utah.dts.det.ccl.actions.facility.variances;

import gov.utah.dts.det.ccl.actions.facility.variances.reports.RevokeNotificationLetter;
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
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-outcome", type = "redirectAction", params = {"facilityId", "${facilityId}", "varianceId", "${varianceId}"}),
	@Result(name = "input", location = "revoke_form.jsp")
})
public class RevokeAction extends BaseVarianceAction {

	@Action(value = "save-revoke")
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "variance", message = "&zwnj;")
		},
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "variance.revocationDate", message = "Revocation date is not a valid date. (MM/DD/YYYY)")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "variance.revocationDate", message = "Revocation date is reqired.", shortCircuit = true)
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "variance.revokeReason", message = "Reason is required.")
		},
		customValidators = {
			@CustomValidator(type = "dateBetweenRange", fieldName = "variance.revocationDateCompareRange", message = "Revocation date must be between start and end dates.")
		}
	)
	public String doSave() {
		varianceService.saveVarianceRevoke(variance);
		reloadVariance();
		sendEmailNotifications();
		
		return REDIRECT_VIEW;
	}

	private void sendEmailNotifications() {
		EMessage message = new EMessage();
		String ruleNumber = "R501-"+variance.getRule().getGeneratedRuleNumber();
		message.setSubject("Re: Revocation of Rule "+ruleNumber+" Variance");
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
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		sb = new StringBuilder();
		sb.append("The Department of Human Services, Office of Licensing, has <bold>REVOKED</bold> your variance of ");
		sb.append("rule "+ruleNumber+" that was in effect for the period beginning "+df.format(variance.getStartDate())+" and ending "+df.format(variance.getEndDate())+".");
		sb.append("<br/><br/>The revocation of this variance will go into effect as of "+df.format(variance.getRevocationDate())+".");
		sb.append("<br/><br/>Please see the attached document for details.");
       	message.setMessage(sb.toString());
       	
       	// Do the notification letter generation processing.
		ba = RevokeNotificationLetter.generate(variance, request);
		// Check to see if generated pdf stream contains information
		if (ba != null) {
			// Add pdf attachment
	        byte[] bytes = ba.toByteArray();
	        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
	        EmailAttachment attachment = new EmailAttachment(ruleNumber+"_Variance_Revocation.pdf", dataSource);
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
		// Add the Revoking Licensing Director as a blind copy recipient
		if (variance.getRevokeModifiedBy() != null && StringUtils.isNotBlank(variance.getRevokeModifiedBy().getEmail())) {
			message.addBCC(variance.getRevokeModifiedBy().getEmail());
		}
        MailDispatcher.send(message);
	}
	
	@Override
	public void validate() {
		if (variance.getOutcome() == null || !variance.getOutcome().equals(VarianceOutcome.APPROVED) || !variance.isFinalized()) {
			addActionError("The variance is not in a revocable state.");
		}
	}
	
	public List<VarianceOutcome> getOutcomes() {
		return Arrays.asList(VarianceOutcome.values());
	}
}