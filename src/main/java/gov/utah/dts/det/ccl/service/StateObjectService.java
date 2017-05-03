package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.StateChange;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface StateObjectService {

	@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
	public List<StateChange> getObjectStateChanges(Long objectId);
}