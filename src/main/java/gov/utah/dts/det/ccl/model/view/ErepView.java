package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "EREP_VIEW")
@Immutable
public class ErepView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "LICENSE_TYPE")
	private String licenseType;
	
	@Column(name = "FACILITY_NAME")
	private String facilityName;
	
	@Column(name = "PRIMARY_PHONE")
	private String primaryPhone;
	
	@Column(name = "ADDRESS_ONE")
	private String addressOne;
	
	@Column(name = "ADDRESS_TWO")
	private String addressTwo;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name = "ADULT_TOTAL_SLOTS")
	private Integer adultTotalSlots;

	@Column(name = "YOUTH_TOTAL_SLOTS")
	private Integer youthTotalSlots;

	@Column(name = "EXPIRATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	@Column(name = "LICENSOR_FIRST_NAME")
	private String licensorFirstName;
	
	@Column(name = "LICENSOR_LAST_NAME")
	private String licensorLastName;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;
	
	public ErepView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
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

	public Integer getAdultTotalSlots() {
		return adultTotalSlots;
	}

	public void setAdultTotalSlots(Integer adultTotalSlots) {
		this.adultTotalSlots = adultTotalSlots;
	}

	public Integer getYouthTotalSlots() {
		return youthTotalSlots;
	}

	public void setYouthTotalSlots(Integer youthTotalSlots) {
		this.youthTotalSlots = youthTotalSlots;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getLicensorFirstName() {
		return licensorFirstName;
	}

	public void setLicensorFirstName(String licensorFirstName) {
		this.licensorFirstName = licensorFirstName;
	}

	public String getLicensorLastName() {
		return licensorLastName;
	}

	public void setLicensorLastName(String licensorLastName) {
		this.licensorLastName = licensorLastName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}