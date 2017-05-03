package gov.utah.dts.det.ccl.service.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningOscarDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningOscar;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningOscarService;
import gov.utah.dts.det.query.SortBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningOscarService")
public class TrackingRecordScreeningOscarServiceImpl implements TrackingRecordScreeningOscarService {

	@Autowired
	TrackingRecordScreeningOscarDao trackingRecordScreeningOscarDao;

	@Override
	public List<TrackingRecordScreeningOscar> getOscarForScreening(Long screeningId, SortBy sortBy) {
		return trackingRecordScreeningOscarDao.getOscarForScreening(screeningId, sortBy);
	}

	@Override
	public TrackingRecordScreeningOscar save(TrackingRecordScreeningOscar entity) {
		return trackingRecordScreeningOscarDao.save(entity);
	}

	@Override
	public TrackingRecordScreeningOscar load(Long id) {
		return trackingRecordScreeningOscarDao.load(id);
	}

	@Override
	public void delete(Long id) {
		trackingRecordScreeningOscarDao.delete(id);
	}

	@Override
	public void evict(TrackingRecordScreeningOscar entity) {
		trackingRecordScreeningOscarDao.evict(entity);
	}

}
