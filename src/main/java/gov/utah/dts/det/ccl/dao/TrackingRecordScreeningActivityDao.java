package gov.utah.dts.det.ccl.dao;

import java.util.List;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningActivity;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningActivityDao extends AbstractBaseDao<TrackingRecordScreeningActivity, Long> {

	public List<TrackingRecordScreeningActivity> getActivityForScreening(Long screeningId, SortBy sortBy);
	
}
