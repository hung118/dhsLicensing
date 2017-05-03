package gov.utah.dts.det.ccl.service.impl;

import java.util.List;
import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningConvictionDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConviction;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningConvictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningConvictionService")
public class TrackingRecordScreeningConvictionServiceImpl extends GenericServiceImpl<TrackingRecordScreeningConviction, Long> implements
TrackingRecordScreeningConvictionService {

	@Autowired
	TrackingRecordScreeningConvictionDao trackingRecordScreeningConvictionDao;

	@Override
	public List<TrackingRecordScreeningConviction> getConvictionsForScreening(Long screeningId) {
		return trackingRecordScreeningConvictionDao.getConvictionsForScreening(screeningId);
	}

	@Override
	public List<TrackingRecordScreeningConviction> getConvictionsForLetter(Long screeningId) {
		return trackingRecordScreeningConvictionDao.getConvictionsForLetter(screeningId);
	}

	@Override
	public TrackingRecordScreeningConviction save(TrackingRecordScreeningConviction entity) {
		return trackingRecordScreeningConvictionDao.save(entity);
	}

	@Override
	public TrackingRecordScreeningConviction load(Long id) {
		return trackingRecordScreeningConvictionDao.load(id);
	}
	
	@Override
	public void delete(Long id) {
		trackingRecordScreeningConvictionDao.delete(id);
	}
	
	@Override
	public void evict(TrackingRecordScreeningConviction entity) {
		trackingRecordScreeningConvictionDao.evict(entity);
	}
}
