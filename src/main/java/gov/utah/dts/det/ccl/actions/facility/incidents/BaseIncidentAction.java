package gov.utah.dts.det.ccl.actions.facility.incidents;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.service.IncidentService;

@SuppressWarnings("serial")
public class BaseIncidentAction extends BaseFacilityAction {

	protected IncidentService incidentService;
	
	private Incident incident;
	
	private Long incidentId;
	
	public void setIncidentService(IncidentService incidentService) {
		this.incidentService = incidentService;
	}
	
	protected Incident getIncident() {
		if (incident == null && incidentId != null) {
			incident = incidentService.loadById(incidentId);
		}
		return incident;
	}
	
	public Long getIncidentId() {
		return incidentId;
	}
	
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
}