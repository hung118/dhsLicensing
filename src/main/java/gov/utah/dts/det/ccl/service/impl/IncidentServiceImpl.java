package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.IncidentDao;
import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.model.view.IncidentView;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.IncidentService;
import gov.utah.dts.det.ccl.service.NoteService;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("incidentService")
public class IncidentServiceImpl implements IncidentService {
	
	@Autowired
	private IncidentDao incidentDao;
	
	@Autowired
	private NoteService noteService;
	
	@Override
	public Incident loadById(Long id) {
		return incidentDao.load(id);
	}
	
	@Override
	public Incident createIncident(Incident incident) {
		incident.setState(Incident.State.ENTRY, Incident.StateChange.CREATED, null);
		return incidentDao.save(incident);
	}
	
	@Override
	public Incident saveIncident(Incident incident) throws CclServiceException {
		List<String> errorCodes = new ArrayList<String>();
		if (incident.getState() == Incident.State.FINALIZED) {
			errorCodes.add("error.incident.save.finalized");
		}
		
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to save incident", errorCodes);
		}
		
		return incidentDao.save(incident);
	}
	
	@Override
	public Incident completeEntry(Incident incident, String note) throws CclServiceException {
		List<String> errorCodes = validateIncident(incident);
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to complete incident intake", errorCodes);
		}
		
		incident.setState(Incident.State.APPROVAL, Incident.StateChange.ENTRY_COMPLETED, note);
		return incidentDao.save(incident);
	}
	
	@Override
	public Incident rejectEntry(Incident incident, String note) throws CclServiceException {
		if (StringUtils.isBlank(note)) {
			throw new CclServiceException("Unable to reject intake", "Note is required.");
		}
		incident.setState(Incident.State.ENTRY, Incident.StateChange.ENTRY_REJECTED, note);
		return incidentDao.save(incident);
	}
	
	@Override
	public Incident finalizeIncident(Incident incident, String note) throws CclServiceException {
		List<String> errorCodes = validateIncident(incident);
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to finalize incident", errorCodes);
		}

		incident.setState(Incident.State.FINALIZED, Incident.StateChange.FINALIZED, note);
		return incidentDao.save(incident);
	}
	
	@Override
	public Incident unfinalizeIncident(Incident incident, String note) {
		incident.setState(Incident.State.APPROVAL, Incident.StateChange.UNFINALIZED, note);
		return incidentDao.save(incident);
	}
	
	private List<String> validateIncident(Incident incident) {
		List<String> errorCodes = new ArrayList<String>();
		if (incident.getDate() == null) {
			errorCodes.add("error.incident.finalize.incident-date-required");
		}
		if (incident.getInjuryType() == null) {
			errorCodes.add("error.incident.finalize.injury-type-required");
		}
		if (incident.getDeath() == null) {
			errorCodes.add("error.incident.finalize.death-required");
		}
		if (incident.getChild() == null || StringUtils.isEmpty(incident.getChild().getFirstName())) {
			errorCodes.add("error.incident.finalize.child-first-name-required");
		}
		if (incident.getChild() == null || StringUtils.isEmpty(incident.getChild().getLastName())) {
			errorCodes.add("error.incident.finalize.child-last-name-required");
		}
		if (incident.getReportedOverPhone() == null) {
			errorCodes.add("error.incident.finalize.reported-over-phone-required");
		}
		if (incident.getSentWrittenReport() == null) {
			errorCodes.add("error.incident.finalize.sent-written-report-required");
		}
		
		return errorCodes;
	}
	
	@Override
	public void deleteIncident(Incident incident) {
		List<String> errorCodes = new ArrayList<String>();
		if (incident.getState() == Incident.State.FINALIZED) {
			errorCodes.add("error.incident.delete.incident-finalized");
		}
		List<Note> notes = noteService.getNotesForObject(incident.getId(), NoteType.INCIDENT_AND_INJURY, null, null);
		if (!notes.isEmpty()) {
			errorCodes.add("error.incident.delete.notes-not-empty");
		}
		
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to delete incident", errorCodes);
		}
		
		incidentDao.delete(incident);
	}
	
	@Override
	public List<IncidentView> getIncidentsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized) {
		return incidentDao.getIncidentsForFacility(facilityId, listRange, sortBy, finalized);
	}
	
	@Override
	public void evict(final Object entity) {
		incidentDao.evict(entity);
	}
}