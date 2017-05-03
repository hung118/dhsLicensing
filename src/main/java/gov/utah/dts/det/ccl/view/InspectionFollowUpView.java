package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.admin.model.PickListValue;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@SuppressWarnings("serial")
public class InspectionFollowUpView implements Serializable {

	private Long inspectionId;
	private Long findingId;
	private Date inspectionDate;
	private PickListValue primaryInspectionType;
	private Set<PickListValue> otherInspectionTypes;
	private Long ruleId;
	private boolean corrected = false;
	private boolean correctedByThis = false;
	private boolean followedUpByThis = false;
	
	public InspectionFollowUpView() {
		
	}

	public Long getInspectionId() {
		return inspectionId;
	}
	
	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}
	
	public Long getFindingId() {
		return findingId;
	}
	
	public void setFindingId(Long findingId) {
		this.findingId = findingId;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	
	public PickListValue getPrimaryInspectionType() {
		return primaryInspectionType;
	}
	
	public void setPrimaryInspectionType(PickListValue primaryInspectionType) {
		this.primaryInspectionType = primaryInspectionType;
	}
	
	public Set<PickListValue> getOtherInspectionTypes() {
		return otherInspectionTypes;
	}
	
	public void setOtherInspectionTypes(Set<PickListValue> otherInspectionTypes) {
		this.otherInspectionTypes = otherInspectionTypes;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public boolean getCorrected() {
		return corrected;
	}

	public void setCorrected(boolean corrected) {
		this.corrected = corrected;
	}

	public boolean getCorrectedByThis() {
		return correctedByThis;
	}

	public void setCorrectedByThis(Boolean correctedByThis) {
		this.correctedByThis = correctedByThis;
	}
	
	public boolean isFollowedUpByThis() {
		return followedUpByThis;
	}
	
	public void setFollowedUpByThis(boolean followedUpByThis) {
		this.followedUpByThis = followedUpByThis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((findingId == null) ? 0 : findingId.hashCode());
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
		InspectionFollowUpView other = (InspectionFollowUpView) obj;
		if (findingId == null) {
			if (other.findingId != null)
				return false;
		} else if (!findingId.equals(other.findingId))
			return false;
		return true;
	}
}