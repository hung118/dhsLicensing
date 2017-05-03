package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.ScreeningLetterType;
import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "TRAC_REC_SCREENING_LETTER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrackingRecordScreeningLetter extends AbstractAuditableEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TRS_LETTER_SEQ")
	@SequenceGenerator(name = "TRS_LETTER_SEQ", sequenceName = "TRS_LETTER_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRS_ID")
	private TrackingRecordScreening trackingRecordScreening;

	@Column(name = "LETTER_TYPE")
	@Enumerated(EnumType.STRING)
	private ScreeningLetterType letterType;

	@Column(name = "LETTER_DATE")
	@Temporal(TemporalType.DATE)
	private Date letterDate;

	@Column(name = "FPI_DETAILS")
	private String details;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS_ID")
	private Address address;

	@Override
	public Long getPk() {
		return id;
	}

	@Override
	public void setPk(Long pk) {
		id = pk;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public Date getLetterDate() {
		return letterDate;
	}

	public void setLetterDate(Date letterDate) {
		this.letterDate = letterDate;
	}

	public ScreeningLetterType getLetterType() {
		return letterType;
	}

	public void setLetterType(ScreeningLetterType letterType) {
		this.letterType = letterType;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	   /**
     * Check to see whether the object contains any data.
     * @return true if any object attribute is non-null, false otherwise.
     */
    public boolean hasData() {
        return letterType != null || letterDate != null || StringUtils.isNotBlank(details) ||
                (address != null && 
                    (StringUtils.isNotBlank(address.getAddressOne()) || 
                     StringUtils.isNotBlank(address.getAddressTwo()) ||
                     StringUtils.isNotBlank(address.getCity()) || 
                     StringUtils.isNotBlank(address.getState()) || 
                     StringUtils.isNotBlank(address.getZipCode())
                    )
                );
    }

}
