package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.License;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.Region;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT_NONCOMPLIANCE_VIEW")
@Immutable
public class SeriousNoncomplianceView implements Serializable {

	@Id
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "license_id")
	private License license;
	
	@ManyToOne
	@JoinColumn(name = "REGION_ID")
	private Region region;
	
	@ManyToOne
	@JoinColumn(name = "LICENSING_SPECIALIST_ID")
	private Person licensingSpecialist;
	
	@Column(name = "TOTAL_POINTS")
	private Integer totalPoints;
	
	@Column(name = "TRIGGER_DATE")
	@Temporal(TemporalType.DATE)
	private Date triggerDate;
	
	public SeriousNoncomplianceView() {
		
	}
	
	public Long getFacilityId() {
		return facilityId;
	}
	
	public void setFacilityId(BigDecimal facilityId) {
		this.facilityId = facilityId.longValue();
	}
	
	public String getFacilityName() {
		return facilityName;
	}
	
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
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
	
	public Integer getTotalPoints() {
		return totalPoints;
	}
	
	public void setTotalPoints(BigDecimal totalPoints) {
		this.totalPoints = totalPoints.intValue();
	}
	
	public Date getTriggerDate() {
		return triggerDate;
	}
	
	public void setTriggerDate(Date triggerDate) {
		this.triggerDate = triggerDate;
	}
}