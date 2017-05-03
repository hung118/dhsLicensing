package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Phone;
import gov.utah.dts.det.ccl.model.Region;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "BASIC_FACILITY_VIEW")
@Immutable
public class BasicFacilityView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FACILITY_NAME")
	private String name;
	
	@Column(name = "STATUS")
	private FacilityStatus status;
	
	@AttributeOverrides({
		@AttributeOverride(name = "addressOne", column = @Column(name = "LOC_ADDRESS_ONE")),
		@AttributeOverride(name = "addressTwo", column = @Column(name = "LOC_ADDRESS_TWO")),
		@AttributeOverride(name = "city", column = @Column(name = "LOC_CITY")),
		@AttributeOverride(name = "state", column = @Column(name = "LOC_STATE")),
		@AttributeOverride(name = "zipCode", column = @Column(name = "LOC_ZIP_CODE")),
		@AttributeOverride(name = "county", column = @Column(name = "LOC_ZIP_CODE", insertable = false, updatable = false))
	})
	private EmbeddableAddress locationAddress;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "addressOne", column = @Column(name = "MAIL_ADDRESS_ONE")),
		@AttributeOverride(name = "addressTwo", column = @Column(name = "MAIL_ADDRESS_TWO")),
		@AttributeOverride(name = "city", column = @Column(name = "MAIL_CITY")),
		@AttributeOverride(name = "state", column = @Column(name = "MAIL_STATE")),
		@AttributeOverride(name = "zipCode", column = @Column(name = "MAIL_ZIP_CODE")),
		@AttributeOverride(name = "county", column = @Column(name = "MAIL_ZIP_CODE", insertable = false, updatable = false))
	})
	private EmbeddableAddress mailingAddress;
	
	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "PRIMARY_PHONE"))
	private Phone primaryPhone;
	
	@ManyToOne
	@JoinColumn(name = "REGION_ID")
	private Region region;
	
	@ManyToOne
	@JoinColumn(name = "LICENSING_SPECIALIST_ID")
	private Person licensingSpecialist;
	
	public BasicFacilityView() {
		
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
	
	public FacilityStatus getStatus() {
		return status;
	}
	
	public void setStatus(FacilityStatus status) {
		this.status = status;
	}
	
	public EmbeddableAddress getLocationAddress() {
		return locationAddress;
	}
	
	public void setLocationAddress(EmbeddableAddress locationAddress) {
		this.locationAddress = locationAddress;
	}
	
	public EmbeddableAddress getMailingAddress() {
		return mailingAddress;
	}
	
	public void setMailingAddress(EmbeddableAddress mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	
	public Phone getPrimaryPhone() {
		return primaryPhone;
	}
	
	public void setPrimaryPhone(Phone primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Person getLicensingSpecialist() {
		return licensingSpecialist;
	}
	
	public void setLicensingSpecialist(Person licensingSpecialist) {
		this.licensingSpecialist = licensingSpecialist;
	}

}