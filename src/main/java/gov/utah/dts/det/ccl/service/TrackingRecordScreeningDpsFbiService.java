package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningDpsFbi;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningDpsFbiService {

  public TrackingRecordScreeningDpsFbi save(TrackingRecordScreeningDpsFbi entity);

  public TrackingRecordScreeningDpsFbi load(Long id);

  public void delete(TrackingRecordScreeningDpsFbi entity);
}
