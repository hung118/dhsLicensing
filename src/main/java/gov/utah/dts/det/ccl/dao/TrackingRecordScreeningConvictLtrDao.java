package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConvictionLetter;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningConvictLtrDao extends AbstractBaseDao<TrackingRecordScreeningConvictionLetter, Long> {

	public List<TrackingRecordScreeningConvictionLetter> getConvictionLettersForScreening(Long screeningId, SortBy sortBy);

}
