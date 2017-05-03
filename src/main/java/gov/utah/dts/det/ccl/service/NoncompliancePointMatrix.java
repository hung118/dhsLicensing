package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.NoncompliancePoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoncompliancePointMatrix {

	private Map<NoncompliancePointMatrixKey, Integer> matrix = new HashMap<NoncompliancePointMatrixKey, Integer>();
	
	public NoncompliancePointMatrix(List<NoncompliancePoints> points) {
		for (NoncompliancePoints pts : points) {
			NoncompliancePointMatrixKey key = new NoncompliancePointMatrixKey(pts.getFindingsCategoryId(), pts.getNoncomplianceLevelId());
			matrix.put(key, pts.getPoints());
		}
	}
	
	public int getPoints(PickListValue findingCategory, PickListValue noncomplianceLevel) {
		int points = 0;
		if (findingCategory != null && noncomplianceLevel != null && findingCategory.getId() != null && noncomplianceLevel.getId() != null) {
			Integer pts = matrix.get(new NoncompliancePointMatrixKey(findingCategory.getId(), noncomplianceLevel.getId()));
			if (pts != null) {
				points = pts.intValue();
			}
		}
		return points;
	}
}