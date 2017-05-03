package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.CorrectionVerificationType;
import gov.utah.dts.det.ccl.model.view.PreviousFindingView;
import gov.utah.dts.det.model.AbstractAuditableEntity;
import gov.utah.dts.det.util.CompareUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Entity
@Table(name = "FINDING")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Conversion
public class Finding extends AbstractAuditableEntity<Long> implements Serializable, Comparable<Finding> {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FINDING_SEQ")
	@SequenceGenerator(name = "FINDING_SEQ", sequenceName = "FINDING_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSPECTION_ID")
	private Inspection inspection;
	
	@ManyToOne
	@JoinColumn(name = "RULE_ID")
	private RuleSubSection rule;
	
	@Column(name = "DECLARATIVE_STATEMENT")
	@Lob
	private String declarativeStatement;
	
	@Column(name = "ADDITIONAL_INFORMATION")
	@Lob
	private String additionalInformation;
	
	@ManyToOne
	@JoinColumn(name = "FINDING_CATEGORY_ID")
	private FindingCategoryPickListValue findingCategory;
	
	@ManyToOne
	@JoinColumn(name = "NONCOMPLIANCE_LEVEL_ID")
	private NoncomplianceLevelPickListValue noncomplianceLevel;
	
	@Column(name = "CMP_AMOUNT")
	private Double cmpAmount;
	
	@Column(name = "CORRECTION_VERIFICATION")
	@Type(type = "CorrectionVerificationType")
	private CorrectionVerificationType correctionVerification = CorrectionVerificationType.VERIFICATION_PENDING;
	
