package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.InspectionService;

@SuppressWarnings("serial")
public class BaseInspectionAction extends BaseFacilityAction {

	protected InspectionService inspectionService;
	
	protected Inspection inspection;
	
	protected Long inspectionId;
	
	public void setInspectionService(InspectionService inspectionService) {
		this.inspectionService = inspectionService;
	}
	
	protected Inspection getInspection() {
		if (inspection == null && inspectionId != null) {
			inspection = inspectionService.loadById(inspectionId);
		}
		return inspection;
	}
	
	protected void saveInspection() {
		inspection = inspectionService.saveInspection(inspection);
	}
	
	public Long getInspectionId() {
		return inspectionId;
	}
	
	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}
	
	public boolean isEditable() {
		return getInspection().getState() != Inspection.State.FINALIZED && (SecurityUtil.hasAnyRole(RoleType.ROLE_SUPER_ADMIN)
				|| (getInspection().getState() == Inspection.State.ENTRY && SecurityUtil.isEntityOwner(getInspection()))); 
	}
}