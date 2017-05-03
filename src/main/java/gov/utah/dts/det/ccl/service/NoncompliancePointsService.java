package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.NoncompliancePoints;

import java.util.Map;

public interface NoncompliancePointsService {

	public NoncompliancePoints saveNoncompliancePoints(NoncompliancePoints noncompliancePoints);
	
	public void savePointsMatrix(Map<String, Integer> points);
	
	public Map<String, String> getPointsMatrix();
	
	public Integer getPointsForValues(FindingCategoryPickListValue ncLevel, PickListValue findingsCategory);
}