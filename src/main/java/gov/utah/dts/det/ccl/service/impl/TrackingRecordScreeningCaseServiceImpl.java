package gov.utah.dts.det.ccl.service.impl;

import java.util.List;
import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningCaseDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCase;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningCaseService")
public class TrackingRecordScreeningCaseServiceImpl extends GenericServiceImpl<TrackingRecordScreeningCase, Long> implements
TrackingRecordScreeningCaseService {

	@Autowired
	TrackingRecordScreeningCaseDao dao;

	@Override
	public List<TrackingRecordScreeningCase> getCasesForScreening(Long screeningId) {
		return dao.getCasesForScreening(screeningId);
	}

	@Override
	public TrackingRecordScreeningCase save(TrackingRecordScreeningCase entity) {
		return dao.save(entity);
	}

	@Override
	public TrackingRecordScreeningCase load(Long id) {
		return dao.load(id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(id);
	}
	
	@Override
	public void evict(TrackingRecordScreeningCase entity) {
		dao.evict(entity);
	}
}
