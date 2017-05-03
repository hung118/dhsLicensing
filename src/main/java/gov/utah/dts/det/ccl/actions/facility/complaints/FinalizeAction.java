package gov.utah.dts.det.ccl.actions.facility.complaints;

import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.view.JsonResponse;

@SuppressWarnings("serial")
public class FinalizeAction extends ComplaintStateChangeAction {

	@Override
	public void handleExecute() throws Exception {
		Complaint c = super.getComplaint();
		complaintService.finalizeComplaint(c, note);
		response = new JsonResponse(200);
	}
}