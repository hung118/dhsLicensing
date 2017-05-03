package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.admin.service.AdminService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin")
@Results({
	@Result(name = "success", location = "cache_cleared.jsp")
})
public class CacheAction extends ActionSupport {
	
	private static final String ERROR_MSG = "Unable to clear the cache.";
	private static final String SUCCESS_MSG = "Cache was successfully cleared.";
	
	private AdminService adminService;
	
	private String message;

	@Action(value = "clear-cache")
	public String doClearCache() {
		try {
			adminService.clearCache();
			message = SUCCESS_MSG;
		} catch (Exception e) {
			message = ERROR_MSG + ": " + e.getMessage();
		}
		
		return SUCCESS;
	}
	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	public String getMessage() {
		return message;
	}
}