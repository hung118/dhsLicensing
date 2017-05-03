package gov.utah.dts.det.ccl.service.impl;

import java.util.List;
import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningLtr15Dao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLtr15;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningLtr15Service;
import gov.utah.dts.det.query.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningLtr15Service")
public class TrackingRecordScreeningLtr15ServiceImpl implements TrackingRecordScreeningLtr15Service {

	@Autowired
	TrackingRecordScreeningLtr15Dao dao;

	@Override
	public List<TrackingRecordScreeningLtr15> get15DayLettersForScreening(Long screeningId, SortBy sortBy) {
		return dao.get15DayLettersForScreening(screeningId, sortBy);
	}

	@Override
	public TrackingRecordScreeningLtr15 save(TrackingRecordScreeningLtr15 entity) {
		return dao.save(entity);
	}

	@Override
	public TrackingRecordScreeningLtr15 load(Long id) {
		return dao.load(id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public void evict(TrackingRecordScreeningLtr15 entity) {
		dao.evict(entity);
	}
}
