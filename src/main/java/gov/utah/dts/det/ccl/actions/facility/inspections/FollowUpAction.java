package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.ccl.model.Finding;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.InspectionType;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.view.InspectionFollowUpView;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "follow-up-section", type = "redirectAction", params = {"facilityId", "${facilityId}", "inspectionId", "${inspectionId}"}),
	@Result(name = "input", location = "follow_up_form.jsp"),
	@Result(name = "view", location = "follow_up_detail.jsp")
})
public class FollowUpAction extends BaseInspectionAction {

	private List<InspectionFollowUpView> matrix;
	
	private List<Finding> followUps;
	private List<Finding> corrections;
	
	@Action(value = "follow-up-section")
	public String doSection() {
		boolean followUpType = false;
		for (InspectionType type : getInspection().getTypes()) {
			if (applicationService.propertyContainsPickListValue(type.getInspectionType(), ApplicationPropertyKey.FOLLOW_UP_TYPES.getKey())) {
				followUpType = true;
				break;
			}
		}
		
		if (followUpType) {
			matrix = inspectionService.getInspectionFollowUpMatrix(getInspectionId());
			if (matrix.isEmpty()) {
				return doEdit();
			}
			
			return VIEW;
		}
		
		return null;
	}
	
	@Action(value = "view-follow-up-matrix")
	public String doView() {
		matrix = inspectionService.getInspectionFollowUpMatrix(getInspectionId());
		return VIEW;
	}
	
	@Action(value = "edit-follow-up-matrix")
	public String doEdit() {
		matrix = inspectionService.getEditableInspectionFollowUpMatrix(getInspectionId());
		return INPUT;
	}
	
	@Action(value = "save-follow-up-matrix")
	public String doSave() {
		inspectionService.saveInspectionFollowUpMatrix(getInspectionId(), followUps, corrections);
		return REDIRECT_VIEW;
	}
	
	@Override
	public Inspection getInspection() {
		return super.getInspection();
	}
	
	public List<InspectionFollowUpView> getMatrix() {
		return matrix;
	}
	
	public List<Finding> getFollowUps() {
		return followUps;
	}
	
	public void setFollowUps(List<Finding> followUps) {
		this.followUps = followUps;
	}
	
	public List<Finding> getCorrections() {
		return corrections;
	}
	
	public void setCorrections(List<Finding> corrections) {
		this.corrections = corrections;
	}
}