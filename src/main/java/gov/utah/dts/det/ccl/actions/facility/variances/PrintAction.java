package gov.utah.dts.det.ccl.actions.facility.variances;

import gov.utah.dts.det.ccl.actions.facility.variances.reports.OutcomeNotificationLetter;
import gov.utah.dts.det.ccl.actions.facility.variances.reports.RevokeNotificationLetter;
import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "view-outcome", type = "redirectAction", params = {"facilityId", "${facilityId}", "varianceId", "${varianceId}"})
})
public class PrintAction extends BaseVarianceAction implements ServletRequestAware {

    private HttpServletRequest request;
    
	@Action(value = "print-outcome")
	@Validations(
		customValidators = {
			@CustomValidator(type = "outcomeFinalized", fieldName = "variance", message = "Variance outcome must be finalized.")
		}
	)
	public void doPrintOutcome() {
		ByteArrayOutputStream ba = null;
		try {
	       	// Do the notification letter generation processing.
			ba = OutcomeNotificationLetter.generate(variance, request);
			if (ba != null) {
				String ruleNumber = "R501-"+variance.getRule().getGeneratedRuleNumber();
				String filename = "";
		       	if (variance.getOutcome().equals(VarianceOutcome.APPROVED)) {
		       		filename = ruleNumber+"_Variance_Approval.pdf";
		       	} else 
		       	if (variance.getOutcome().equals(VarianceOutcome.DENIED)) {
		       		filename = ruleNumber+"_Variance_Denied.pdf";
		       	} else {
		       		filename = ruleNumber+"_Variance_Not_Necessary.pdf";
		       	}
				response.setContentType("application/pdf");
				response.setContentLength(ba.size());
				response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				ServletOutputStream out = response.getOutputStream();
				ba.writeTo(out);
				out.flush();
			}
		} catch (Exception e) {
		}
	}

	@Action(value = "print-revoke")
	@Validations(
		customValidators = {
			@CustomValidator(type = "revokeFinalized", fieldName = "variance", message = "Variance revocation must be finalized.")
		}
	)
	public void doPrintRevocation() {
		ByteArrayOutputStream ba = null;
		try {
	       	// Do the notification letter generation processing.
			ba = RevokeNotificationLetter.generate(variance, request);
			if (ba != null) {
				String ruleNumber = "R501-"+variance.getRule().getGeneratedRuleNumber();
				String filename = ruleNumber+"_Variance_Revocation.pdf";
				response.setContentType("application/pdf");
				response.setContentLength(ba.size());
				response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				ServletOutputStream out = response.getOutputStream();
				ba.writeTo(out);
				out.flush();
			}
		} catch (Exception e) {
		}
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

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}