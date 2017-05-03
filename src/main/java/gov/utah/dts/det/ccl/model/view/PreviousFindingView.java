package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "PREVIOUS_FINDINGS_VIEW")
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.currentFinding", joinColumns = @JoinColumn(name = "CURR_FIND_ID"))
})
@Immutable
public class PreviousFindingView implements Serializable {

	@EmbeddedId
	private PreviousFindingViewPk primaryKey = new PreviousFindingViewPk();
	
	@Column(name = "PREV_FIND_INS_DATE")
	@Temporal(TemporalType.DATE)
	private Date inspectionDate;
	
	@Column(name = "PREV_FIND_INS_PRIMARY_TYPE")
	private String primaryInspectionType;
	
	@Column(name = "PREV_FIND_INS_OTHER_TYPES")
	private String otherInspectionTypes;
	
	@Column(name = "PREV_FIND_NC_LEVEL")
	private String noncomplianceLevel;
	
	@Column(name = "PREV_FIND_CATEGORY")
	private String findingCategory;
	
	@Column(name = "PREV_FIND_CMP_AMOUNT")
	private Double cmpAmount;
	
	@Column(name = "PREV_FIND_CORRECTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date correctionDate;
	
	@Column(name = "PREV_FIND_CORRECTED_ON_SITE")
	@Type(type = "yes_no")
	private Boolean correctedOnSite;
	
	@Column(name = "PREV_FIND_UNDER_APPEAL")
	@Type(type = "yes_no")
	private Boolean underAppeal;
	
	@Column(name = "PREV_FIND_DEC_STATEMENT")
	@Lob
	private String declarativeStatement;
	
	@Column(name = "PREV_FIND_ADDITIONAL_INFO")
	@Lob
	private String findingText;
	
	public PreviousFindingView() {
		
	}

	public PreviousFindingViewPk getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PreviousFindingViewPk primaryKey) {
		this.primaryKey = primaryKey;
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

	public String getOtherInspectionTypes() {
		return otherInspectionTypes;
	}

	public void setOtherInspectionTypes(String otherInspectionTypes) {
		this.otherInspectionTypes = otherInspectionTypes;
	}

	public String getNoncomplianceLevel() {
		return noncomplianceLevel;
	}

	public void setNoncomplianceLevel(String noncomplianceLevel) {
		this.noncomplianceLevel = noncomplianceLevel;
	}

	public String getFindingCategory() {
		return findingCategory;
	}

	public void setFindingCategory(String findingCategory) {
		this.findingCategory = findingCategory;
	}

	public Double getCmpAmount() {
		return cmpAmount;
	}

	public void setCmpAmount(Double cmpAmount) {
		this.cmpAmount = cmpAmount;
	}

	public Date getCorrectionDate() {
		return correctionDate;
	}

	public void setCorrectionDate(Date correctionDate) {
		this.correctionDate = correctionDate;
	}

	public Boolean getCorrectedOnSite() {
		return correctedOnSite;
	}

	public void setCorrectedOnSite(Boolean correctedOnSite) {
		this.correctedOnSite = correctedOnSite;
	}

	public Boolean getUnderAppeal() {
		return underAppeal;
	}

	public void setUnderAppeal(Boolean underAppeal) {
		this.underAppeal = underAppeal;
	}
	
	public String getDeclarativeStatement() {
		return declarativeStatement;
	}
	
	public void setDeclarativeStatement(String declarativeStatement) {
		this.declarativeStatement = declarativeStatement;
	}

	public String getFindingText() {
		return findingText;
	}

	public void setFindingText(String findingText) {
		this.findingText = findingText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmpAmount == null) ? 0 : cmpAmount.hashCode());
		result = prime * result + ((correctedOnSite == null) ? 0 : correctedOnSite.hashCode());
		result = prime * result + ((correctionDate == null) ? 0 : correctionDate.hashCode());
		result = prime * result + ((findingCategory == null) ? 0 : findingCategory.hashCode());
		result = prime * result + ((findingText == null) ? 0 : findingText.hashCode());
		result = prime * result + ((inspectionDate == null) ? 0 : inspectionDate.hashCode());
		result = prime * result + ((noncomplianceLevel == null) ? 0 : noncomplianceLevel.hashCode());
		result = prime * result + ((otherInspectionTypes == null) ? 0 : otherInspectionTypes.hashCode());
		result = prime * result + ((primaryInspectionType == null) ? 0 : primaryInspectionType.hashCode());
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
		result = prime * result + ((underAppeal == null) ? 0 : underAppeal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreviousFindingView other = (PreviousFindingView) obj;
		if (cmpAmount == null) {
			if (other.cmpAmount != null)
				return false;
		} else if (!cmpAmount.equals(other.cmpAmount))
			return false;
		if (correctedOnSite == null) {
			if (other.correctedOnSite != null)
				return false;
		} else if (!correctedOnSite.equals(other.correctedOnSite))
			return false;
		if (correctionDate == null) {
			if (other.correctionDate != null)
				return false;
		} else if (!correctionDate.equals(other.correctionDate))
			return false;
		if (findingCategory == null) {
			if (other.findingCategory != null)
				return false;
		} else if (!findingCategory.equals(other.findingCategory))
			return false;
		if (findingText == null) {
			if (other.findingText != null)
				return false;
		} else if (!findingText.equals(other.findingText))
			return false;
		if (inspectionDate == null) {
			if (other.inspectionDate != null)
				return false;
		} else if (!inspectionDate.equals(other.inspectionDate))
			return false;
		if (noncomplianceLevel == null) {
			if (other.noncomplianceLevel != null)
				return false;
		} else if (!noncomplianceLevel.equals(other.noncomplianceLevel))
			return false;
		if (otherInspectionTypes == null) {
			if (other.otherInspectionTypes != null)
				return false;
		} else if (!otherInspectionTypes.equals(other.otherInspectionTypes))
			return false;
		if (primaryInspectionType == null) {
			if (other.primaryInspectionType != null)
				return false;
		} else if (!primaryInspectionType.equals(other.primaryInspectionType))
			return false;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		if (underAppeal == null) {
			if (other.underAppeal != null)
				return false;
		} else if (!underAppeal.equals(other.underAppeal))
			return false;
		return true;
	}
}