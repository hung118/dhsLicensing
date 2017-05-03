package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Phone;
import gov.utah.dts.det.ccl.model.enums.FacilityType;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITY_CASELOAD_VIEW")
@Immutable
public class FacilityCaseloadView implements Serializable {

	@Id
	@Column(name = "ID", updatable=false, insertable=false)
	private Long id;
	
	@Column(name = "FACILITY_ID_NUMBER")
	private String idNumber;
	
	@Column(name = "FACILITY_NAME")
	private String name;
	
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
	@AttributeOverride(name = "phoneNumber", column = @Column(name = "PRIMARY_PHONE"))
	private Phone primaryPhone;
	
	@Column(name = "STATUS")
	private FacilityStatus status;
	
	@ManyToOne
	@JoinColumn(name = "LICENSING_SPECIALIST_ID")
	private Person licensingSpecialist;
		
	@Column(name = "LICENSE_TYPE_ID")
	@Type(type = "FacilityType")
	private FacilityType licenseType;
	
	@Column(name = "DIRECTOR_NAMES")
	private String directorNames;
	
	@OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@Sort(type = SortType.NATURAL)
	private SortedSet<License> licenses = new TreeSet<License>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EmbeddableAddress getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(EmbeddableAddress locationAddress) {
		this.locationAddress = locationAddress;
	}

	public Phone getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(Phone primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public FacilityStatus getStatus() {
		return status;
	}

	public void setStatus(FacilityStatus status) {
		this.status = status;
	}

	public Person getLicensingSpecialist() {
		return licensingSpecialist;
	}

	public void setLicensingSpecialist(Person licensingSpecialist) {
		this.licensingSpecialist = licensingSpecialist;
	}

	public String getDirectorNames() {
		return directorNames;
	}

	public void setDirectorNames(String directorNames) {
		this.directorNames = directorNames;
	}
	
	public SortedSet<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(SortedSet<License> licenses) {
		this.licenses = licenses;
	}

	public FacilityType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(FacilityType licenseType) {
		this.licenseType = licenseType;
	}
	
	public List<License> getActiveLicenses() {
		List<License> list = new ArrayList<License>();
		for (Iterator<License> itr = getLicenses().iterator(); itr.hasNext();) {
			License l = itr.next();
			if (l.getStatus() != null && l.getStatus().getValue().equalsIgnoreCase("Active")) {
				list.add(l);
			}
		}

		return list;
	}
	
}