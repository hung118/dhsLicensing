package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@MappedSuperclass
public class BaseInspectionNeededView extends BaseFacilityAlertView implements Serializable {

	@Id
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "LAST_ANNOUNCED_INSP_ID")
	private Long lastAnnouncedInspectionId;
	
	@Column(name = "LAST_ANNOUNCED_DATE")
	@Temporal(TemporalType.DATE)
	private Date lastAnnouncedInspectionDate;
	
	@Column(name = "LAST_UNANNOUNCED_INSP_ID")
	private Long lastUnannouncedInspectionId;
	
	@Column(name = "LAST_UNANNOUNCED_DATE")
	@Temporal(TemporalType.DATE)
	private Date lastUnannouncedInspectionDate;
	
	public BaseInspectionNeededView() {
		
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}
	
	public Long getLastAnnouncedInspectionId() {
		return lastAnnouncedInspectionId;
	}
	
	public void setLastAnnouncedInspectionId(Long lastAnnouncedInspectionId) {
		this.lastAnnouncedInspectionId = lastAnnouncedInspectionId;
	}

	public Date getLastAnnouncedInspectionDate() {
		return lastAnnouncedInspectionDate;
	}

	public void setLastAnnouncedInspectionDate(Date lastAnnouncedInspectionDate) {
		this.lastAnnouncedInspectionDate = lastAnnouncedInspectionDate;
	}
	
	public Long getLastUnannouncedInspectionId() {
		return lastUnannouncedInspectionId;
	}
	
	public void setLastUnannouncedInspectionId(Long lastUnannouncedInspectionId) {
		this.lastUnannouncedInspectionId = lastUnannouncedInspectionId;
	}

	public Date getLastUnannouncedInspectionDate() {
		return lastUnannouncedInspectionDate;
	}

	public void setLastUnannouncedInspectionDate(Date lastUnannouncedInspectionDate) {
		this.lastUnannouncedInspectionDate = lastUnannouncedInspectionDate;
	}
}