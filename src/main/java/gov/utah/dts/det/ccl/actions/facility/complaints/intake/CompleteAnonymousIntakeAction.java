package gov.utah.dts.det.ccl.actions.facility.complaints.intake;

import org.springframework.dao.DataIntegrityViolationException;

import gov.utah.dts.det.ccl.actions.facility.complaints.ComplaintStateChangeAction;
import gov.utah.dts.det.ccl.model.ComplaintComplainant;
import gov.utah.dts.det.ccl.model.enums.NameUsage;
import gov.utah.dts.det.ccl.view.JsonResponse;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public class CompleteAnonymousIntakeAction extends ComplaintStateChangeAction implements Preparable {

	private ComplaintComplainant complainant;
	
	@Override
	public void prepare() throws Exception {
		if (super.getComplaint().getComplainant() != null) {
			complainant = super.getComplaint().getComplainant();
		}
	}
	
	@Override
	public void handleExecute() throws Exception {
		try {
			if (complainant.getId() == null) {
				super.getComplaint().setComplainant(complainant);
	//			complainant.setComplaint(super.getComplaint());
			}
			
			complainant.setNameUsage(NameUsage.NAME_REFUSED);
			complaintService.completeIntake(super.getComplaint(), note);
			response = new JsonResponse(200);
		} catch (DataIntegrityViolationException dive) {
			response = new JsonResponse(400, "Relationship to Provider is required.");
		}
	}
	
	public ComplaintComplainant getComplainant() {
		return complainant;
	}
	
	public void setComplainant(ComplaintComplainant complainant) {
		this.complainant = complainant;
	}
}