package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.ccl.model.Phone;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CaregiverView implements Serializable {

	public Long id;
	public String firstName;
	public String lastName;
	public String maidenName;
	public String aliases;
	public Address address;
	public Date birthday;
	public Phone phone;
	public String caregiverType;
	public String caregiverStatus;
	
	public CaregiverView(Long id, String firstName, String lastName, String maidenName, String aliases, String addressOne, String addressTwo,
			String city, String state, String zipCode, Date birthday, Phone phone, String caregiverType, String caregiverStatus) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.maidenName = maidenName;
		this.aliases = aliases;
		this.address = new AddressImpl(addressOne, addressTwo, city, state, zipCode);
		this.birthday = birthday;
		this.phone = phone;
		this.caregiverType = caregiverType;
		this.caregiverStatus = caregiverStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public String getCaregiverType() {
		return caregiverType;
	}

	public void setCaregiverType(String caregiverType) {
		this.caregiverType = caregiverType;
	}

	public String getCaregiverStatus() {
		return caregiverStatus;
	}

	public void setCaregiverStatus(String caregiverStatus) {
		this.caregiverStatus = caregiverStatus;
	}
}