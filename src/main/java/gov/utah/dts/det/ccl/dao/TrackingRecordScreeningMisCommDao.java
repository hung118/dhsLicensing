package gov.utah.dts.det.ccl.dao;

import java.util.List;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMisComm;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningMisCommDao extends AbstractBaseDao<TrackingRecordScreeningMisComm, Long> {

	public List<TrackingRecordScreeningMisComm> getMisCommForScreening(Long screeningId, SortBy sortBy);
	
}
