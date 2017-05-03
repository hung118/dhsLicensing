package gov.utah.dts.det.ccl.actions.facility.complaints;

import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.access.AccessDeniedException;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public abstract class ComplaintStateChangeAction extends BaseComplaintAction {

	protected String note;
	
	protected JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			handleExecute();
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			response = new JsonResponse(400, getActionErrors());
		} catch (AccessDeniedException ade) {
			response = new JsonResponse(401, ade.getMessage());
		}
		
		return SUCCESS;
	}
	
	public abstract void handleExecute() throws Exception;
	
	public JsonResponse getResponse() {
		return response;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}