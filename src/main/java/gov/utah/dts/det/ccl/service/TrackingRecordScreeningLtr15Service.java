package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLtr15;
import gov.utah.dts.det.query.SortBy;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningLtr15Service {

	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')")
	public List<TrackingRecordScreeningLtr15> get15DayLettersForScreening(Long screeningId, SortBy sortBy);

	public TrackingRecordScreeningLtr15 save(TrackingRecordScreeningLtr15 entity);
	
	public TrackingRecordScreeningLtr15 load(Long id);
	
	public void delete(Long id);
	
	public void evict(TrackingRecordScreeningLtr15 entity);

}
