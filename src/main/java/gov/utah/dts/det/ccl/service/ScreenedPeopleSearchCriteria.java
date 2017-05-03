package gov.utah.dts.det.ccl.service;

import java.util.Date;

public class ScreenedPeopleSearchCriteria {

	private String firstName;
	private String lastName;
	private String maidenName;
	private Date birthday;
	private String ssnLastFour;
	private String ssnLastFourHash;
	
	public ScreenedPeopleSearchCriteria(String firstName, String lastName, String maidenName, Date birthday, String ssnLastFour) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.maidenName = maidenName;
		this.birthday = birthday;
		this.ssnLastFour = ssnLastFour;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getMaidenName() {
		return maidenName;
	}
	
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getSsnLastFour() {
		return ssnLastFour;
	}
	
	public void setSsnLastFour(String ssnLastFour) {
		this.ssnLastFour = ssnLastFour;
	}
	
	public String getSsnLastFourHash() {
		return ssnLastFourHash;
	}
	
	public void setSsnLastFourHash(String ssnLastFourHash) {
		this.ssnLastFourHash = ssnLastFourHash;
	}
}