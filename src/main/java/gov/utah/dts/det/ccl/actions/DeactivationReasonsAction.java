package gov.utah.dts.det.ccl.actions;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.view.JsonResponse;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class DeactivationReasonsAction extends ActionSupport {

	private PickListService pickListService;
	
	private JsonResponse response;
	
	public String execute() throws Exception {
		List<PickListValue> values = pickListService.getValuesForPickList("Facility Deactivation Reasons", true);
		
		response = new JsonResponse(200, values);
		
		return SUCCESS;
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}