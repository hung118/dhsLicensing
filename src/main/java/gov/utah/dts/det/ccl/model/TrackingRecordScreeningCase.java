package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TRAC_REC_SCREENING_CASE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningCase extends AbstractAuditableEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, updatable = false, nullable = false)
	@SequenceGenerator(name = "TRS_CASE_SEQ", sequenceName = "TRS_CASE_SEQ")
	@GeneratedValue(generator = "TRS_CASE_SEQ", strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRS_ID")
	private TrackingRecordScreening trackingRecordScreening;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CASE_TYPE_ID")
	private PickListValue caseType;

	@Column(name = "CASE_NUMBER")
	private String caseNumber;

	@Column(name = "CASE_DATE")
	@Temporal(TemporalType.DATE)
	private Date caseDate;

	@Column(name = "DETAIL")
	private String detail;

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

	public PickListValue getCaseType() {
		return caseType;
	}

	public void setCaseType(PickListValue caseType) {
		this.caseType = caseType;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public Date getCaseDate() {
		return caseDate;
	}

	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public TrackingRecordScreening getTrackingRecordScreening() {
		return trackingRecordScreening;
	}

	public void setTrackingRecordScreening(TrackingRecordScreening trackingRecordScreening) {
		this.trackingRecordScreening = trackingRecordScreening;
	}
	
	public String getShortDetail() {
		if (detail != null && detail.length() >= 60) {
			return detail.substring(0,60);
		}
		return detail;
	}
}
