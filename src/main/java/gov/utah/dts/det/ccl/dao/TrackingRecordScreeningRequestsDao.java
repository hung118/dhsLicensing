package gov.utah.dts.det.ccl.dao;

import java.util.List;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningRequests;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningRequestsDao extends AbstractBaseDao<TrackingRecordScreeningRequests, Long> {

	public List<TrackingRecordScreeningRequests> getRequestsForScreening(Long screeningId, SortBy sortBy);

}
