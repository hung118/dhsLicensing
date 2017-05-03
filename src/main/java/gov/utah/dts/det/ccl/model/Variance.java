package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;
import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.Calendar;
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

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Entity
@Table(name = "VARIANCE")
@Conversion
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Variance extends AbstractAuditableEntity<Long> implements Serializable, Activatable, DateRange, BetweenDateRange {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "VARIANCE_SEQ")
	@SequenceGenerator(name = "VARIANCE_SEQ", sequenceName = "VARIANCE_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID")
	private Facility facility;
	
	@Column(name = "REQUEST_DATE")
	@Temporal(TemporalType.DATE)
	private Date requestDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RULE_ID")
	private RuleSubSection rule;
	
	@Column(name = "REQUESTED_START_DATE")
	@Temporal(TemporalType.DATE)
	private Date requestedStartDate;
	
	@Column(name = "REQUESTED_END_DATE")
	@Temporal(TemporalType.DATE)
	private Date requestedEndDate;
	
	@Column(name = "REVIEW_DATE")
	@Temporal(TemporalType.DATE)
	private Date reviewDate;
	
	@Column(name = "OUTCOME")
	@Type(type = "VarianceOutcome")
	private VarianceOutcome outcome;
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "REVOCATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date revocationDate;

	@Column(name = "PURPOSE")
	private String purpose;

	@Column(name = "HEALTH_SAFETY_INSURED_BY")
	private String healthSafetyInsuredBy;
	
	@Column(name = "CLIENT_NAME")
	private String clientName;

	@Column(name = "LICENSOR_OUTCOME")
	@Type(type = "VarianceOutcome")
	private VarianceOutcome licensorOutcome;
	
	@Column(name = "LICENSOR_RESPONSE")
	private String licensorResponse;
	
	@Column(name = "LICENSOR_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date licensorModifiedDate;
	
	@Column(name = "LICENSOR_MODIFIED_BY_ID")
	private Long licensorModifiedById;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LICENSOR_MODIFIED_BY_ID", insertable = false, updatable = false)
	private Person licensorModifiedBy;

	@Column(name = "SUPERVISOR_OUTCOME")
	@Type(type = "VarianceOutcome")
	private VarianceOutcome supervisorOutcome;
	
	@Column(name = "SUPERVISOR_RESPONSE")
	private String supervisorResponse;
	
	@Column(name = "SUPERVISOR_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date supervisorModifiedDate;
	
	@Column(name = "SUPERVISOR_MODIFIED_BY_ID")
	private Long supervisorModifiedById;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPERVISOR_MODIFIED_BY_ID", insertable = false, updatable = false)
	private Person supervisorModifiedBy;

	@Column(name = "DIRECTOR_RESPONSE")
	private String directorResponse;
	
	@Column(name = "DIRECTOR_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date directorModifiedDate;
	
	@Column(name = "DIRECTOR_MODIFIED_BY_ID")
	private Long directorModifiedById;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIRECTOR_MODIFIED_BY_ID", insertable = false, updatable = false)
	private Person directorModifiedBy;
	
	@Column(name = "FINALIZED")
	@Type(type = "yes_no")
	private Boolean finalized = false;
	
	@Column(name = "REVOKE_REASON")
	private String revokeReason;
	
	@Column(name = "REVOKE_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date revokeModifiedDate;
	
	@Column(name = "REVOKE_MODIFIED_BY_ID")
	private Long revokeModifiedById;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REVOKE_MODIFIED_BY_ID", insertable = false, updatable = false)
	private Person revokeModifiedBy;
	
	@Column(name = "REVOKE_FINALIZED")
	@Type(type = "yes_no")
	private Boolean revokeFinalized = false;

	public Variance() {
		Calendar cal = Calendar.getInstance();
		requestDate = cal.getTime();
		requestedStartDate = cal.getTime();
	}
	
	@Override
	public boolean isActive() {
		boolean rval = false;
		if (outcome != null && outcome.equals(VarianceOutcome.APPROVED) && startDate != null && endDate != null && isFinalized()) {
			// Variance has been approved and is finalized.
			Date today = org.apache.commons.lang.time.DateUtils.truncate(new Date(), Calendar.DATE);
			if (!isRevokeFinalized()) {
				// Variance has not been revoked so use start and variance end dates for date comparisons
				rval = (org.apache.commons.lang.time.DateUtils.isSameDay(today, getStartDate()) || 
						(today.compareTo(getStartDate()) >= 0 && today.compareTo(getEndDate()) <= 0) ||
						org.apache.commons.lang.time.DateUtils.isSameDay(today, getEndDate()));
			} else {
				// Variance has been revoked so use start and revocation dates for date comparisons
				if (getRevocationDate() != null) {
					rval = ((org.apache.commons.lang.time.DateUtils.isSameDay(today, getStartDate()) || 
							 today.compareTo(getStartDate()) >= 0 && today.compareTo(getRevocationDate()) < 0)) &&
							!org.apache.commons.lang.time.DateUtils.isSameDay(today, getRevocationDate());
				}
			}
		}
		return rval;
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

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Request date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Request date is required.", shortCircuit = true)
		}
	)
	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@RequiredFieldValidator(message = "Rule is required.")
	public RuleSubSection getRule() {
		return rule;
	}

	public void setRule(RuleSubSection rule) {
		this.rule = rule;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Requested start date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Requested start date is required.", shortCircuit = true)
		}
	)
	public Date getRequestedStartDate() {
		return requestedStartDate;
	}

	public void setRequestedStartDate(Date requestedStartDate) {
		this.requestedStartDate = requestedStartDate;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Requested end date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Requested end date is required.", shortCircuit = true)
		}
	)
	public Date getRequestedEndDate() {
		return requestedEndDate;
	}

	public void setRequestedEndDate(Date requestedEndDate) {
		this.requestedEndDate = requestedEndDate;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	public VarianceOutcome getOutcome() {
		return outcome;
	}
	
	public void setOutcome(VarianceOutcome outcome) {
		this.outcome = outcome;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRevocationDate() {
		return revocationDate;
	}

	public void setRevocationDate(Date revocationDate) {
		this.revocationDate = revocationDate;
	}

	@RequiredStringValidator(message = "Purpose is required.")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	@RequiredStringValidator(message = "How will health and safety be insured if approved entry is required.")
	public String getHealthSafetyInsuredBy() {
		return healthSafetyInsuredBy;
	}

	public void setHealthSafetyInsuredBy(String healthSafetyInsuredBy) {
		this.healthSafetyInsuredBy = healthSafetyInsuredBy;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public VarianceOutcome getLicensorOutcome() {
		return licensorOutcome;
	}

	public void setLicensorOutcome(VarianceOutcome licensorOutcome) {
		this.licensorOutcome = licensorOutcome;
	}

	public String getLicensorResponse() {
		return licensorResponse;
	}

	public void setLicensorResponse(String licensorResponse) {
		this.licensorResponse = licensorResponse;
	}

	public Date getLicensorModifiedDate() {
		return licensorModifiedDate;
	}

	public void setLicensorModifiedDate(Date licensorModifiedDate) {
		this.licensorModifiedDate = licensorModifiedDate;
	}

	public Long getLicensorModifiedById() {
		return licensorModifiedById;
	}

	public void setLicensorModifiedById(Long licensorModifiedById) {
		this.licensorModifiedById = licensorModifiedById;
	}

	public Person getLicensorModifiedBy() {
		return licensorModifiedBy;
	}

	public void setLicensorModifiedBy(Person licensorModifiedBy) {
		this.licensorModifiedBy = licensorModifiedBy;
	}

	public VarianceOutcome getSupervisorOutcome() {
		return supervisorOutcome;
	}

	public void setSupervisorOutcome(VarianceOutcome supervisorOutcome) {
		this.supervisorOutcome = supervisorOutcome;
	}

	public String getSupervisorResponse() {
		return supervisorResponse;
	}

	public void setSupervisorResponse(String supervisorResponse) {
		this.supervisorResponse = supervisorResponse;
	}

	public Date getSupervisorModifiedDate() {
		return supervisorModifiedDate;
	}

	public void setSupervisorModifiedDate(Date supervisorModifiedDate) {
		this.supervisorModifiedDate = supervisorModifiedDate;
	}

	public Long getSupervisorModifiedById() {
		return supervisorModifiedById;
	}

	public void setSupervisorModifiedById(Long supervisorModifiedById) {
		this.supervisorModifiedById = supervisorModifiedById;
	}

	public Person getSupervisorModifiedBy() {
		return supervisorModifiedBy;
	}

	public void setSupervisorModifiedBy(Person supervisorModifiedBy) {
		this.supervisorModifiedBy = supervisorModifiedBy;
	}

	public String getDirectorResponse() {
		return directorResponse;
	}

	public void setDirectorResponse(String directorResponse) {
		this.directorResponse = directorResponse;
	}

	public Date getDirectorModifiedDate() {
		return directorModifiedDate;
	}

	public void setDirectorModifiedDate(Date directorModifiedDate) {
		this.directorModifiedDate = directorModifiedDate;
	}

	public Long getDirectorModifiedById() {
		return directorModifiedById;
	}

	public void setDirectorModifiedById(Long directorModifiedById) {
		this.directorModifiedById = directorModifiedById;
	}

	public Person getDirectorModifiedBy() {
		return directorModifiedBy;
	}

	public void setDirectorModifiedBy(Person directorModifiedBy) {
		this.directorModifiedBy = directorModifiedBy;
	}

	public boolean isFinalized() {
		return finalized;
	}
	
	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}

	public String getRevokeReason() {
		return revokeReason;
	}
	
	public void setRevokeReason(String revokeReason) {
		this.revokeReason = revokeReason;
	}
	
	public Date getRevokeModifiedDate() {
		return revokeModifiedDate;
	}

	public void setRevokeModifiedDate(Date revokeModifiedDate) {
		this.revokeModifiedDate = revokeModifiedDate;
	}

	public Long getRevokeModifiedById() {
		return revokeModifiedById;
	}

	public void setRevokeModifiedById(Long revokeModifiedById) {
		this.revokeModifiedById = revokeModifiedById;
	}

	public Person getRevokeModifiedBy() {
		return revokeModifiedBy;
	}

	public void setRevokeModifiedBy(Person revokeModifiedBy) {
		this.revokeModifiedBy = revokeModifiedBy;
	}

	public boolean isRevokeFinalized() {
		return revokeFinalized;
	}
	
	public void setRevokeFinalized(boolean revokeFinalized) {
		this.revokeFinalized = revokeFinalized;
	}

	public Date getCompareDate() {
		return revocationDate;
	}

	//returns a new anonymous class for validating the requested date range
	public DateRange getRequestedDateRange() {
		return new DateRange() {

			public Date getStartDate() {
				return requestedStartDate;
			}
			
			public Date getEndDate() {
				return requestedEndDate;
			}
		};
	}

	public BetweenDateRange getRevocationDateCompareRange() {
		return new BetweenDateRange() {
			public Date getStartDate() {
				return startDate;
			}
			
			public Date getEndDate() {
				return endDate;
			}
			
			public Date getCompareDate() {
				return revocationDate;
			}
		};
	}

	/**
	 * Returns TRUE if the variance is in a revocable state.
	 * @return True if the variance outcome is approved, the outcome has been finalized, and the variance
	 * end date has not passed. 
	 */
	public boolean isRevocable() {
		Boolean rval = false;
		if (outcome != null && outcome.equals(VarianceOutcome.APPROVED) && endDate != null && isFinalized() && !isRevokeFinalized()) {
			Date today = org.apache.commons.lang.time.DateUtils.truncate(new Date(), Calendar.DATE);
			if (!today.after(endDate)) {
				rval = true;
			}
		}
		return rval;
	}
	
}