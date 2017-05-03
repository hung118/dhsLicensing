package gov.utah.dts.det.ccl.actions.facility.information.forms;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.view.ViewUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.security.access.AccessDeniedException;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "view-forms", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "forms_form.jsp"),
	@Result(name = "view", location = "forms_detail.jsp")
})
public class FormsAction extends BaseFacilityAction implements Preparable {

	private Facility facility;

	@Override
	public void prepare() throws Exception {
		facility = super.getFacility();
	}

	@SkipValidation
	@Action(value = "view-forms")
	public String doView() {
		return VIEW;
	}

	@SkipValidation
	@Action(value = "edit-forms")
	public String doEdit() {
		return INPUT;
	}

	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "facility", message = "&zwnj;")
		},
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "facility.rulesCertReceived", message = "Rules Certification Received date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.codeOfConductReceived", message = "Code of Conduct Received date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.confidentialFormReceived", message = "Confidential Form Received date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.emergencyPlanReceived", message = "Emergency Plan Received date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.referenceOneReceived", message = "Reference 1 Received date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.referenceTwoReceived", message = "Reference 1 Received date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "facility.referenceThreeReceived", message = "Reference 1 Received date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		}
	)
	@Action(value = "save-forms")
	public String doSave() {
		try {
			facilityService.saveFacility(facility);
			facilityService.evict(facility);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
		} catch (AccessDeniedException ade) {
			addActionError("You do not have access to make this change.");
		}

		if (hasErrors()) {
			return INPUT;
		}

		return REDIRECT_VIEW;
	}

	@Override
	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}
}