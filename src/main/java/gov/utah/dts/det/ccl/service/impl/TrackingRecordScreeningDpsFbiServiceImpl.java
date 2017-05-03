package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.TrackingRecordScreeningDpsFbiDao;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningDpsFbi;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningDpsFbiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author jtorres
 * 
 */
@Service("trackingRecordScreeningDpsFbiService")
public class TrackingRecordScreeningDpsFbiServiceImpl implements TrackingRecordScreeningDpsFbiService {

  @Autowired
  TrackingRecordScreeningDpsFbiDao dao;

  @Override
  public TrackingRecordScreeningDpsFbi save(TrackingRecordScreeningDpsFbi entity) {
    return dao.save(entity);
  }

  @Override
  public TrackingRecordScreeningDpsFbi load(Long id) {
    return dao.load(id);
  }

  @Override
  public void delete(TrackingRecordScreeningDpsFbi entity) {
    dao.delete(entity);

  }
}
