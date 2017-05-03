package gov.utah.dts.det.ccl.service;

import java.util.List;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConvictionLetter;
import gov.utah.dts.det.query.SortBy;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningConvictLtrService {

	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_BACKGROUND_SCREENING_MANAGER','ROLE_BACKGROUND_SCREENING')")
	public List<TrackingRecordScreeningConvictionLetter> getConvictionLettersForScreening(Long screeningId, SortBy sortBy);

	public TrackingRecordScreeningConvictionLetter save(TrackingRecordScreeningConvictionLetter entity);
	
	public TrackingRecordScreeningConvictionLetter load(Long id);
	
	public void delete(Long id);
	
	public void evict(TrackingRecordScreeningConvictionLetter entity);

}
