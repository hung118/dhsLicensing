package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT_REFERRAL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Referral extends AbstractBaseEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "COMPLAINT_REFERRAL_SEQ")
	@SequenceGenerator(name = "COMPLAINT_REFERRAL_SEQ", sequenceName = "COMPLAINT_REFERRAL_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ID", nullable = false, updatable = false)
	private Complaint complaint;
	
	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "AGENCY_ID")
	@Fetch(FetchMode.JOIN)
	private PickListValue agency;
	
	@Column(name = "REFERRAL_DATE")
	@Temporal(TemporalType.DATE)
	private Date referralDate;
	
	public Referral() {
		
	}
	
	@Override
	public Long getPk() {
		return id;
	}
	
	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	
	@RequiredFieldValidator(message = "Agency is required.")
	public PickListValue getAgency() {
		return agency;
	}
	
	public void setAgency(PickListValue agency) {
		this.agency = agency;
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Referral date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Referral date is required.", shortCircuit = true)
		}
	)
	public Date getReferralDate() {
		return referralDate;
	}
	
	public void setReferralDate(Date referralDate) {
		this.referralDate = referralDate;
	}
}