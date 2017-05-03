package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.dao.DataIntegrityViolationException;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import gov.utah.dts.det.ccl.actions.CclAction;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/accounts")
@Results({
	@Result(name = "redirect-view", location = "accounts-list", type = "redirectAction"),
	@Result(name = "input", location = "account_form.jsp"),
	@Result(name = "view", location = "accounts_list.jsp")
})
public class AccountsAction extends ActionSupport implements Preparable, CclAction {

	private UserService userService;
	
	private User user;
	private List<RoleType> formRoles;
	
	private Map<String, List<User>> roleUserMap;
	
	@Override
	public void prepare() throws Exception {
		if (user != null && user.getId() != null) {
			user = userService.loadById(user.getId());
		}
	}
	
	@Action(value = "tab", results = {
		@Result(name = "success", location = "accounts_tab.jsp")
	})
	public String doTab() {
		return SUCCESS;
	}
	
	@Action(value = "accounts-list")
	public String doList() {
		//create the map of roles to lists of users
		roleUserMap = new LinkedHashMap<String, List<User>>();
		roleUserMap.put("NONE", new ArrayList<User>());
		for (RoleType roleType : RoleType.values()) {
			roleUserMap.put(roleType.getDisplayName(), new ArrayList<User>());
		}
		
		//place the users in all maps they have the role for
		Set<User> allUsers = userService.getAllUsers();
		for (User user : allUsers) {
			if (user.getRoles() == null || user.getRoles().isEmpty()) {
				List<User> usersWithNoRoles = roleUserMap.get("NONE");
				usersWithNoRoles.add(user);
			} else {
				for (RoleType role : user.getRoles()) {
					List<User> usersInRole = roleUserMap.get(role.getDisplayName());
					usersInRole.add(user);
				}
			}
		}
		
		return VIEW;
	}
	
	@Action(value = "edit-account")
	public String doEdit() {
		if (user != null) {
			formRoles = new ArrayList<RoleType>();
			for (RoleType role : user.getRoles()) {
				formRoles.add(role);
			}
		}
		
		return INPUT;
	}
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "user.person.firstName", message = "First name is required."),
			@RequiredStringValidator(fieldName = "user.person.lastName", message = "Last name is required."),
			@RequiredStringValidator(fieldName = "user.person.address.addressOne", message = "Address one is required."),
			@RequiredStringValidator(fieldName = "user.person.address.city", message = "City is required."),
			@RequiredStringValidator(fieldName = "user.person.address.state", message = "State is required."),
			@RequiredStringValidator(fieldName = "user.person.address.zipCode", message = "Zip code is required.")
		}
	)
	@Action(value = "save-account")
	public String doSave() {
		if (user.getId() == null) {
			user.setActive(true);
		}
		
		user.getPerson().setEmail(user.getUsername());
		
		manageRoles();
		try {
			userService.saveUser(user);
		} catch (DataIntegrityViolationException dive) {
			addFieldError("user.username", "Username is already being used.");
			return INPUT;
		}
		
		return REDIRECT_VIEW;
	}
	
	@Action(value = "deactivate-account")
	public String doDeactivate() {
		userService.deactivateUser(user);
		
		return REDIRECT_VIEW;
	}
	
	@Action(value = "activate-account")
	public String doActivate() {
		userService.activateUser(user);
		
		return REDIRECT_VIEW;
	}
	
	private void manageRoles() {
		if (formRoles == null || formRoles.isEmpty()) {
			user.getRoles().clear();
		} else {
			Set<RoleType> formRolesSet = new HashSet<RoleType>(formRoles);
			for (Iterator<RoleType> itr = user.getRoles().iterator(); itr.hasNext();) {
				RoleType role = itr.next();
				//if the form roles didn't contain the user's role, then remove it
				if (!formRolesSet.remove(role)) {
					itr.remove();
				}
			}
			//any roles left in the form roles set are new.  Add them to the user.
			for (RoleType roleType : formRolesSet) {
				user.getRoles().add(roleType);
			}
		}
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@VisitorFieldValidator(message = "&zwnj;")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<RoleType> getFormRoles() {
		return formRoles;
	}
	
	public void setFormRoles(List<RoleType> formRoles) {
		this.formRoles = formRoles;
	}
	
	public Map<String, List<User>> getRoleUserMap() {
		return roleUserMap;
	}
	
	public List<RoleType> getAvailableRoles() {
		return Arrays.asList(RoleType.values());
	}
}