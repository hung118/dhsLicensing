package gov.utah.dts.det.ccl.dao;

import java.util.List;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConviction;
import gov.utah.dts.det.dao.AbstractBaseDao;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningConvictionDao extends AbstractBaseDao<TrackingRecordScreeningConviction, Long> {

	public List<TrackingRecordScreeningConviction> getConvictionsForScreening(Long screeningId);

	public List<TrackingRecordScreeningConviction> getConvictionsForLetter(Long screeningId);
}
