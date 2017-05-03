package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "CITYSTATEZIPCOUNTY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location extends AbstractBaseEntity<Long> implements Serializable {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CITYSTATEZIPCOUNTY_SEQ")
	@SequenceGenerator(name = "CITYSTATEZIPCOUNTY_SEQ", sequenceName = "CITYSTATEZIPCOUNTY_SEQ")
	private Long id;
	
	@Column(name = "CITY", nullable = false)
	private String city;

	@Column(name = "STATE", nullable = false)
	private String state;

	@Column(name = "ZIPCODE", nullable = false)
	private String zipCode;

	@Column(name = "COUNTY", nullable = false)
	private String county;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION", nullable = false)
	@Fetch(FetchMode.JOIN)
	private Region region;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RRAGENCY", nullable = false)
	@Fetch(FetchMode.JOIN)
	private PickListValue rrAgency;
	
	public Location() {
		
	}
	
	@Override
	public Long getPk() {
		return id;
	}
	
	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@RequiredStringValidator(message = "City is required.")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@RequiredStringValidator(message = "State is required.")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@RequiredStringValidator(message = "Zip code is required.")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@RequiredStringValidator(message = "County is required.")
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	@RequiredFieldValidator(message = "Region is required.")
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	@RequiredFieldValidator(message = "R and R agency is required.")
	public PickListValue getRrAgency() {
		return rrAgency;
	}
	
	public void setRrAgency(PickListValue rrAgency) {
		this.rrAgency = rrAgency;
	}
}