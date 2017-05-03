package gov.utah.dts.det.ccl.actions.errors;

import com.opensymphony.xwork2.ActionSupport;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.security.AccessDeniedType;
import gov.utah.dts.det.ccl.security.CclAccessDeniedHandler;
import gov.utah.dts.det.service.ApplicationService;
import java.util.Map;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.RequestAware;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "error", type = "tiles")
})
public class ErrorsAction extends ActionSupport implements RequestAware {

	private Map<String, Object> requestMap;
	
	private ApplicationService applicationService;
	
	private String errorMessage;
	
	@Override
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	
	@Action(value = "error-403")
	public String do403Error() {
		AccessDeniedType type = (AccessDeniedType) requestMap.get(CclAccessDeniedHandler.ERROR_TYPE_KEY);
		if (type != null) {
			switch (type) {
				case ACCESS_DENIED:
					errorMessage = applicationService.getApplicationPropertyValue(ApplicationPropertyKey.ACCESS_DENIED_MSG.getKey());
					break;
				case NO_ACCOUNT:
					errorMessage = applicationService.getApplicationPropertyValue(ApplicationPropertyKey.NO_ACCOUNT_MSG.getKey());
					break;
			}
		}
		
		return SUCCESS;
	}
	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}