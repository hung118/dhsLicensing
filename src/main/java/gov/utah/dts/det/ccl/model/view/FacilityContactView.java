package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Phone;
import gov.utah.dts.det.ccl.model.enums.FacilityType;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITY_CONTACT_VIEW")
@Immutable
public class FacilityContactView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PERSON_ID")
	private Long personId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false)
	private Person contact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_TYPE")
	private PickListValue personType;

	@Column(name = "FACILITY_NAME")
	private String facilityName;
	
	@Column(name = "FACILITY_ID_NUMBER")
	private String facilityIdNumber;
	
	@Column(name = "INITIAL_REGULATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date initialRegulationDate;
	
	@Column(name = "FACILITY_TYPE")
	@Type(type = "FacilityType")
	private FacilityType facilityType;

	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "FACILITY_PRIMARY_PHONE"))
	private Phone primaryPhone;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "addressOne", column = @Column(name = "FACILITY_LOC_ADDRESS_ONE")),
		@AttributeOverride(name = "addressTwo", column = @Column(name = "FACILITY_LOC_ADDRESS_TWO")),
		@AttributeOverride(name = "city", column = @Column(name = "FACILITY_LOC_CITY")),
		@AttributeOverride(name = "state", column = @Column(name = "FACILITY_LOC_STATE")),
		@AttributeOverride(name = "zipCode", column = @Column(name = "FACILITY_LOC_ZIP_CODE")),
		@AttributeOverride(name = "county", column = @Column(name = "FACILITY_LOC_COUNTY"))
	})
	private EmbeddableAddress locationAddress;
	
	public FacilityContactView() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public PickListValue getPersonType() {
		return personType;
	}

	public void setPersonType(PickListValue personType) {
		this.personType = personType;
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
	
	public Date getInitialRegulationDate() {
		return initialRegulationDate;
	}

	public void setInitialRegulationDate(Date initialRegulationDate) {
		this.initialRegulationDate = initialRegulationDate;
	}
	
	public FacilityType getFacilityType() {
		return facilityType;
	}
	
	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}
	
	public Phone getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(Phone primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public EmbeddableAddress getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(EmbeddableAddress locationAddress) {
		this.locationAddress = locationAddress;
	}
	
	public Person getContact() {
		return contact;
	}
	
	public void setContact(Person contact) {
		this.contact = contact;
	}
}