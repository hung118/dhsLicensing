package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.USState;
import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * 
 * @author jtorres
 * 
 */
@Entity
@Table(name = "TRAC_REC_SCREENING_OSCAR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuppressWarnings("serial")
public class TrackingRecordScreeningOscar extends AbstractAuditableEntity<Long>
		implements Serializable {

	@Id
	@Column(name = "ID", updatable = false, unique = true, nullable = false)
	@SequenceGenerator(name = "TRS_OSCAR_SEQ", sequenceName = "TRS_OSCAR_SEQ")
	@GeneratedValue(generator = "TRS_OSCAR_SEQ", strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRS_ID")
	private TrackingRecordScreening trackingRecordScreening;

	@Column(name = "STATE")
	@Type(type = "USState")
	private USState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OSCAR_TYPE_ID")
	private PickListValue oscarType;

	@Column(name = "CASE_NUMBER")
	private String caseNumber;

	@Column(name = "OSCAR_DATE")
	@Temporal(TemporalType.DATE)
	private Date oscarDate;

	@Column(name = "NOTES")
	private String notes;

	@Override
	public Long getPk() {
		return id;
	}

	@Override
	public void setPk(Long pk) {
		id = pk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TrackingRecordScreening getTrackingRecordScreening() {
		return trackingRecordScreening;
	}

	public void setTrackingRecordScreening(
			TrackingRecordScreening trackingRecordScreening) {
		this.trackingRecordScreening = trackingRecordScreening;
	}

	public USState getState() {
		return state;
	}

	public void setState(USState state) {
		this.state = state;
	}

	public PickListValue getOscarType() {
		return oscarType;
	}

	public void setOscarType(PickListValue oscarType) {
		this.oscarType = oscarType;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public Date getOscarDate() {
		return oscarDate;
	}

	public void setOscarDate(Date oscarDate) {
		this.oscarDate = oscarDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	   /**
     * Check to see whether the object contains any data.
     * @return true if any object attribute is non-null, false otherwise.
     */
    public boolean hasData() {
        return state != null || oscarDate != null || StringUtils.isNotBlank(caseNumber) || StringUtils.isNotBlank(notes) ||
                (oscarType != null && oscarType.getId() != null);
    }

}
