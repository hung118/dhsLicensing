package gov.utah.dts.det.ccl.service;

public class UserCaseloadCount {

	private Long id;
	private String name;
	private String roleType;
	private Boolean active;
	private Long count;
	
//	public UserCaseloadCount(Long id, String name, String roleType, Boolean active, Long count) {
//		this.id = id;
//		this.name = name;
//		this.roleType = roleType;
//		this.active = active;
//		this.count = count;
//	}
	
	public UserCaseloadCount() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRoleType() {
		return roleType;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}