package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.enums.RoleType;
import java.util.ArrayList;
import java.util.List;

public class UserSearchCriteria {

private Long personId;
private String name;
private Boolean active;
List<RoleType> roleTypes = new ArrayList<RoleType>();
	
	public UserSearchCriteria() {
		
	}
	
	public UserSearchCriteria(Long personId, String name, Boolean active, List<RoleType> roleTypes) {
		this.personId = personId;
		this.name = name;
		this.active = active;
		this.roleTypes = roleTypes;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<RoleType> getRoleTypes() {
		return roleTypes;
	}

	public void setRoleTypes(List<RoleType> roleTypes) {
		this.roleTypes = roleTypes;
	}

}