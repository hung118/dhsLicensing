package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.view.FacilityResultView;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.ActionSupport;
import gov.utah.dts.det.ccl.service.CclServiceException;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class FacilityAutocompleteAction extends ActionSupport {

	private FacilityService facilityService;
	
	private Long facId;
	private String facName;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			List<FacilityResultView> facilities = null;
			if (facId != null) {
				FacilityResultView frv = facilityService.getFacilityResultView(facId);
				facilities = new ArrayList<FacilityResultView>();
				facilities.add(frv);
			} else {
				facilities = facilityService.searchFacilitiesByName(facName,null);
			}
			response = new JsonResponse(200, facilities);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			response = new JsonResponse(400, getActionErrors());
		} catch (AccessDeniedException ade) {
			response = new JsonResponse(401, ade.getMessage());
		}
		
		return SUCCESS;
	}
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public Long getFacId() {
		return facId;
	}
	
	public void setFacId(Long facId) {
		this.facId = facId;
	}
	
	public String getFacName() {
		return facName;
	}
	
	public void setFacName(String facName) {
		this.facName = facName;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}