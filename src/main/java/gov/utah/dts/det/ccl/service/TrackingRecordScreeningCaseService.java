package gov.utah.dts.det.ccl.service;

import java.util.List;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCase;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningCaseService {
	
	public List<TrackingRecordScreeningCase> getCasesForScreening(Long screeningId);

	public TrackingRecordScreeningCase save(TrackingRecordScreeningCase entity);
	
	public TrackingRecordScreeningCase load(Long id);
	
	public void delete(Long id);
	
	public void evict(TrackingRecordScreeningCase entity);

}
