package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.view.JsonResponse;

@SuppressWarnings("serial")
public class UnfinalizeAction extends InspectionStateChangeAction {

	@Override
	public void handleExecute() throws Exception {
		Inspection i = super.getInspection();
		inspectionService.unfinalizeInspection(i, note);
		response = new JsonResponse(200);
	}
}