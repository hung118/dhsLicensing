package gov.utah.dts.det.ccl.actions.facility.complaints.intake;

import gov.utah.dts.det.ccl.actions.facility.complaints.ComplaintStateChangeAction;
import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.view.JsonResponse;

@SuppressWarnings("serial")
public class CompleteIntakeAction extends ComplaintStateChangeAction {

	@Override
	public void handleExecute() throws Exception {
		Complaint c = super.getComplaint();
		complaintService.completeIntake(c, note);
		response = new JsonResponse(200);
	}
}