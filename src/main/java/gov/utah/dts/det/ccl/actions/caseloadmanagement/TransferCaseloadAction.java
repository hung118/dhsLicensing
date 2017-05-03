package gov.utah.dts.det.ccl.actions.caseloadmanagement;

import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.service.UserCaseloadCount;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;

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
public class TransferCaseloadAction extends ActionSupport {
	
	private FacilityService facilityService;

	private Person fromLS;
	private Person toLS;
	private List<Facility> facilities;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			facilityService.transferFacilities(fromLS, toLS, facilities);
			List<UserCaseloadCount> counts = facilityService.getUserCaseloadCounts();
			response = new JsonResponse(200, counts);
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
	
	public Person getFromLS() {
		return fromLS;
	}
	
	public void setFromLS(Person fromLS) {
		this.fromLS = fromLS;
	}
	
	public Person getToLS() {
		return toLS;
	}
	
	public void setToLS(Person toLS) {
		this.toLS = toLS;
	}
	
	public List<Facility> getFacilities() {
		return facilities;
	}
	
	public void setFacilities(List<Facility> facilities) {
		this.facilities = facilities;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}