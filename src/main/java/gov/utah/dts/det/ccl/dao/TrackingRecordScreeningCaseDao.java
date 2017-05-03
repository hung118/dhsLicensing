package gov.utah.dts.det.ccl.dao;

import java.util.List;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCase;
import gov.utah.dts.det.dao.AbstractBaseDao;

/**
 * 
 * @author jtorres
 * 
 */
public interface TrackingRecordScreeningCaseDao extends AbstractBaseDao<TrackingRecordScreeningCase, Long> {

	public List<TrackingRecordScreeningCase> getCasesForScreening(Long screeningId);

}
