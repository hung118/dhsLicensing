package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.ccl.model.Phone;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class FacilityResultView implements Serializable {

	private Long id;
	private String facilityName;
	private String facilityIdNumber;
	private String primaryPhone;
	private String addressOne;
	private String addressTwo;
	private String city;
	private String state;
	private String zipCode;
	
	public FacilityResultView(Long id, String facilityName, String facilityIdNumber, Phone primaryPhone, String addressOne, String addressTwo,
			String city, String state, String zipCode) {
		this.id = id;
		this.facilityName = facilityName;
		this.facilityIdNumber = facilityIdNumber;
		if(primaryPhone != null) {
			this.primaryPhone = primaryPhone.getFormattedPhoneNumber();
		}
		this.addressOne = addressOne;
		this.addressTwo = addressTwo;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public FacilityResultView(Long id, String facilityName, String facilityIdNumber, Phone primaryPhone, String addressOne, String addressTwo,
			String city, String state, String zipCode, String altAddressOne, String altAddressTwo, String altCity, String altState, String altZipCode) {
		this.id = id;
		this.facilityName = facilityName;
		this.facilityIdNumber = facilityIdNumber;
		if(primaryPhone != null) {
			this.primaryPhone = primaryPhone.getFormattedPhoneNumber();
		}

		if (addressOne != null && !StringUtils.isBlank(addressOne)) {
			this.addressOne = addressOne;
			this.addressTwo = addressTwo;
			this.city = city;
			this.state = state;
			this.zipCode = zipCode;
		} else {
			this.addressOne = altAddressOne;
			this.addressTwo = altAddressTwo;
			this.city = altCity;
			this.state = altState;
			this.zipCode = altZipCode;
		}
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFacilityName() {
		return facilityName;
	}
	
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
	public String getFacilityIdNumber() {
		return facilityIdNumber;
	}
	
	public void setFacilityIdNumber(String facilityIdNumber) {
		this.facilityIdNumber = facilityIdNumber;
	}
	
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	
	public String getAddressOne() {
		return addressOne;
	}
	
	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}
	
	public String getAddressTwo() {
		return addressTwo;
	}
	
	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}