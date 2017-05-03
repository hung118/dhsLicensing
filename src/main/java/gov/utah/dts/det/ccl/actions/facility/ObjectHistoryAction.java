package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.ccl.model.StateChange;
import gov.utah.dts.det.ccl.service.StateObjectService;
import gov.utah.dts.det.ccl.view.JsonResponse;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class ObjectHistoryAction extends ActionSupport {
	
	private StateObjectService stateObjectService;

	private Long objectId;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			List<StateChange> stateChanges = stateObjectService.getObjectStateChanges(objectId);
			response = new JsonResponse(200, stateChanges);
		} catch (AccessDeniedException ade) {
			response = new JsonResponse(401, ade.getMessage());
		}
		
		return SUCCESS;
	}
	
	public void setStateObjectService(StateObjectService stateObjectService) {
		this.stateObjectService = stateObjectService;
	}
	
	public Long getObjectId() {
		return objectId;
	}
	
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}