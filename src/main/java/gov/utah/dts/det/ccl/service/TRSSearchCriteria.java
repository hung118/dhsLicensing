package gov.utah.dts.det.ccl.service;

import java.util.Date;

/**
 * 
 * @author jtorres
 * 
 */
public class TRSSearchCriteria {

	private String firstName;
	private String lastName;
	private Date birthday;
	private String ssnLastFour;
	
	public TRSSearchCriteria() {
		
	}

	public TRSSearchCriteria(String firstName, String lastName, Date birthday, String ssnLastFour) {
		this.firstName = firstName;
		this.lastName = lastName;
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

}
