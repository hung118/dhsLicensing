package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.view.Address;
import gov.utah.dts.det.ccl.view.MailingLabel;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;

@SuppressWarnings("serial")
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AlertFollowUpsNeededView implements Serializable, MailingLabel {

	@Id
	@Column(name = "INSPECTION_ID")
	private Long inspectionId;

	@ManyToOne
	@JoinColumn(name = "FACILITY_ID", insertable = false, updatable = false)
	private BasicFacilityInformation facility;
	
	@ManyToOne
	@JoinColumn(name = "RECIPIENT_ID")
	private Person recipient;
	
	@Column(name = "INSPECTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date inspectionDate;
	
	@Column(name = "PRIMARY_TYPE")
	private String primaryType;
	
	@Column(name = "OTHER_TYPES")
	private String otherTypes;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSPECTION_ID")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<FindingFollowUpView> findings;

	private transient AlertType alertType;
	
	public Long getInspectionId() {
		return inspectionId;
	}
	
	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}
	
	public BasicFacilityInformation getFacility() {
		return facility;
	}
	
	public void setFacility(BasicFacilityInformation facility) {
		this.facility = facility;
	}
	
	public Person getRecipient() {
		return recipient;
	}
	
	public void setRecipient(Person recipient) {
		this.recipient = recipient;
	}
	
	public Date getInspectionDate() {
		return inspectionDate;
	}
	
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	
	public String getPrimaryType() {
		return primaryType;
	}
	
	public void setPrimaryType(String primaryType) {
		this.primaryType = primaryType;
	}
	
	public String getOtherTypes() {
		return otherTypes;
	}
	
	public void setOtherTypes(String otherTypes) {
		this.otherTypes = otherTypes;
	}
	
	public List<FindingFollowUpView> getFindings() {
		return findings;
	}
	
	public void setFindings(List<FindingFollowUpView> findings) {
		this.findings = findings;
	}
	
	@Override
	public String getName() {
		return facility.getName();
	}
	
	@Override
	public Address getAddress() {
		return facility.getMailingAddress();
	}
	
	public AlertType getAlertType() throws JSONException, ParseException {
		if (alertType == null) {
			alertType = AlertType.ALERT;
			Date now = new Date();
			Date latestCorrectionDate = null;
			for (FindingFollowUpView find : getFindings()) {
				if (latestCorrectionDate == null || latestCorrectionDate.before(find.getCorrectionDate())) {
					latestCorrectionDate = find.getCorrectionDate();
				}
			}
			Date expDt = getFacility().getLicenseExpirationDate();
//			if (corrDl == null) {
//				corrDl = DateUtils.addDays(inspectionDate, 30);
//			}
			if (expDt == null) {
				expDt = DateUtils.addDays(latestCorrectionDate, 60);
			}
			Date window = DateUtils.addDays(expDt, -30);
			
			//all inspections are required at least 7 days before the expiration date
			if (latestCorrectionDate.compareTo(window) >= 0 && now.compareTo(DateUtils.addDays(expDt, -14)) >= 0) {
				//if we had 30 days or less until the license expiration and we are within 14 days of the license expiration
				alertType = AlertType.RED_ALERT;
			} else if (latestCorrectionDate.compareTo(window) >= 0 && now.compareTo(DateUtils.addDays(expDt, -21)) >= 0) {
				//if we had 30 days or less until the license expiration and we are within 21 days of the license expiration
				alertType = AlertType.ORANGE_ALERT;
			} else if (now.compareTo(DateUtils.addDays(latestCorrectionDate, 21)) >= 0) {
				//if we had more than 30 days until the license expiration and we are over 21 days past the correction deadline
				alertType = AlertType.RED_ALERT;
			} else if (now.compareTo(DateUtils.addDays(latestCorrectionDate, 14)) >= 0) {
				//if we had more than 30 days until the license expiration and we are over 14 days past the correction deadline
				alertType = AlertType.ORANGE_ALERT;
			}
		}
		return alertType;
	}
}