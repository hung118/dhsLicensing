package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLetter;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningLetterDao extends AbstractBaseDao<TrackingRecordScreeningLetter, Long> {

	public List<TrackingRecordScreeningLetter> getLettersForScreening(Long screeningId, SortBy sortBy);

}
