package gov.utah.dts.det.ccl.actions.facility.complianceandhistory;

import gov.utah.dts.det.ccl.service.InspectionService;
import gov.utah.dts.det.ccl.service.RuleViolation;
import gov.utah.dts.det.ccl.view.JsonResponse;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class RuleViolationsAction extends ActionSupport {

	private InspectionService inspectionService;
	
	private Long facilityId;
	private Long ruleId;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		List<RuleViolation> violations = inspectionService.getRuleViolations(facilityId, ruleId);
		response = new JsonResponse(200, violations);
		return SUCCESS;
	}
	
	public void setInspectionService(InspectionService inspectionService) {
		this.inspectionService = inspectionService;
	}
	
	public Long getFacilityId() {
		return facilityId;
	}
	
	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}
	
	public Long getRuleId() {
		return ruleId;
	}
	
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}