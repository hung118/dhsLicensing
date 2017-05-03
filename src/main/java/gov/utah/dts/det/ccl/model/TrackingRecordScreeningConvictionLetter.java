package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.ScreeningConvictionLetterType;
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
@Table(name = "TRAC_REC_SCREENING_CONVICT_LTR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningConvictionLetter extends AbstractAuditableEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, updatable = false, nullable = false)
	@SequenceGenerator(name = "TRS_CONVICT_LTR_SEQ", sequenceName = "TRS_CONVICT_LTR_SEQ")
	@GeneratedValue(generator = "TRS_CONVICT_LTR_SEQ", strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "TRS_ID", insertable = false, updatable = false)
	private Long trsId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRS_ID")
	private TrackingRecordScreening trackingRecordScreening;

	@Column(name = "LETTER_TYPE")
	@Type(type = "ScreeningConvictionLetterType")
	private ScreeningConvictionLetterType letterType;

	@Column(name = "LETTER_DATE")
	@Temporal(TemporalType.DATE)
	private Date letterDate;

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

	public Long getTrsId() {
		return trsId;
	}
	
	public void setTrsId(Long trsId) {
		this.trsId = trsId;
	}
	
	public Date getLetterDate() {
		return letterDate;
	}

	public void setLetterDate(Date letterDate) {
		this.letterDate = letterDate;
	}

	public ScreeningConvictionLetterType getLetterType() {
		return letterType;
	}

	public void setLetterType(ScreeningConvictionLetterType letterType) {
		this.letterType = letterType;
	}

	public TrackingRecordScreening getTrackingRecordScreening() {
		return trackingRecordScreening;
	}

	public void setTrackingRecordScreening(TrackingRecordScreening trackingRecordScreening) {
		this.trackingRecordScreening = trackingRecordScreening;
	}
	
}
