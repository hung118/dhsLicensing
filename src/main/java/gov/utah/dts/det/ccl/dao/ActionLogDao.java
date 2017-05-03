/**
 * $Rev: 7 $:
 * $LastChangedDate: 2009-02-18 12:32:20 -0700 (Wed, 18 Feb 2009) $:
 * $Author: danolsen $:
 */
package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.ActionLog;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface ActionLogDao extends AbstractBaseDao<ActionLog, Long> {
	
	public List<ActionLog> getActionLogsForFacility(Long facilityId, PickListValue actionType, ListRange listRange, SortBy sortBy);
}