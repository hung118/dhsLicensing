package gov.utah.dts.det.ccl.dao;

import java.util.List;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLtr15;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningLtr15Dao extends AbstractBaseDao<TrackingRecordScreeningLtr15, Long> {

	public List<TrackingRecordScreeningLtr15> get15DayLettersForScreening(Long screeningId, SortBy sortBy);

}
