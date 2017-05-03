package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.NoncompliancePoints;
import gov.utah.dts.det.ccl.model.NoncompliancePointsPk;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.List;

public interface NoncompliancePointsDao extends AbstractBaseDao<NoncompliancePoints, NoncompliancePointsPk> {

	public List<NoncompliancePoints> getAllPoints();
	
	public Integer getPointsForValues(FindingCategoryPickListValue ncLevel, PickListValue findingsCategory);
}