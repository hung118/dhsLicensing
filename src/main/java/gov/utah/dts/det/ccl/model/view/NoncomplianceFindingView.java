package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "NONCOMPLIANCE_FINDING_VIEW")
@Immutable
public class NoncomplianceFindingView implements Serializable {
	
	@Id
	@Column(name = "FINDING_ID")
	private Long findingId;
	
	@Column(name = "INSPECTION_ID")
	private Long inspectionId;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "RULE_ID")
	private Long ruleId;
	
	@Column(name = "INSPECTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date inspectionDate;
	
	@Column(name = "DECLARATIVE_STATEMENT")
	private String declarativeStatement;
	
	@Column(name = "ADDITIONAL_INFORMATION")
	private String additionalInformation;
	
	@Column(name = "NC_POINTS")
	private Integer ncPoints;
	
	@Column(name = "RULE_NUMBER")
	private String ruleNumber;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "FINDING_TYPE")
	private Character findingType;
	
	@Column(name = "SECTION_ORDER")
	private String sectionOrder;
	
	@Column(name = "SUBSECTION_ORDER")
	private String subsectionOrder;
	
	public NoncomplianceFindingView() {
		
	}

	public Long getFindingId() {
		return findingId;
	}

	public void setFindingId(Long findingId) {
		this.findingId = findingId;
	}

	public Long getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	public Date getInspectionDate() {
		return inspectionDate;
	}
	
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
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
	
	public Integer getNcPoints() {
		return ncPoints;
	}

	public void setNcPoints(Integer ncPoints) {
		this.ncPoints = ncPoints;
	}

	public String getRuleNumber() {
		return ruleNumber;
	}

	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Character getFindingType() {
		return findingType;
	}

	public void setFindingType(Character findingType) {
		this.findingType = findingType;
	}

	public String getSectionOrder() {
		return sectionOrder;
	}

	public void setSectionOrder(String sectionOrder) {
		this.sectionOrder = sectionOrder;
	}

	public String getSubsectionOrder() {
		return subsectionOrder;
	}

	public void setSubsectionOrder(String subsectionOrder) {
		this.subsectionOrder = subsectionOrder;
	}
}