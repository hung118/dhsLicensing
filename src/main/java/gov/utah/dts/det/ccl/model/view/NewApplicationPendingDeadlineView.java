package gov.utah.dts.det.ccl.model.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT_NEW_APP_PEND_DEAD_VIEW")
@Immutable
public class NewApplicationPendingDeadlineView extends BaseFacilityAlertView {

	@Id
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "APPLICATION_RECEIVED_DATE")
	@Temporal(TemporalType.DATE)
	private Date applicationReceivedDate;
	
	@Column(name = "LETTER_SENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date letterSentDate;
	
	@Column(name = "LICENSE_START_DATE")
	@Temporal(TemporalType.DATE)
	private Date licenseStartDate;
	
	public NewApplicationPendingDeadlineView() {
		
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public Date getApplicationReceivedDate() {
		return applicationReceivedDate;
	}

	public void setApplicationReceivedDate(Date applicationReceivedDate) {
		this.applicationReceivedDate = applicationReceivedDate;
	}
	
	public Date getLetterSentDate() {
		return letterSentDate;
	}
	
	public void setLetterSentDate(Date letterSentDate) {
		this.letterSentDate = letterSentDate;
	}
	
	public Date getLicenseStartDate() {
		return licenseStartDate;
	}
	
	public void setLicenseStartDate(Date licenseStartDate) {
		this.licenseStartDate = licenseStartDate;
	}
	
	public boolean isCloseFacility() {
		Date today = new Date();
		if (today.compareTo(DateUtils.addMonths(licenseStartDate, 6)) >= 0) {
			return true;
		}
		return false;
	}
}