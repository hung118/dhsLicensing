package gov.utah.dts.det.ccl.service.impl;

public class UnlicensedComplaintSearchCriteria {

	private String ownerFirstName;
	private String ownerLastName;
	
	private String facilityName;
	private String address;
	private String city;
	private String county;
	private String zipCode;
	private String phone;
	
	public UnlicensedComplaintSearchCriteria(String ownerFirstName, String ownerLastName, String facilityName, String address, String city,
			String county, String zipCode, String phone) {
		this.ownerFirstName = ownerFirstName;
		this.ownerLastName = ownerLastName;
		this.facilityName = facilityName;
		this.address = address;
		this.city = city;
		this.county = county;
		this.zipCode = zipCode;
		this.phone = phone;
	}
	
	public String getOwnerFirstName() {
		return ownerFirstName;
	}
	
	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}
	
	public String getOwnerLastName() {
		return ownerLastName;
	}
	
	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}
	
	public String getFacilityName() {
		return facilityName;
	}
	
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
}