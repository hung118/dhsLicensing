package gov.utah.dts.det.ccl.service;

import java.util.Date;

public class RuleViolation {

	private Long inspectionId;
	private Date inspectionDate;
	private String primaryType;
	private String otherTypes;
	private String ncLevel;
	private String findingCategory;
	private Date rescindedDate;
	private Date appealDate;
	
	public RuleViolation(Long inspectionId, Date inspectionDate, String primaryType, String otherTypes, String ncLevel, String findingCategory, Date rescindedDate, Date appealDate) {
		this.inspectionId = inspectionId;
		this.inspectionDate = inspectionDate;
		this.primaryType = primaryType;
		this.otherTypes = otherTypes;
		this.ncLevel = ncLevel;
		this.findingCategory = findingCategory;
		this.rescindedDate = rescindedDate;
		this.appealDate = appealDate;
	}

	public Long getInspectionId() {
		return inspectionId;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}
	
	public String getPrimaryType() {
		return primaryType;
	}
	
	public String getOtherTypes() {
		return otherTypes;
	}

	public String getNcLevel() {
		return ncLevel;
	}

	public String getFindingCategory() {
		return findingCategory;
	}

	public Date getRescindedDate() {
		return rescindedDate;
	}

	public Date getAppealDate() {
		return appealDate;
	}
}