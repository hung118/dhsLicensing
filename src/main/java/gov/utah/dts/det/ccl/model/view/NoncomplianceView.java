package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "NONCOMPLIANCE_VIEW")
@SuppressWarnings("serial")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class NoncomplianceView implements Serializable {

	@Id
	@Column(name = "INSPECTION_ID")
	private Long inspectionId;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "INSPECTION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inspectionDate;
	
	@Column(name = "PRIMARY_INSPECTION_TYPE")
	private String primaryInspectionType;
	
	@Column(name = "PRIMARY_TYPE_ABBREV")
	private String primaryTypeAbbreviation;
	
	@Column(name = "OTHER_INSPECTION_TYPES")
	private String otherInspectionTypes;
	
	@Column(name = "OTHER_ITYPES_ABBREV")
	private String otherTypesAbbreviations;
	
	@Column(name = "IS_ANNOUNCED")
	@Type(type = "yes_no")
	private boolean announced;
	
	@Column(name = "CITED_FINDINGS")
	private Integer citedFindings;
	
	@Column(name = "REPEAT_CITED_FINDINGS")
	private Integer repeatCitedFindings;
	
	@Column(name = "CITED_POINTS")
	private Integer citedNCPoints;
	
	@Column(name = "TA_FINDINGS")
	private Integer taFindings;
	
	@Column(name = "TA_POINTS")
	private Integer taNCPoints;
	
	@Column(name = "TOTAL_POINTS")
	private Integer totalNCPoints;
	
	@Column(name = "CMP_AMOUNT")
	private Double cmpAmount;
	
	public NoncomplianceView() {
		
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

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public String getPrimaryInspectionType() {
		return primaryInspectionType;
	}

	public void setPrimaryInspectionType(String primaryInspectionType) {
		this.primaryInspectionType = primaryInspectionType;
	}
	
	public String getPrimaryTypeAbbreviation() {
		return primaryTypeAbbreviation;
	}
	
	public void setPrimaryTypeAbbreviation(String primaryTypeAbbreviation) {
		this.primaryTypeAbbreviation = primaryTypeAbbreviation;
	}

	public String getOtherInspectionTypes() {
		return otherInspectionTypes;
	}

	public void setOtherInspectionTypes(String otherInspectionTypes) {
		this.otherInspectionTypes = otherInspectionTypes;
	}
	
	public String getOtherTypesAbbreviations() {
		return otherTypesAbbreviations;
	}
	
	public void setOtherTypesAbbreviations(String otherTypesAbbreviations) {
		this.otherTypesAbbreviations = otherTypesAbbreviations;
	}
	
	public boolean isAnnounced() {
		return announced;
	}
	
	public void setAnnounced(boolean announced) {
		this.announced = announced;
	}

	public Integer getCitedFindings() {
		return citedFindings;
	}

	public void setCitedFindings(Integer citedFindings) {
		this.citedFindings = citedFindings;
	}

	public Integer getRepeatCitedFindings() {
		return repeatCitedFindings;
	}

	public void setRepeatCitedFindings(Integer repeatCitedFindings) {
		this.repeatCitedFindings = repeatCitedFindings;
	}

	public Integer getCitedNCPoints() {
		return citedNCPoints;
	}

	public void setCitedNCPoints(Integer citedNCPoints) {
		this.citedNCPoints = citedNCPoints;
	}

	public Integer getTaFindings() {
		return taFindings;
	}

	public void setTaFindings(Integer taFindings) {
		this.taFindings = taFindings;
	}

	public Integer getTaNCPoints() {
		return taNCPoints;
	}

	public void setTaNCPoints(Integer taNCPoints) {
		this.taNCPoints = taNCPoints;
	}

	public Integer getTotalNCPoints() {
		return totalNCPoints;
	}

	public void setTotalNCPoints(Integer totalNCPoints) {
		this.totalNCPoints = totalNCPoints;
	}

	public Double getCmpAmount() {
		return cmpAmount;
	}

	public void setCmpAmount(Double cmpAmount) {
		this.cmpAmount = cmpAmount;
	}
}