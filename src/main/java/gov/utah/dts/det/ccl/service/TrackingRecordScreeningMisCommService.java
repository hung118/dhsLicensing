package gov.utah.dts.det.ccl.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMisComm;
import gov.utah.dts.det.query.SortBy;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningMisCommService {

	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')")
	public List<TrackingRecordScreeningMisComm> getMisCommForScreening(Long screeningId, SortBy sortBy);

	public TrackingRecordScreeningMisComm save(TrackingRecordScreeningMisComm entity);

	public TrackingRecordScreeningMisComm load(Long id);

	public void delete(Long id);
	
	public void evict(TrackingRecordScreeningMisComm entity);
}
