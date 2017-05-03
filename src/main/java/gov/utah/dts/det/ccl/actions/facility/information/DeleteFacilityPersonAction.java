package gov.utah.dts.det.ccl.actions.facility.information;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
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
public class DeleteFacilityPersonAction extends BaseFacilityAction {

	private Long personId;
	
	private JsonResponse response;
	
	public String execute() throws Exception {
		try {
			facilityService.deletePerson(getFacility(), personId);
			response = new JsonResponse(200);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			response = new JsonResponse(400, getActionErrors());
		} catch (AccessDeniedException ade) {
			response = new JsonResponse(401, ade.getMessage());
		}
		
		return SUCCESS;
	}

	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}