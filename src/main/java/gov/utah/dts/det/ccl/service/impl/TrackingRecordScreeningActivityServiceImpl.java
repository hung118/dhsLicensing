package gov.utah.dts.det.ccl.service.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningActivityDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningActivity;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningActivityService;
import gov.utah.dts.det.query.SortBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningActivityService")
public class TrackingRecordScreeningActivityServiceImpl extends GenericServiceImpl<TrackingRecordScreeningActivity, Long>
    implements TrackingRecordScreeningActivityService {

  @Autowired
  TrackingRecordScreeningActivityDao trackingRecordScreeningActivityDao;

	@Override
	public List<TrackingRecordScreeningActivity> getActivityForScreening(Long screeningId, SortBy sortBy) {
		return trackingRecordScreeningActivityDao.getActivityForScreening(screeningId, sortBy);
	}

	@Override
	public TrackingRecordScreeningActivity save(TrackingRecordScreeningActivity entity) {
		return trackingRecordScreeningActivityDao.save(entity);
	}

	@Override
	public TrackingRecordScreeningActivity load(Long id) {
		return trackingRecordScreeningActivityDao.load(id);
	}

	@Override
	public void delete(Long id) {
		trackingRecordScreeningActivityDao.delete(id);
	}

	@Override
	public void evict(TrackingRecordScreeningActivity entity) {
		trackingRecordScreeningActivityDao.evict(entity);
	}

}
