package gov.utah.dts.det.ccl.actions.facility.incidents;

import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.ccl.view.JsonResponse;

@SuppressWarnings("serial")
public class UnfinalizeAction extends IncidentStateChangeAction {

	@Override
	public void handleExecute() throws Exception {
		Incident i = super.getIncident();
		incidentService.unfinalizeIncident(i, note);
		response = new JsonResponse(200);
	}
}