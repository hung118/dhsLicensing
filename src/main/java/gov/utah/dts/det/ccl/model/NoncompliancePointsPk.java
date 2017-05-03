package gov.utah.dts.det.ccl.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class NoncompliancePointsPk implements Serializable {

	private Long findingsCategoryId;
	private Long noncomplianceLevelId;
	
	public NoncompliancePointsPk() {
		
	}
	
	public Long getFindingsCategoryId() {
		return findingsCategoryId;
	}
	
	public void setFindingsCategoryId(Long findingsCategoryId) {
		this.findingsCategoryId = findingsCategoryId;
	}
	
	public Long getNoncomplianceLevelId() {
		return noncomplianceLevelId;
	}
	
	public void setNoncomplianceLevelId(Long noncomplianceLevelId) {
		this.noncomplianceLevelId = noncomplianceLevelId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((findingsCategoryId == null) ? 0 : findingsCategoryId.hashCode());
		result = prime * result + ((noncomplianceLevelId == null) ? 0 : noncomplianceLevelId.hashCode());
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
		NoncompliancePointsPk other = (NoncompliancePointsPk) obj;
		if (findingsCategoryId == null) {
			if (other.findingsCategoryId != null)
				return false;
		} else if (!findingsCategoryId.equals(other.findingsCategoryId))
			return false;
		if (noncomplianceLevelId == null) {
			if (other.noncomplianceLevelId != null)
				return false;
		} else if (!noncomplianceLevelId.equals(other.noncomplianceLevelId))
			return false;
		return true;
	}
}