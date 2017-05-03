package gov.utah.dts.det.ccl.model;

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

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TRAC_REC_SCREENING_REQUESTS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningRequests extends AbstractAuditableEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@SequenceGenerator(name = "TRS_REQUESTS_SEQ", sequenceName = "TRS_REQUESTS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TRS_REQUESTS_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRS_ID")
	private TrackingRecordScreening trackingRecordScreening;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "FROM_DATE")
	@Temporal(TemporalType.DATE)
	private Date fromDate;

	@Column(name = "TO_DATE")
	@Temporal(TemporalType.DATE)
	private Date toDate;
	
	@Column(name = "RECEIVED_DATE")
	@Temporal(TemporalType.DATE)
	private Date receivedDate;
	
	@Column(name = "OC_DOCUMENT")
	private String ocDocument;

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

	public void setTrackingRecordScreening(TrackingRecordScreening trackingRecordScreening) {
		this.trackingRecordScreening = trackingRecordScreening;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getOcDocument() {
		return ocDocument;
	}

	public void setOcDocument(String ocDocument) {
		this.ocDocument = ocDocument;
	}
	
    /**
     * Check to see whether the object contains any data.
     * @return true if any object attribute is non-null, false otherwise.
     */
    public boolean hasData() {
        return fromDate != null || toDate != null || receivedDate != null || StringUtils.isNotBlank(country) || StringUtils.isNotBlank(ocDocument);
    }
}
