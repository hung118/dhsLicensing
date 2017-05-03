package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.view.JsonResponse;

@SuppressWarnings("serial")
public class CompleteEntryAction extends InspectionStateChangeAction {

	private Person approver;
	
	@Override
	public void handleExecute() throws Exception {
		Inspection i = super.getInspection();
		inspectionService.completeEntry(i, approver, note);
		response = new JsonResponse(200);
	}
	
	public void setApprover(Person approver) {
		this.approver = approver;
	}
}