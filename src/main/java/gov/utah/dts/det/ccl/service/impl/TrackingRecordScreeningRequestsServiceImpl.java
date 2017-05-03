package gov.utah.dts.det.ccl.service.impl;

import java.util.List;
import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningRequestsDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningRequests;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningRequestService;
import gov.utah.dts.det.query.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningRequestService")
public class TrackingRecordScreeningRequestsServiceImpl implements TrackingRecordScreeningRequestService {

	@Autowired
	TrackingRecordScreeningRequestsDao trackingRecordScreeningRequestsDao;

	@Override
	public List<TrackingRecordScreeningRequests> getRequestsForScreening(Long screeningId, SortBy sortBy) {
		return trackingRecordScreeningRequestsDao.getRequestsForScreening(screeningId, sortBy);
	}

	@Override
	public TrackingRecordScreeningRequests save(TrackingRecordScreeningRequests entity) {
		return trackingRecordScreeningRequestsDao.save(entity);
	}

	@Override
	public TrackingRecordScreeningRequests load(Long id) {
		return trackingRecordScreeningRequestsDao.load(id);
	}
	
	@Override
	public void delete(Long id) {
		trackingRecordScreeningRequestsDao.delete(id);
	}
	
	@Override
	public void evict(TrackingRecordScreeningRequests entity) {
		trackingRecordScreeningRequestsDao.evict(entity);
	}

}
