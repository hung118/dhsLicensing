package gov.utah.dts.det.ccl.actions;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class CountryAutocompleteAction extends ActionSupport {
	
	private final String PICK_LIST_COUNTRIES = "Countries";

	@Autowired
	private PickListService picklistService;
	
	private JsonResponse response;
	
	private String countryName;
	
	@Override
	public String execute() throws Exception {
		try {
			List<PickListValue> countries = null;
			if (StringUtils.isNotBlank(countryName)) {
				countries = picklistService.searchPickListByValue(PICK_LIST_COUNTRIES, countryName, true);
				if (countries.isEmpty()) {
					PickListValue plv = new PickListValue();
					plv.setId(null);
					plv.setValue(countryName);
					countries.add(plv);
				}
			} else {
				countries = picklistService.getValuesForPickList(PICK_LIST_COUNTRIES, true);
			}
			response = new JsonResponse(200, countries);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			response = new JsonResponse(400, getActionErrors());
		} catch (AccessDeniedException ade) {
			response = new JsonResponse(401, ade.getMessage());
		}
		
		return SUCCESS;
	}
	
	public JsonResponse getResponse() {
		return response;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}