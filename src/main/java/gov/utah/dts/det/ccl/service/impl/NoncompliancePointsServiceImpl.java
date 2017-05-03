package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.NoncompliancePointsDao;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.NoncompliancePoints;
import gov.utah.dts.det.ccl.service.NoncompliancePointsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("noncompliancePointsService")
public class NoncompliancePointsServiceImpl implements NoncompliancePointsService {

	@Autowired
	private NoncompliancePointsDao noncompliancePointsDao;
	
	@Override
	public NoncompliancePoints saveNoncompliancePoints(NoncompliancePoints noncompliancePoints) {
		return noncompliancePointsDao.save(noncompliancePoints);
	}
	
	@Override
	public void savePointsMatrix(Map<String, Integer> pointsMatrix) {
		//get a map of the original values
		Map<String, NoncompliancePoints> originalPointsMatrix = new HashMap<String, NoncompliancePoints>();
		List<NoncompliancePoints> allPoints = noncompliancePointsDao.getAllPoints();
		for (NoncompliancePoints value : allPoints) {
			originalPointsMatrix.put(getKey(value), value);
		}
		
		for (String key : pointsMatrix.keySet()) {
			//see if the points value already exists
			NoncompliancePoints value = originalPointsMatrix.get(key);
			if (value == null) {
				//did not already exist, add a new one
				value = new NoncompliancePoints();
				int separator = key.indexOf("-");
				value.setFindingsCategoryId(Long.parseLong(key.substring(0, separator)));
				value.setNoncomplianceLevelId(Long.parseLong(key.substring(separator + 1)));
			}
			//set the points value
			value.setPoints(pointsMatrix.get(key));
			
			//save the value
			saveNoncompliancePoints(value);
		}
	}
	
	@Override
	public Map<String, String> getPointsMatrix() {
		Map<String, String> pointsMatrix = new HashMap<String, String>();
		List<NoncompliancePoints> points = noncompliancePointsDao.getAllPoints();
		for (NoncompliancePoints value : points) {
			pointsMatrix.put(getKey(value), value.getPoints().toString());
		}
		return pointsMatrix;
	}
	
	@Override
	public Integer getPointsForValues(FindingCategoryPickListValue ncLevel, PickListValue findingsCategory) {
		return noncompliancePointsDao.getPointsForValues(ncLevel, findingsCategory);
	}
	
	private String getKey(NoncompliancePoints points) {
		return points.getFindingsCategoryId() + "-" + points.getNoncomplianceLevelId();
	}
}