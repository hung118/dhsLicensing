package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.ActionLog;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface ActionLogService {

	public ActionLog loadById(Long id);

	public ActionLog saveActionLog(ActionLog actionLog, Date dueDate);
	
	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
	public void deleteActionLog(ActionLog actionLog);

	public List<ActionLog> getActionLogsForFacility(Long facilityId, PickListValue actionType, ListRange listRange, SortBy sortBy);
	
	public String getActionAlertJSON(Long actionId, Facility facility);
	
	public void evict(final Object entity);
}