package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCbsComm;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningCbsCommService {
	
	public TrackingRecordScreeningCbsComm load(Long id);

	public TrackingRecordScreeningCbsComm save(TrackingRecordScreeningCbsComm entity);
	
	public void delete(TrackingRecordScreeningCbsComm entity);
	
	public void evict(TrackingRecordScreeningCbsComm entity);

}
