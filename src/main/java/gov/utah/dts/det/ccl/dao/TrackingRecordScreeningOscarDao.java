package gov.utah.dts.det.ccl.dao;

import java.util.List;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningOscar;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningOscarDao extends AbstractBaseDao<TrackingRecordScreeningOscar, Long> {

	public List<TrackingRecordScreeningOscar> getOscarForScreening(Long screeningId, SortBy sortBy);
	
}
