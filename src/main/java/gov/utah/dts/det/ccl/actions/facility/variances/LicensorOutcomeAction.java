package gov.utah.dts.det.ccl.actions.facility.variances;

import java.util.Arrays;
import java.util.List;
import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-licensor-outcome", type = "redirectAction", params = {"facilityId", "${facilityId}", "varianceId", "${varianceId}"}),
	@Result(name = "input", location = "licensor_outcome_form.jsp")
})
public class LicensorOutcomeAction extends BaseVarianceAction {

	@Action(value = "save-licensor-outcome")
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "variance.licensorOutcome", message = "Outcome is required.")
		},
		requiredStrings = {
			@RequiredStringValidator(fieldName = "variance.licensorResponse", message = "Response is required.")
		}
	)
	public String doSave() {
		varianceService.saveVarianceLicensorOutcome(variance);
		varianceService.evict(variance);
		
		return REDIRECT_VIEW;
	}
	
	public List<VarianceOutcome> getOutcomes() {
		return Arrays.asList(VarianceOutcome.values());
	}

}