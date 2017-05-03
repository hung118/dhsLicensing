package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningCbsCommDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCbsComm;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningCbsCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningCbsCommService")
public class TrackingRecordScreeningCbsCommServiceImpl implements TrackingRecordScreeningCbsCommService {

	@Autowired
	TrackingRecordScreeningCbsCommDao dao;

	@Override
	public TrackingRecordScreeningCbsComm save(TrackingRecordScreeningCbsComm entity) {
		return dao.save(entity);
	}

	@Override
	public TrackingRecordScreeningCbsComm load(Long id) {
		return dao.load(id);
	}
	
	@Override
	public void delete(TrackingRecordScreeningCbsComm entity) {
		dao.delete(entity);
	}
	
	@Override
	public void evict(TrackingRecordScreeningCbsComm entity) {
		dao.evict(entity);
	}

}
