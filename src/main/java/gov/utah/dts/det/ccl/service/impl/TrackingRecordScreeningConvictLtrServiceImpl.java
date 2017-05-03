package gov.utah.dts.det.ccl.service.impl;

import java.util.List;
import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningConvictLtrDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConvictionLetter;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningConvictLtrService;
import gov.utah.dts.det.query.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningConvictLtrService")
public class TrackingRecordScreeningConvictLtrServiceImpl implements TrackingRecordScreeningConvictLtrService {

	@Autowired
	TrackingRecordScreeningConvictLtrDao dao;

	@Override
	public List<TrackingRecordScreeningConvictionLetter> getConvictionLettersForScreening(Long screeningId, SortBy sortBy) {
		return dao.getConvictionLettersForScreening(screeningId, sortBy);
	}

	@Override
	public TrackingRecordScreeningConvictionLetter save(TrackingRecordScreeningConvictionLetter entity) {
		return dao.save(entity);
	}

	@Override
	public TrackingRecordScreeningConvictionLetter load(Long id) {
		return dao.load(id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(id);
	}
	
	@Override
	public void evict(TrackingRecordScreeningConvictionLetter entity) {
		dao.evict(entity);
	}

}
