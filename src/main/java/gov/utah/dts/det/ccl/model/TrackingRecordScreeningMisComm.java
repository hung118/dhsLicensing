package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractAuditableEntity;

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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TRAC_REC_SCREENING_MISCOMM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningMisComm extends AbstractAuditableEntity<Long> implements Serializable {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "MIS_COMM_DATE")
	@Temporal(TemporalType.DATE)
	private Date misCommDate;

	@Column(name = "MIS_COMM_DECISION")
	private String misCommDecision;

	@Column(name = "OAH_REQUEST_DATE")
	@Temporal(TemporalType.DATE)
	private Date oahRequestDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OAH_DECISION_ID")
	private PickListValue oahDecision;

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

	public Date getMisCommDate() {
		return misCommDate;
	}

	public void setMisCommDate(Date misCommDate) {
		this.misCommDate = misCommDate;
	}

	public String isMisCommDecision() {
		return misCommDecision;
	}

	public void setMisCommDecision(String misCommDecision) {
		this.misCommDecision = misCommDecision;
	}

	public Date getOahRequestDate() {
		return oahRequestDate;
	}

	public void setOahRequestDate(Date oahRequestDate) {
		this.oahRequestDate = oahRequestDate;
	}

	public PickListValue getOahDecision() {
		return oahDecision;
	}

	public void setOahDecision(PickListValue oahDecision) {
		this.oahDecision = oahDecision;
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
	    return misCommDate != null || oahRequestDate != null || StringUtils.isNotBlank(misCommDecision) ||
	            (oahDecision != null && oahDecision.getId() != null);
	}

}
