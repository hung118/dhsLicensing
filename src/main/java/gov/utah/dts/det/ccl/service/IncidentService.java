package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.model.view.IncidentView;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface IncidentService {
	
	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST')")
	public Incident loadById(Long id);
	
	@PreAuthorize(value = "hasPermission(#incident, 'create')")
	public Incident createIncident(Incident incident);
	
	@PreAuthorize(value = "hasPermission(#incident, 'save-entry')")
	public Incident saveIncident(Incident incident) throws CclServiceException;

	@PreAuthorize(value = "hasPermission(#incident, 'complete-entry')")
	public Incident completeEntry(Incident incident, String note) throws CclServiceException;

	@PreAuthorize(value = "hasPermission(#incident, 'reject-entry')")
	public Incident rejectEntry(Incident incident, String note) throws CclServiceException;
	
	@PreAuthorize(value = "hasPermission(#incident, 'finalize')")
	public Incident finalizeIncident(Incident incident, String note) throws CclServiceException;
	
	@PreAuthorize(value = "hasPermission(#incident, 'unfinalize')")
	public Incident unfinalizeIncident(Incident incident, String note);

	@PreAuthorize(value = "hasPermission(#incident, 'delete')")
	public void deleteIncident(Incident incident);

	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST')")
	public List<IncidentView> getIncidentsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized);
	
	public void evict(final Object entity);
}