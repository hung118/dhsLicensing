package gov.utah.dts.det.ccl.actions.facility.variances;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Variance;
import gov.utah.dts.det.ccl.service.VarianceService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "new_variance_form.jsp"),
	@Result(name = "success", location = "edit-variance", type = "redirectAction", params = {"namespace", "/facility/variances", "facilityId", "${facilityId}", "varianceId", "${variance.id}"})
})
public class NewVarianceAction extends BaseFacilityAction {
	
	private VarianceService varianceService;
	
	private Variance variance;
	
	@SkipValidation
	@Action(value = "new-variance")
	public String doForm() {
		if (variance == null) {
			variance = new Variance();
		}
		
		return INPUT;
	}

	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "variance", message = "&zwnj;")
		},
		customValidators = {
			@CustomValidator(type = "dateRange", fieldName = "variance.requestedDateRange", message = "Requested end date must be after requested start date.")
		}
	)
	@Action(value = "save-new-variance")
	public String doSave() {
		variance.setFacility(getFacility());
		variance = varianceService.saveNewVariance(variance);
		
		return SUCCESS;
	}
	
	public void setVarianceService(VarianceService varianceService) {
		this.varianceService = varianceService;
	}
	
	public Variance getVariance() {
		return variance;
	}
	
	public void setVariance(Variance variance) {
		this.variance = variance;
	}
}