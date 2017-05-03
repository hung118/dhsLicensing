package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.view.Address;
import gov.utah.dts.det.ccl.view.MailingLabel;

import java.io.Serializable;
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
@Table(name = "ALERT_EXP_EXPIRING_LIC_VIEW")
@Immutable
public class ExpiredAndExpiringLicenseView implements Serializable, MailingLabel {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_ID", insertable = false, updatable = false)
    private BasicFacilityView facility;

	@Column(name = "LICENSE_EXPIRATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date licenseExpirationDate;
	
	@Column(name = "SERVICE_CODE")
	private String serviceCode;
	
	@Column(name = "APPLICATION_RECEIVED_DATE")
	@Temporal(TemporalType.DATE)
	private Date applicationReceivedDate;
	
	@ManyToOne
	@JoinColumn(name = "APPLICATION_RECEIVED_ACTION_ID")
	private PickListValue applicationReceivedAction;
	
	public ExpiredAndExpiringLicenseView() {
		
	}
	
	public Long getId() {
	    return id;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}

	public Long getFacilityId() {
		return facilityId;
	}
	
	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}
	
	public BasicFacilityView getFacility() {
	    return facility;
	}
	
	public void setFacility(BasicFacilityView facility) {
	    this.facility = facility;
	}

	public Date getLicenseExpirationDate() {
		return licenseExpirationDate;
	}

	public void setLicenseExpirationDate(Date licenseExpirationDate) {
		this.licenseExpirationDate = licenseExpirationDate;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Date getApplicationReceivedDate() {
		return applicationReceivedDate;
	}

	public void setApplicationReceivedDate(Date applicationReceivedDate) {
		this.applicationReceivedDate = applicationReceivedDate;
	}
	
	public PickListValue getApplicationReceivedAction() {
		return applicationReceivedAction;
	}
	
	public void setApplicationReceivedAction(PickListValue applicationReceivedAction) {
		this.applicationReceivedAction = applicationReceivedAction;
	}

	@Override
	public String getName() {
	    return facility.getName();
	}

	@Override
	public Address getAddress() {
	    return facility.getMailingAddress();
	}
}