package gov.utah.dts.det.ccl.service;

import java.util.List;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConviction;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningConvictionService {
	
	public List<TrackingRecordScreeningConviction> getConvictionsForScreening(Long screeningId);

	public List<TrackingRecordScreeningConviction> getConvictionsForLetter(Long screeningId);

	public TrackingRecordScreeningConviction save(TrackingRecordScreeningConviction entity);
	
	public TrackingRecordScreeningConviction load(Long id);
	
	public void delete(Long id);
	
	public void evict(TrackingRecordScreeningConviction entity);

}