	@Column(name = "WARN_CORRECTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date warningCorrectionDate;
	
	@Column(name = "UNDER_APPEAL_DATE")
	@Temporal(TemporalType.DATE)
	private Date appealDate;
	
	@Column(name = "RESCINDED_DATE")
	@Temporal(TemporalType.DATE)
	private Date rescindedDate;
	
	@ManyToOne
	@JoinTable(name = "FINDING_CORRECTION", joinColumns = {@JoinColumn(name = "FINDING_ID", referencedColumnName = "ID", unique = true)},
			inverseJoinColumns = {@JoinColumn(name = "INSPECTION_ID", referencedColumnName = "ID")})
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Inspection correctedOn;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
	@JoinTable(name = "FINDING_FOLLOW_UP",
			joinColumns = @JoinColumn(name = "FINDING_ID", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "INSPECTION_ID", referencedColumnName = "ID")
		)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Inspection> followUps = new HashSet<Inspection>();
	
	@OneToMany(mappedBy = "primaryKey.currentFinding", cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
	private List<PreviousFindingView> previousFindings = new ArrayList<PreviousFindingView>();
	
	public Finding() {
		
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

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}
	
	public String getDeclarativeStatement() {
		return declarativeStatement;
	}
	
	public void setDeclarativeStatement(String declarativeStatement) {
		this.declarativeStatement = declarativeStatement;
	}
	
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	public FindingCategoryPickListValue getFindingCategory() {
		return findingCategory;
	}
	
	public void setFindingCategory(FindingCategoryPickListValue findingCategory) {
		this.findingCategory = findingCategory;
	}

	public NoncomplianceLevelPickListValue getNoncomplianceLevel() {
		return noncomplianceLevel;
	}

	public void setNoncomplianceLevel(NoncomplianceLevelPickListValue noncomplianceLevel) {
		this.noncomplianceLevel = noncomplianceLevel;
	}

	public Double getCmpAmount() {
		return cmpAmount;
	}

	public void setCmpAmount(Double cmpAmount) {
		this.cmpAmount = cmpAmount;
	}
	
	public CorrectionVerificationType getCorrectionVerification() {
		return correctionVerification;
	}
	
	public void setCorrectionVerification(CorrectionVerificationType correctionVerification) {
		if (correctionVerification == CorrectionVerificationType.RESCINDED || correctionVerification == CorrectionVerificationType.UNDER_APPEAL
				|| correctionVerification == CorrectionVerificationType.VERIFIED) {
			throw new IllegalArgumentException("Unable to set correction verification type");
		}
		this.correctionVerification = correctionVerification;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Correction date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		}
	)
	public Date getWarningCorrectionDate() {
		return warningCorrectionDate;
	}
	
	public void setWarningCorrectionDate(Date warningCorrectionDate) {
		this.warningCorrectionDate = warningCorrectionDate;
	}
	
	@RequiredFieldValidator(message = "Rule number is required.")
	public RuleSubSection getRule() {
		return rule;
	}

	public void setRule(RuleSubSection rule) {
		this.rule = rule;
	}
	
	public Date getAppealDate() {
		return appealDate;
	}

	public Date getRescindedDate() {
		return rescindedDate;
	}

	public Inspection getCorrectedOn() {
		return correctedOn;
	}
	
	public void setCorrectedOn(Inspection correctedOn) {
		this.correctedOn = correctedOn;
		if (!isRescinded() && !isUnderAppeal()) {
			updateVerificationType();
		}
	}
	
	public Set<Inspection> getFollowUps() {
		return followUps;
	}
	
	public void setFollowUps(Set<Inspection> followUps) {
		this.followUps = followUps;
	}
	
	public void addFollowUp(Inspection inspection) {
		getFollowUps().add(inspection);
		if (!isRescinded() && !isUnderAppeal()) {
			updateVerificationType();
		}
	}
	
	public List<PreviousFindingView> getPreviousFindings() {
		return previousFindings;
	}
	
	public void setPreviousFindings(List<PreviousFindingView> previousFindings) {
		this.previousFindings = previousFindings;
	}
	
	public void rescind(Date rescindDate) {
		if (rescindDate == null) {
			throw new NullPointerException("Rescinded date must not be null when rescinding a finding.");
		}
		if (rescindDate.compareTo(getInspection().getInspectionDate()) < 0) {
			throw new IllegalArgumentException("Rescinded date must not be prior to the inspection date.");
		}
		this.rescindedDate = rescindDate;
		this.appealDate = null;
		correctionVerification = CorrectionVerificationType.RESCINDED;
	}
	
	public void unrescind() {
		if (rescindedDate != null) {
			this.rescindedDate = null;
			updateVerificationType();
		}
	}
	
	public void appeal(Date appealDate) {
		if (rescindedDate != null) {
			throw new IllegalStateException("Cannot appeal a rescinded finding.");
		}
		if (appealDate == null) {
			throw new NullPointerException("Appeal date must not be null when appealing a finding.");
		}
		if (appealDate.compareTo(getInspection().getInspectionDate()) < 0) {
			throw new IllegalArgumentException("Appeal date must not be prior to the inspection date.");
		}
		this.appealDate = appealDate;
		correctionVerification = CorrectionVerificationType.UNDER_APPEAL;
	}
	
	public void unappeal() {
		if (appealDate != null) {
			appealDate = null;
			updateVerificationType();
		}
	}
	
	/* This should only be called after the finding is taken out of appeal or the rescinding has been reversed */
	private void updateVerificationType() {
		if ((isCorrectedOnSite() && (getRule().isNoFollowUp() || !getFollowUps().isEmpty()))
				|| (getCorrectedOn() != null && !getCorrectedOn().equals(getInspection()))) {
			correctionVerification = CorrectionVerificationType.VERIFIED;
		} else {
			correctionVerification = CorrectionVerificationType.VERIFICATION_PENDING;
		}
	}
	
	public boolean isCorrectedOnSite() {
		if (getCorrectedOn() != null && getCorrectedOn().equals(getInspection())) {
			return true;
		}
		return false;
	}
	
	public boolean isFollowUpNeeded() {
		if (correctionVerification == CorrectionVerificationType.VERIFICATION_PENDING) {
			return true;
		}
		
		return false;
	}
	
	public boolean isRescinded() {
		return getRescindedDate() != null;
	}
	
	public boolean isUnderAppeal() {
		return getAppealDate() != null;
	}
	
	@Override
	public int compareTo(Finding o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getInspection(), o.getInspection(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getRescindedDate(), o.getRescindedDate(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getRule(), o.getRule(), true);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		return comp;
	}
}