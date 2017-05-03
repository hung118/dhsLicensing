package gov.utah.dts.det.ccl.actions.facility.variances;

import gov.utah.dts.det.ccl.model.Variance;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-request", type = "redirectAction", params = {"facilityId", "${facilityId}", "varianceId", "${varianceId}"}),
	@Result(name = "input", location = "request_form.jsp")
})
public class RequestAction extends BaseVarianceAction {

	@Action(value = "save-request")
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "variance", message = "&zwnj;")
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "variance.requestedDateRange", message = "Requested end date must be after requested start date.")
		}
	)
	public String doSave() {
		varianceService.saveVariance(variance);
		varianceService.evict(variance);
		
		return REDIRECT_VIEW;
	}
	
	@Override
	public Variance getVariance() {
		return variance;
	}
}