package gov.utah.dts.det.ccl.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningOscar;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningOscarService {
	
	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')")
	public List<TrackingRecordScreeningOscar> getOscarForScreening(Long screeningId, SortBy sortBy);

	public TrackingRecordScreeningOscar save(TrackingRecordScreeningOscar entity);

	public TrackingRecordScreeningOscar load(Long id);

	public void delete(Long id);
	
	public void evict(TrackingRecordScreeningOscar entity);
}
