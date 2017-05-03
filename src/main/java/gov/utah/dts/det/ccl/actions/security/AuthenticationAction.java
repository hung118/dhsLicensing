package gov.utah.dts.det.ccl.actions.security;

import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.service.ApplicationService;
import gov.utah.dts.det.util.DevSiteminderFilter;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings({"serial", "unchecked"})
@Results({
	@Result(name = "input", location = "login_form.jsp")
})
public class AuthenticationAction extends ActionSupport implements SessionAware {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationAction.class);
	
	private Map<String, Object> sessionMap;
	
	private ApplicationService applicationService;
	
	private String username;
	
	private String logoutUrl;
	
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
	
	@Action(value = "login")
	public String doLogin() {
		return INPUT;
	}
	
	@Action(value = "logout", results = {
		@Result(name = "success", location = "${logoutUrl}", type = "redirect")
	})
	public String doLogout() {
		invalidateSession();
		
		logoutUrl = applicationService.getApplicationPropertyValue(ApplicationPropertyKey.LOGOUT_URL.getKey());
		
		return SUCCESS;
	}
	
	@Action(value = "authenticate", results = {
		@Result(name = "success", location = "index", type = "redirectAction", params = {"namespace", "/home"})
	})
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "username", message = "Username is required.")
		}
	)
	public String doAuthenticate() {
		sessionMap.put(DevSiteminderFilter.USERNAME_KEY, username);
		
		return SUCCESS;
	}
	
	private void invalidateSession() {
		if (sessionMap instanceof org.apache.struts2.dispatcher.SessionMap) {
			try {
				((org.apache.struts2.dispatcher.SessionMap) sessionMap).invalidate();
			} catch (IllegalStateException e) {
				logger.error("unable to invalidate session", e);
			}
		}
	}
	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getLogoutUrl() {
		return logoutUrl;
	}
}