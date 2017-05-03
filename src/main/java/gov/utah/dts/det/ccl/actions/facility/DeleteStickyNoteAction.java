package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.ccl.view.JsonResponse;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class DeleteStickyNoteAction extends BaseFacilityAction {

	private Integer id;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		getFacility().deleteNote(id);
		facilityService.saveFacility(getFacility());
		response = new JsonResponse(200, getFacility().getNotes());
		return SUCCESS;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}