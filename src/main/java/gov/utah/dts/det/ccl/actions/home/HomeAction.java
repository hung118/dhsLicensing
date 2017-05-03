package gov.utah.dts.det.ccl.actions.home;

import java.io.IOException;

import gov.utah.dts.det.ccl.actions.caseloadmanagement.CaseloadSortBy;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.SecurityService;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.service.ApplicationService;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "home", type = "tiles"),
	@Result(name = "search", location = "facility/search/index", type = "redirectAction", params = {"namespace", "/"})
})
public class HomeAction extends ActionSupport {

	private ApplicationService applicationService;
	private UserService userService;
	private SecurityService securityService;
	
	private Long personId;
	
	private String welcomeMessage;
	private User user;
	
	@Action(value = "index")
	public String doHome() {
		welcomeMessage = applicationService.findApplicationPropertyByKey(ApplicationPropertyKey.WELCOME_MSG.getKey()).getValue();
		
		try {
			loadUser();
		} catch (IllegalAccessException iae) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	@Action(value = "userInfo", results = {
	@Result(name = "success", location = "user_info_home.jsp")
	})
	public String doUserIfo() {
		try {
			loadUser();
		} catch (IllegalAccessException iae) {
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	@Action(value = "admin-manager", results = {
	@Result(name = "success", location = "admin_manager_home.jsp")
	})
	public String doAdminManagerHome() {
		return doHomeForRole(RoleType.ROLE_ADMIN_MANAGER);
	}

	@Action(value = "facility-provider", results = {
		@Result(name = "success", location = "facility_provider_home.jsp")
	})
	public String doFacilityProviderHome() {
		return doHomeForRole(RoleType.ROLE_FACILITY_PROVIDER);
	}

	@Action(value = "licensing-director", results = {
		@Result(name = "success", location = "licensing_director_home.jsp")
	})
	public String doLicensingDirectorHome() {
		return doHomeForRole(RoleType.ROLE_LICENSING_DIRECTOR);
	}

	@Action(value = "licensing-manager", results = {
		@Result(name = "success", location = "licensing_manager_home.jsp")
	})
	public String doLicensingManagerHome() {
		return doHomeForRole(RoleType.ROLE_LICENSOR_MANAGER);
	}

	@Action(value = "licensing-specialist", results = {
		@Result(name = "success", location = "licensing_specialist_home.jsp")
	})
	public String doLicensingSpecialistHome() {
		return doHomeForRole(RoleType.ROLE_LICENSOR_SPECIALIST);
	}
		
	@Action(value = "office-specialist", results = {
		@Result(name = "success", location = "office_specialist_home.jsp")
	})
	public String doOfficeSpecialistHome() {
		return doHomeForRole(RoleType.ROLE_OFFICE_SPECIALIST);
	}
		
	@Action(value = "super-admin", results = {
		@Result(name = "success", location = "super_admin_home.jsp")
	})
	public String doSuperAdminHome() {
		return doHomeForRole(RoleType.ROLE_SUPER_ADMIN);
	}
		
	private String doHomeForRole(RoleType roleType) {
		try {
			loadUser();
			if (!hasRole(roleType)) {
				throw new IllegalAccessException("The requested user does not have the role: " + roleType.getDisplayName());
			}
		} catch (IllegalAccessException iae) {
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	private void loadUser() throws IllegalAccessException {
		if (personId == null) {
			user = SecurityUtil.getUser();
		} else if (securityService.isPersonAccessibleByCurrentPerson(personId)) {
			user = userService.loadUserByPersonId(personId);
		} else {
			throw new IllegalAccessException("You do not have access to the requested user's homepage.");
		}
	}
	
	public User getUser() {
		return user;
	}
	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	
	public String getUsersName() {
		//only return the users name if it is different from the logged in user
		if (user != null && !SecurityUtil.getUser().getId().equals(user.getId())) {
			return user.getPerson().getFirstAndLastName();
		}
		return null;
	}
	
	public String getCaseloadSortBys() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(CaseloadSortBy.values());
	}
	
	public boolean hasRole(String roleTypeName) {
		if (StringUtils.isNotBlank(roleTypeName)) {
			return hasRole(RoleType.valueOf(roleTypeName));
		}
		return false;
	}
	
	private boolean hasRole(RoleType roleType) {
		return (user != null && user.getRoles().contains(roleType));
	}

	public boolean isUserInternal() {
		return (user != null && SecurityUtil.isUserInternal());
	}
}