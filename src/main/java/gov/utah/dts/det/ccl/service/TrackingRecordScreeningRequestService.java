package gov.utah.dts.det.ccl.service;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningRequests;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningRequestService {

	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')")
	public List<TrackingRecordScreeningRequests> getRequestsForScreening(Long screeningId, SortBy sortBy);

	public TrackingRecordScreeningRequests save(TrackingRecordScreeningRequests entity);
	
	public TrackingRecordScreeningRequests load(Long id);
	
	public void delete(Long id);
	
	public void evict(TrackingRecordScreeningRequests entity);

}
