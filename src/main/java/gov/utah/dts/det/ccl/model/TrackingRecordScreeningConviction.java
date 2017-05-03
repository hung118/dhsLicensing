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
@Table(name = "TRAC_REC_SCREENING_CONVICTION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningConviction extends AbstractAuditableEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, updatable = false, nullable = false)
	@SequenceGenerator(name = "TRS_CONVICTION_SEQ", sequenceName = "TRS_CONVICTION_SEQ")
	@GeneratedValue(generator = "TRS_CONVICTION_SEQ", strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRS_ID")
	private TrackingRecordScreening trackingRecordScreening;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONVICTION_TYPE_ID")
	private PickListValue convictionType;

	@Column(name = "CONVICTION_DESC")
	private String convictionDesc;

	@Column(name = "CONVICTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date convictionDate;

	@Column(name = "DISMISSED")
	@Type(type = "yes_no")
	private Boolean dismissed = false;

	@Column(name = "COURT_INFO")
	private String courtInfo;

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

	public Date getConvictionDate() {
		return convictionDate;
	}

	public void setConvictionDate(Date convictionDate) {
		this.convictionDate = convictionDate;
	}

	public String getConvictionDesc() {
		return convictionDesc;
	}

	public void setConvictionDesc(String convictionDesc) {
		this.convictionDesc = convictionDesc;
	}

	public PickListValue getConvictionType() {
		return convictionType;
	}

	public void setConvictionType(PickListValue convictionType) {
		this.convictionType = convictionType;
	}

	public String getCourtInfo() {
		return courtInfo;
	}

	public void setCourtInfo(String courtInfo) {
		this.courtInfo = courtInfo;
	}

	public boolean isDismissed() {
		return dismissed;
	}

	public void setDismissed(boolean dismissed) {
		this.dismissed = dismissed;
	}

	public TrackingRecordScreening getTrackingRecordScreening() {
		return trackingRecordScreening;
	}

	public void setTrackingRecordScreening(TrackingRecordScreening trackingRecordScreening) {
		this.trackingRecordScreening = trackingRecordScreening;
	}
	
	public String getShortConvictionDesc() {
		if (convictionDesc != null && convictionDesc.length() >= 30) {
			return convictionDesc.substring(0,30);
		}
		return convictionDesc;
	}

	public String getShortCourtInfo() {
		if (courtInfo != null && courtInfo.length() >= 60) {
			return courtInfo.substring(0,60);
		}
		return courtInfo;
	}
}
