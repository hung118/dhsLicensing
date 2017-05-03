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
import org.hibernate.annotations.Type;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TRAC_REC_SCREENING_LTR15")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningLtr15 extends AbstractAuditableEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@SequenceGenerator(name = "TRS_LETTERS15_SEQ", sequenceName = "TRS_LETTERS15_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TRS_LETTERS15_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRS_ID")
	private TrackingRecordScreening trackingRecordScreening;

	@Column(name = "ISSUED_DATE")
	@Temporal(TemporalType.DATE)
	private Date issuedDate;

	@Column(name = "DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REASON_ID")
	private PickListValue reason;

	@Column(name = "RESOLVED")
	@Type(type = "yes_no")
	private Boolean resolved = false;

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

	public void setTrackingRecordScreening(TrackingRecordScreening screening) {
		this.trackingRecordScreening = screening;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public PickListValue getReason() {
		return reason;
	}

	public void setReason(PickListValue reason) {
		this.reason = reason;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

}
