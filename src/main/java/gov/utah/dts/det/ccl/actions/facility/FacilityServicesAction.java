package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.service.FacilityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ParameterAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/facility/service")
public class FacilityServicesAction extends ActionSupport implements ParameterAware {
	
	private Map<String, String[]> parameters;

	private FacilityService facilityService;

	private Map<String, Object> response;
	
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	@Action(value = "status-reasons", interceptorRefs = {
		@InterceptorRef("controlStack")
	}, results = {
		@Result(name = "success", type = "json", params = {"root", "response", "includeProperties", "reasons,reasons\\[.*?\\]\\.id,reasons\\[.*?\\]\\.value"})
	})
	public String doGetStatusReasons() {
		String[] params = parameters.get("active");
		Boolean active = Boolean.parseBoolean(params[0]);
		
		response = new HashMap<String, Object>();
		if (active != null) {
			List<PickListValue> reasons = facilityService.getStatusReasons(active);
			response.put("reasons", reasons);
		}
		
		return SUCCESS;
	}
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public Map<String, Object> getResponse() {
		return response;
	}
}