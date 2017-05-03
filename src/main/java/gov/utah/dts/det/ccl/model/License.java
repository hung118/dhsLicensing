/**
 * 
 */
package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.CondSanc;
import gov.utah.dts.det.model.AbstractAuditableEntity;
import gov.utah.dts.det.util.CompareUtils;
import gov.utah.dts.det.util.DateUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FACILITY_LICENSE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class License extends AbstractAuditableEntity<Long> implements Serializable, Activatable, DateRange, Comparable<License> {
	
	public static final Date DEFAULT_LICENSE_END_DATE;
	
	static {
		Calendar calendar = Calendar.getInstance();
		calendar.set(9999, 8, 9);
		DEFAULT_LICENSE_END_DATE = org.apache.commons.lang.time.DateUtils.truncate(calendar, Calendar.DATE).getTime();
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FACILITY_LICENSE_SEQ")
	@SequenceGenerator(name = "FACILITY_LICENSE_SEQ", sequenceName = "FACILITY_LICENSE_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FACILITY_ID")
	private Facility facility;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LICENSE_SUBTYPE_ID")
	private PickListValue subtype;	// subtype (ccl) changed to status -- Redmine 24673 changed to License Type

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LICENSE_STATUS_ID")
	private PickListValue status;
	
	@Column(name = "ADULT_TOTAL_SLOTS")
	private Integer adultTotalSlots;

	@Column(name = "ADULT_MALE_COUNT")
	private Integer adultMaleCount;
	
	@Column(name = "ADULT_FEMALE_COUNT")
	private Integer adultFemaleCount;

	@Column(name = "YOUTH_TOTAL_SLOTS")
	private Integer youthTotalSlots;

	@Column(name = "YOUTH_MALE_COUNT")
	private Integer youthMaleCount;
	
	@Column(name = "YOUTH_FEMALE_COUNT")
	private Integer youthFemaleCount;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "EXPIRATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
/*
 * No longer used.  Remine 24760
 *
	@Column(name = "LICENSE_HOLDER_NAME")
	private String licenseHolderName;
 */	

	@Column(name = "CALCULATES_ALERTS")
	@Type(type = "yes_no")
	private Boolean calculatesAlerts = true;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SERVICE_CODE_ID")
	private PickListValue serviceCode;
	
	@ManyToMany
	@JoinTable(name = "LICENSE_PROGRAM_CODE",
		joinColumns = @JoinColumn(name = "LICENSE_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "PROGRAM_CODE_ID", referencedColumnName = "ID")
	)
	private List<PickListValue> programCodeIds = new ArrayList<PickListValue>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SPECIFIC_SERVICE_CODE_ID")
	private PickListValue specificServiceCode;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AGE_GROUP_ID")
	private PickListValue ageGroup;
	
	@Column(name = "FROM_AGE")
	private String fromAge;
	
	@Column(name = "TO_AGE")
	private String toAge;
	
	@Column(name = "COND_SANC")
	@Type(type = "CondSanc")
	private CondSanc condSanc;
	
	@Column(name = "COMMENT_TEXT")
	private String comment;

	@Column(name = "APPLICATION_RCVD")
	@Temporal(TemporalType.DATE)
	private Date applicationReceived;

	@Column(name = "SIGNATURE_FORM_RCVD")
	@Temporal(TemporalType.DATE)
	private Date signatureFormReceived;

	@Column(name = "VERIFICATION_OF_INCOME_RCVD")
	@Temporal(TemporalType.DATE)
	private Date voiReceived;

	@Column(name = "HOME_STUDY_RCVD")
	@Temporal(TemporalType.DATE)
	private Date homeStudyReceived;

	@Column(name = "MEDICAL_RCVD")
	@Temporal(TemporalType.DATE)
	private Date medicalReceived;

	@Column(name = "SPOUSE_MEDICAL_RCVD")
	@Temporal(TemporalType.DATE)
	private Date spouseMedicalReceived;

	@Column(name = "TRAINING_VERIFIED")
	@Temporal(TemporalType.DATE)
	private Date trainingVerified;

	@Column(name = "SPOUSE_TRAINING_VERIFIED")
	@Temporal(TemporalType.DATE)
	private Date spouseTrainingVerified;
	
	@Column(name = "TWO_YEAR")
	@Type(type = "yes_no")
	private Boolean twoYear = false;
	
	@Column(name = "FINALIZED")
	@Type(type = "yes_no")
	private Boolean finalized = false;
	
	@Column(name = "CERTIFICATE_COMMENT")
	private String certificateComment;
	
	@Transient
	public Long previousLicenseNumber;
	
	@Column(name = "CLOSED_DATE")
	@Temporal(TemporalType.DATE)
	private Date closedDate;
	
	@Column(name = "CLOSED_REASON")
	private String closedReason;

	public License() {
		
	}
	
	@Override
	public boolean isActive() {
		return DateUtils.isActive(this);
	}
	
	@Override
	public Date getEndDate() {
		return getExpirationDate();
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
	
	@RequiredFieldValidator(message = "License Type is required.")
	public PickListValue getSubtype() {
		return subtype;
	}
	
	public void setSubtype(PickListValue subtype) {
		this.subtype = subtype;
	}
	
	public PickListValue getStatus() {
		return status;
	}

	public void setStatus(PickListValue status) {
		this.status = status;
	}

	public PickListValue getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(PickListValue serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public String getServiceCodeCode() {
		String rval = null;
		if (getServiceCode() != null && StringUtils.isNotBlank(getServiceCode().getValue())) {
			String code = getServiceCode().getValue();
			int idx = code.indexOf("-");
			if (idx > 0) {
				rval = code.substring(0,idx).trim();
			}
		}
		return rval;
	}

	public String getServiceCodeDesc() {
		String rval = null;
		if (getServiceCode() != null && StringUtils.isNotBlank(getServiceCode().getValue())) {
			String code = getServiceCode().getValue();
			int idx = code.indexOf("-");
			if (idx >= 0 && (idx+1 <= code.length())) {
				idx++;
				rval = code.substring(idx).trim();
			}
		}
		return rval;
	}

	/*
	 * No longer used.  Redmine 24760
	 *
	// @RequiredStringValidator(message = "Licensee is required.") Redmine 24760
	public String getLicenseHolderName() {
		return licenseHolderName;
	}
	
	public void setLicenseHolderName(String licenseHolderName) {
		this.licenseHolderName = licenseHolderName;
	}
	*/

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Adult Total must be a number.", shortCircuit = true)
		}
	)
	public Integer getAdultTotalSlots() {
		return adultTotalSlots;
	}

	public void setAdultTotalSlots(Integer adultTotalSlots) {
		this.adultTotalSlots = adultTotalSlots;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "# Adult Male must be a number.", shortCircuit = true)
		}
	)
	public Integer getAdultMaleCount() {
		return adultMaleCount;
	}

	public void setAdultMaleCount(Integer adultMaleCount) {
		this.adultMaleCount = adultMaleCount;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "# Adult Female must be a number.", shortCircuit = true)
		}
	)
	public Integer getAdultFemaleCount() {
		return adultFemaleCount;
	}

	public void setAdultFemaleCount(Integer adultFemaleCount) {
		this.adultFemaleCount = adultFemaleCount;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "License start date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
				@RequiredFieldValidator(message = "License start date is required.", shortCircuit = true)
			}
	)
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = org.apache.commons.lang.time.DateUtils.truncate(startDate, Calendar.DATE);
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "License expiration date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		}
	)
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = org.apache.commons.lang.time.DateUtils.truncate(expirationDate, Calendar.DATE);
	}
	
	public String getExpirationDateFormatted() {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		if (getExpirationDate() == null) {
			return null;
		}
		return df.format(getExpirationDate());
	}
	
	public boolean isCalculatesAlerts() {
		return calculatesAlerts;
	}
	
	public void setCalculatesAlerts(boolean calculatesAlerts) {
		this.calculatesAlerts = calculatesAlerts;
	}
	
	public boolean isDefaultExpirationDate() {
		return DEFAULT_LICENSE_END_DATE.equals(expirationDate);
	}

	public PickListValue getSpecificServiceCode() {
		return specificServiceCode;
	}

	public void setSpecificServiceCode(PickListValue specificServiceCode) {
		this.specificServiceCode = specificServiceCode;
	}

	public List<PickListValue> getProgramCodeIds() {
		return programCodeIds;
	}

	public void setProgramCodeIds(List<PickListValue> programCodeIds) {
		this.programCodeIds = programCodeIds;
	}

	public PickListValue getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(PickListValue ageGroup) {
		this.ageGroup = ageGroup;
	}

	public String getFromAge() {
		return fromAge;
	}

	public void setFromAge(String fromAge) {
		this.fromAge = fromAge;
	}

	public String getToAge() {
		return toAge;
	}

	public void setToAge(String toAge) {
		this.toAge = toAge;
	}

	public CondSanc getCondSanc() {
		return condSanc;
	}

	public void setCondSanc(CondSanc condSanc) {
		this.condSanc = condSanc;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getApplicationReceived() {
		return applicationReceived;
	}

	public void setApplicationReceived(Date applicationReceived) {
		this.applicationReceived = applicationReceived;
	}

	public Date getSignatureFormReceived() {
		return signatureFormReceived;
	}

	public void setSignatureFormReceived(Date signatureFormReceived) {
		this.signatureFormReceived = signatureFormReceived;
	}

	public Date getVoiReceived() {
		return voiReceived;
	}

	public void setVoiReceived(Date voiReceived) {
		this.voiReceived = voiReceived;
	}

	public Date getHomeStudyReceived() {
		return homeStudyReceived;
	}

	public void setHomeStudyReceived(Date homeStudyReceived) {
		this.homeStudyReceived = homeStudyReceived;
	}

	public Date getMedicalReceived() {
		return medicalReceived;
	}

	public void setMedicalReceived(Date medicalReceived) {
		this.medicalReceived = medicalReceived;
	}

	public Date getSpouseMedicalReceived() {
		return spouseMedicalReceived;
	}

	public void setSpouseMedicalReceived(Date spouseMedicalReceived) {
		this.spouseMedicalReceived = spouseMedicalReceived;
	}

	public Date getTrainingVerified() {
		return trainingVerified;
	}

	public void setTrainingVerified(Date trainingVerified) {
		this.trainingVerified = trainingVerified;
	}

	public Date getSpouseTrainingVerified() {
		return spouseTrainingVerified;
	}

	public void setSpouseTrainingVerified(Date spouseTrainingVerified) {
		this.spouseTrainingVerified = spouseTrainingVerified;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Youth Total must be a number.", shortCircuit = true)
		}
	)
	public Integer getYouthTotalSlots() {
		return youthTotalSlots;
	}

	public void setYouthTotalSlots(Integer youthTotalSlots) {
		this.youthTotalSlots = youthTotalSlots;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "# Youth Male must be a number.", shortCircuit = true)
		}
	)
	public Integer getYouthMaleCount() {
		return youthMaleCount;
	}

	public void setYouthMaleCount(Integer youthMaleCount) {
		this.youthMaleCount = youthMaleCount;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "# Youth Female must be a number.", shortCircuit = true)
		}
	)
	public Integer getYouthFemaleCount() {
		return youthFemaleCount;
	}

	public void setYouthFemaleCount(Integer youthFemaleCount) {
		this.youthFemaleCount = youthFemaleCount;
	}
	
	public boolean isTwoYear() {
		return twoYear;
	}
	
	public void setTwoYear(boolean twoYear) {
		this.twoYear = twoYear;
	}
	
	public Boolean isFinalized() {
		return finalized;
	}

	public void setFinalized(Boolean finalized) {
		this.finalized = finalized;
	}

	public Long getLicenseNumber() {
		return id;
	}

	public String getCertificateComment() {
        return certificateComment;
    }

    public void setCertificateComment(String certificateComment) {
        this.certificateComment = certificateComment;
    }

    public String getLicenseListDescriptor() {
		StringBuilder sb = new StringBuilder();
		if (getLicenseNumber() != null) {
			sb.append("("+getLicenseNumber().toString()+")");
		}
		if (serviceCode != null && StringUtils.isNotBlank(serviceCode.getValue())) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(serviceCode.getValue());
		}
		if (subtype != null && StringUtils.isNotBlank(subtype.getValue())) {
			if (sb.length() > 0) {
				sb.append(" ");
			}
			sb.append(":"+subtype.getValue()+":");
		}
		return sb.toString();
	}

	@Override
	public int compareTo(License o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getFacility(), o.getFacility(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getStartDate(), o.getStartDate(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getExpirationDate(), o.getExpirationDate(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getSubtype(), o.getSubtype(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getServiceCode(), o.getServiceCode(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		return comp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getFacility() == null) ? 0 : (getFacility().getId() == null ? getFacility().hashCode() : getFacility().getId().hashCode()));
		result = prime * result + ((getSubtype() == null) ? 0 : getSubtype().hashCode());
		result = prime * result + ((getServiceCode() == null) ? 0 : getServiceCode().hashCode());
		result = prime * result + ((getAdultTotalSlots() == null) ? 0 : getAdultTotalSlots().hashCode());
		result = prime * result + ((getYouthTotalSlots() == null) ? 0 : getYouthTotalSlots().hashCode());
		result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
		result = prime * result + ((getExpirationDate() == null) ? 0 : getExpirationDate().hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof License)) {
			return false;
		}
		License other = (License) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getFacility() == null) {
			if (other.getFacility() != null) {
				return false;
			}
		} else if (!getFacility().equals(other.getFacility())) {
			return false;
		}
		if (getSubtype() == null) {
			if (other.getSubtype() != null) {
				return false;
			}
		} else if (!getSubtype().equals(other.getSubtype())) {
			return false;
		}
		if (getAdultTotalSlots() == null) {
			if (other.getAdultTotalSlots() != null) {
				return false;
			}
		} else if (!getAdultTotalSlots().equals(other.getAdultTotalSlots())) {
			return false;
		}
		if (getYouthTotalSlots() == null) {
			if (other.getYouthTotalSlots() != null) {
				return false;
			}
		} else if (!getYouthTotalSlots().equals(other.getYouthTotalSlots())) {
			return false;
		}
		if (getStartDate() == null) {
			if (other.getStartDate() != null) {
				return false;
			}
		} else if (!getStartDate().equals(other.getStartDate())) {
			return false;
		}
		if (getExpirationDate() == null) {
			if (other.getExpirationDate() != null) {
				return false;
			}
		} else if (!getExpirationDate().equals(other.getExpirationDate())) {
			return false;
		}
		return true;
	}

	public Long getPreviousLicenseNumber() {
		return previousLicenseNumber;
	}

	public void setPreviousLicenseNumber(Long previousLicenseNumber) {
		this.previousLicenseNumber = previousLicenseNumber;
	}
	
	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getClosedReason() {
		return closedReason;
	}

	public void setClosedReason(String closedReason) {
		this.closedReason = closedReason;
	}
}