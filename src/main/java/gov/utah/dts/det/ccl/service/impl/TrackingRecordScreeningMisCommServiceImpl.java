package gov.utah.dts.det.ccl.service.impl;

import java.util.List;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningMisCommDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMisComm;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningMisCommService;
import gov.utah.dts.det.query.SortBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningMisCommService")
public class TrackingRecordScreeningMisCommServiceImpl implements TrackingRecordScreeningMisCommService {

	@Autowired
	TrackingRecordScreeningMisCommDao trackingRecordScreeningMisCommDao;

	@Override
	public List<TrackingRecordScreeningMisComm> getMisCommForScreening(Long screeningId, SortBy sortBy) {
		return trackingRecordScreeningMisCommDao.getMisCommForScreening(screeningId, sortBy);
	}

	@Override
	public TrackingRecordScreeningMisComm save(TrackingRecordScreeningMisComm entity) {
		return trackingRecordScreeningMisCommDao.save(entity);
	}

	@Override
	public TrackingRecordScreeningMisComm load(Long id) {
		return trackingRecordScreeningMisCommDao.load(id);
	}

	@Override
	public void delete(Long id) {
		trackingRecordScreeningMisCommDao.delete(id);
	}

	@Override
	public void evict(TrackingRecordScreeningMisComm entity) {
		trackingRecordScreeningMisCommDao.evict(entity);
	}

}
