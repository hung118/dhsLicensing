package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.enums.CorrectionVerificationType;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "FILE_CHECK_VIEW")
@Immutable
public class FileCheckView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "INSPECTION_ID")
	private Long inspectionId;
	
	@Column(name = "INSPECTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date inspectionDate;
	
	@Column(name = "INSPECTION_TYPES")
	private String inspectionTypes;
	
	@Column(name = "FINDING_CATEGORY")
	private String findingCategory;
	
	@Column(name = "NONCOMPLIANCE_LEVEL")
	private String noncomplianceLevel;
	
	@Column(name = "RULE_ID")
	private Long ruleId;
	
	@Column(name = "RULE_TEXT_DOC_ID")
	private Long ruleTextDocId;
	
	@Column(name = "DECLARATIVE_STATEMENT")
	private String declarativeStatement;
	
	@Column(name = "ADDITIONAL_INFORMATION")
	private String additionalInformation;
	
	@Column(name = "CORRECTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date correctionDate;
	
	@Column(name = "VERIFICATION_TYPE")
	@Type(type = "CorrectionVerificationType")
	private CorrectionVerificationType verificationType;
	
	@Column(name = "COMPLAINT_ID")
	private Long complaintId;
	
	@Column(name = "COMPLAINT_DATE")
	@Temporal(TemporalType.DATE)
	private Date complaintDate;
	
	@Column(name = "CITED")
	@Type(type = "yes_no")
	private Boolean cited;
	
	@Column(name = "SUBSTANTIATED")
	@Type(type = "yes_no")
	private Boolean substantiated;
	
	public FileCheckView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public Long getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public String getInspectionTypes() {
		return inspectionTypes;
	}

	public void setInspectionTypes(String inspectionTypes) {
		this.inspectionTypes = inspectionTypes;
	}

	public String getFindingCategory() {
		return findingCategory;
	}

	public void setFindingCategory(String findingCategory) {
		this.findingCategory = findingCategory;
	}

	public String getNoncomplianceLevel() {
		return noncomplianceLevel;
	}

	public void setNoncomplianceLevel(String noncomplianceLevel) {
		this.noncomplianceLevel = noncomplianceLevel;
	}
	
	public Long getRuleId() {
		return ruleId;
	}
	
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	public Long getRuleTextDocId() {
		return ruleTextDocId;
	}
	
	public void setRuleTextDocId(Long ruleTextDocId) {
		this.ruleTextDocId = ruleTextDocId;
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
	
	public Date getCorrectionDate() {
		return correctionDate;
	}
	
	public void setCorrectionDate(Date correctionDate) {
		this.correctionDate = correctionDate;
	}
	
	public CorrectionVerificationType getVerificationType() {
		return verificationType;
	}
	
	public void setVerificationType(CorrectionVerificationType verificationType) {
		this.verificationType = verificationType;
	}

	public Long getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}

	public Date getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}

	public Boolean getCited() {
		return cited;
	}

	public void setCited(Boolean cited) {
		this.cited = cited;
	}

	public Boolean getSubstantiated() {
		return substantiated;
	}

	public void setSubstantiated(Boolean substantiated) {
		this.substantiated = substantiated;
	}
}