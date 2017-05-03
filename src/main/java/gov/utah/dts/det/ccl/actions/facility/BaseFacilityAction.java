package gov.utah.dts.det.ccl.actions.facility;

import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.actions.CclAction;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.security.permissions.FacilityPermissionEvaluator;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.service.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseFacilityAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, CclAction {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected FacilityService facilityService;
	protected PickListService pickListService;
	protected ApplicationService applicationService;
	protected Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	protected FacilityPermissionEvaluator permissionEvaluator = new FacilityPermissionEvaluator();
	
	private Facility facility;
	
	private Long facilityId;
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}
	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	protected Facility getFacility() {
		if (facility == null) {
			if (facilityId != null) {
				facility = facilityService.loadById(facilityId);
			}
		}
		return facility;
	}
	
	public Long getFacilityId() {
		return facilityId;
	}
	
	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public Authentication getAuthentication() {
		return authentication;
	}

	public boolean hasFacilityEntityPermission(String permission, Long id) {
    	boolean rval = false;
    	if (permission != null && id != null) {
    		Facility fac = facilityService.loadById(id);
    		if (fac != null) {
    			rval = permissionEvaluator.hasPermission(authentication, fac, permission);
    		}
    	}
    	return rval;
    }

	public boolean hasFacilityPermission(String permission, Facility fac) {
    	boolean rval = false;
    	if (permission != null && fac != null) {
   			rval = permissionEvaluator.hasPermission(authentication, fac, permission);
    	}
    	return rval;
    }

}