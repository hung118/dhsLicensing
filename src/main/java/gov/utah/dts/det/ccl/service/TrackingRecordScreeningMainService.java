package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMain;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningMainService {

  public TrackingRecordScreeningMain save(TrackingRecordScreeningMain entity);

  public TrackingRecordScreeningMain load(Long id);

  public void delete(TrackingRecordScreeningMain entity);
  
  public void evict(TrackingRecordScreeningMain entity);

}
