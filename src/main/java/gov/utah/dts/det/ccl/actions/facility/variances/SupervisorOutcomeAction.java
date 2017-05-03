package gov.utah.dts.det.ccl.actions.facility.variances;

import java.util.Arrays;
import java.util.List;
import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-supervisor-outcome", type = "redirectAction", params = {"facilityId", "${facilityId}", "varianceId", "${varianceId}"}),
	@Result(name = "input", location = "supervisor_outcome_form.jsp")
})
public class SupervisorOutcomeAction extends BaseVarianceAction {

	@Action(value = "save-supervisor-outcome")
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "variance.supervisorOutcome", message = "Outcome is required."),
			@RequiredFieldValidator(fieldName = "variance.supervisorResponse", message = "Response is required.")
		}
	)
	public String doSave() {
		varianceService.saveVarianceSupervisorOutcome(variance);
		varianceService.evict(variance);
		
		return REDIRECT_VIEW;
	}
	
	@Override
	public void validate() {
		if (variance.getSupervisorOutcome() == null) {
			addFieldError("variance.supervisorOutcome", "Outcome is required.");
		}
		if (variance.getSupervisorResponse() == null || "".equals(variance.getLicensorResponse())) {
			addFieldError("variance.supervisorResponse", "Response is required.");
		}
	}
	
	public List<VarianceOutcome> getOutcomes() {
		return Arrays.asList(VarianceOutcome.values());
	}
}