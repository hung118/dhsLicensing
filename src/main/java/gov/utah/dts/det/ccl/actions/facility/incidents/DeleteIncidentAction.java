package gov.utah.dts.det.ccl.actions.facility.incidents;

import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.view.ViewUtils;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.access.AccessDeniedException;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "json", params = {"root", "response"})
})
public class DeleteIncidentAction extends BaseIncidentAction {

	private Map<String, Object> response;

	public String execute() {
		response = new HashMap<String, Object>();
		try {
			incidentService.deleteIncident(getIncident());
			response.put("response", "success");
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			response.put("response", "error");
			response.put("errors", getActionErrors());
		} catch (AccessDeniedException ade) {
			response.put("response", "error");
			response.put("errors", new String[]{ade.getMessage()});
		}
		return SUCCESS;
	}
	
	public Map<String, Object> getResponse() {
		return response;
	}
}