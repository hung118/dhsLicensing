package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "REGION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Region extends AbstractBaseEntity<Long> implements Serializable, Comparable<Region> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "REGION_SEQ")
	@SequenceGenerator(name = "REGION_SEQ", sequenceName = "REGION_SEQ")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS_ID")
	private Address address;
	
	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "PHONE"))
	private Phone phone;
	
	@Embedded
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "FAX"))
	private Phone fax;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFFICE_SPECIALIST_ID")
	private Person officeSpecialist;
	
	@ManyToMany
	@JoinTable(name = "REGION_LICENSING_SPECIALIST",
		joinColumns = @JoinColumn(name = "REGION_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "SPECIALIST_ID", referencedColumnName = "ID")
	)
	private Set<Person> licensingSpecialists = new HashSet<Person>();
	
	public Region() {
		
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

	@RequiredStringValidator(message = "Region name is required.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@ConversionErrorFieldValidator(message = "Phone number is not a valid phone number. ((###) ### - ####)", shortCircuit = true)
	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}


	@ConversionErrorFieldValidator(message = "Fax number is not a valid phone number. ((###) ### - ####)", shortCircuit = true)
	public Phone getFax() {
		return fax;
	}

	public void setFax(Phone fax) {
		this.fax = fax;
	}

	//@RequiredFieldValidator(message = "Office specialist is required.")
	public Person getOfficeSpecialist() {
		return officeSpecialist;
	}

	public void setOfficeSpecialist(Person officeSpecialist) {
		this.officeSpecialist = officeSpecialist;
	}

	public Set<Person> getLicensingSpecialists() {
		return licensingSpecialists;
	}

	public void setLicensingSpecialists(Set<Person> licensingSpecialists) {
		this.licensingSpecialists = licensingSpecialists;
	}
	
	@Override
	public int compareTo(Region o) {
		if (this == o) {
			return 0;
		}
		
		return CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
	}
}