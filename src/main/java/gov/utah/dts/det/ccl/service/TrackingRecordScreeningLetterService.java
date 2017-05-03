package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLetter;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningLetterService {

	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')")
	public List<TrackingRecordScreeningLetter> getLettersForScreening(Long screeningId, SortBy sortBy);

	public TrackingRecordScreeningLetter save(TrackingRecordScreeningLetter entity);
	
	public TrackingRecordScreeningLetter load(Long id);
	
	public void delete(Long id);

	public void delete(TrackingRecordScreeningLetter entity);

	public void evict(TrackingRecordScreeningLetter entity);

}
