package gov.utah.dts.det.ccl.actions.facility.cmps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.CmpTransaction;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.CmpService;
import gov.utah.dts.det.ccl.view.ViewUtils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "cmp_form.jsp"),
	@Result(name = "redirect-view", location = "tab", type = "redirectAction", params = {"namespace", "/facility/cmps", "facilityId", "${facilityId}"}),
	@Result(name = "success", type = "json", params = {"root", "response"})
})
public class CmpAction extends BaseFacilityAction {

	private CmpService cmpService;
	
	private CmpTransaction transaction;
	private List<PickListValue> fees;
	private Map<String, Object> response;
		
	@SkipValidation
	@Action(value = "new-cmp")
	public String doForm() {
		return INPUT;
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "transaction.date", message = "Received Date is required."),
			@RequiredFieldValidator(fieldName = "transaction.amount", message = "Amount is required.")
		},
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "transaction.amount", message = "Amount must be currency.")
		}
	)
	@Action(value = "save-cmp")
	public String doSave() {
		transaction.setFacility(getFacility());
		cmpService.saveCmpTransaction(transaction);
		return REDIRECT_VIEW;
	}
	
	@SkipValidation
	@Action(value = "edit-cmp")
	public String doEdit() {
		transaction = cmpService.loadById(transaction.getId());
		super.setFacilityId(transaction.getFacility().getId());
		return INPUT;
	}
	
	@SkipValidation
	@Action(value = "delete-cmp")
	public String doDelete() {
		response = new HashMap<String, Object>();
		try {
			cmpService.deleteCmpTransaction(transaction);
			response.put("response", "success");
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			response.put("response", "error");
			response.put("errors", getActionErrors());
		} catch (AccessDeniedException ade) {
			response.put("response", "error");
			response.put("errors", new String[]{ade.getMessage()});
		}
		return SUCCESS;		
	}
		
	public void validate() {
		
	}
	
	public void setCmpService(CmpService cmpService) {
		this.cmpService = cmpService;
	}


	public CmpTransaction getTransaction() {
		return transaction;
	}


	public void setTransaction(CmpTransaction transaction) {
		this.transaction = transaction;
	}
	
	public List<PickListValue> getFees() {
		if (fees == null) {
			fees = pickListService.getValuesForPickList("Fee Amount", true);
		}
		return fees;
	}
	
	public Map<String, Object> getResponse() {
		return response;
	}
	
}