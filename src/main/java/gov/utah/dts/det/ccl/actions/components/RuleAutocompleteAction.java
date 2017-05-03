package gov.utah.dts.det.ccl.actions.components;

import gov.utah.dts.det.ccl.service.RuleService;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.RuleResultView;
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
public class RuleAutocompleteAction extends ActionSupport {

	private RuleService ruleService;
	
	private Long ruleId;
	private String ruleNumber;
	private Long inspectionId;
	private Long findingId;
	private boolean excludeInactive = true;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			List<RuleResultView> ruleViews = null;
			if (ruleId != null) {
				RuleResultView rv = ruleService.getRuleView(ruleId);
				ruleViews = new ArrayList<RuleResultView>();
				ruleViews.add(rv);
			} else {
				ruleViews = ruleService.doRuleSearch(inspectionId, findingId, ruleNumber, excludeInactive);
			}
			response = new JsonResponse(200, ruleViews);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			response = new JsonResponse(400, getActionErrors());
		} catch (AccessDeniedException ade) {
			response = new JsonResponse(401, ade.getMessage());
		}
		
		return SUCCESS;
	}

	public void setRuleService(RuleService ruleService) {
		this.ruleService = ruleService;
	}
	
	public Long getRuleId() {
		return ruleId;
	}
	
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	public String getRuleNumber() {
		return ruleNumber;
	}
	
	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public Long getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}
	
	public Long getFindingId() {
		return findingId;
	}
	
	public void setFindingId(Long findingId) {
		this.findingId = findingId;
	}

	public boolean isExcludeInactive() {
		return excludeInactive;
	}

	public void setExcludeInactive(boolean excludeInactive) {
		this.excludeInactive = excludeInactive;
	}

	public JsonResponse getResponse() {
		return response;
	}
}