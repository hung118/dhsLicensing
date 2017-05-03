package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningMainDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMain;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningMainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningMainService")
public class TrackingRecordScreeningMainServiceImpl implements TrackingRecordScreeningMainService {

  @Autowired
  TrackingRecordScreeningMainDao dao;

  @Override
  public TrackingRecordScreeningMain save(TrackingRecordScreeningMain entity) {
    return dao.save(entity);
  }

  @Override
  public TrackingRecordScreeningMain load(Long id) {
    return dao.load(id);
  }

  @Override
  public void delete(TrackingRecordScreeningMain entity) {
    dao.delete(entity);

  }
  
  @Override
  public void evict(TrackingRecordScreeningMain entity) {
	  dao.evict(entity);
  }

}
