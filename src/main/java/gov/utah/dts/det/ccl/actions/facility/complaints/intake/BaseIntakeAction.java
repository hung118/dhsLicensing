package gov.utah.dts.det.ccl.actions.facility.complaints.intake;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.complaints.BaseComplaintAction;
import gov.utah.dts.det.ccl.model.Complaint;
import java.util.List;

@SuppressWarnings("serial")
public class BaseIntakeAction extends BaseComplaintAction {
	
	@Override
	public Complaint getComplaint() {
		return super.getComplaint();
	}
	
	@Override
	public List<PickListValue> getConclusionTypes() {
		return super.getConclusionTypes();
	}
}