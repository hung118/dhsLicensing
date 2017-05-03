package gov.utah.dts.det.ccl.service;

public class NoncompliancePointMatrixKey {

	private Long findingCategoryId;
	private Long noncomplianceLevelId;
	
	public NoncompliancePointMatrixKey(Long findingCategoryId, Long noncomplianceLevelId) {
		this.findingCategoryId = findingCategoryId;
		this.noncomplianceLevelId = noncomplianceLevelId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((findingCategoryId == null) ? 0 : findingCategoryId.hashCode());
		result = prime * result + ((noncomplianceLevelId == null) ? 0 : noncomplianceLevelId.hashCode());
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
		if (!(obj instanceof NoncompliancePointMatrixKey)) {
			return false;
		}
		NoncompliancePointMatrixKey other = (NoncompliancePointMatrixKey) obj;
		if (findingCategoryId == null) {
			if (other.findingCategoryId != null) {
				return false;
			}
		} else if (!findingCategoryId.equals(other.findingCategoryId)) {
			return false;
		}
		if (noncomplianceLevelId == null) {
			if (other.noncomplianceLevelId != null) {
				return false;
			}
		} else if (!noncomplianceLevelId.equals(other.noncomplianceLevelId)) {
			return false;
		}
		return true;
	}
}