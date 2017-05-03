package gov.utah.dts.det.ccl.service;

import java.util.Date;

public class CaregiverSearchCriteria {

	private String firstName;
	private String middleName;
	private String lastName;
	private String alias;
	private Date birthday;
	private Long typeId;
	private Long statusId;
	
	public CaregiverSearchCriteria(String firstName, String middleName, String lastName, String alias, Date birthday, Long typeId,
			Long statusId) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.alias = alias;
		this.birthday = birthday;
		this.typeId = typeId;
		this.statusId = statusId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Long getTypeId() {
		return typeId;
	}
	
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public Long getStatusId() {
		return statusId;
	}
	
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
}