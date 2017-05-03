package gov.utah.dts.det.ccl.actions.facility.complaints;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.UnlicensedComplaint;
import gov.utah.dts.det.ccl.security.permissions.ComplaintPermissionEvaluator;
import gov.utah.dts.det.ccl.security.permissions.UnlicensedComplaintPermissionEvaluator;
import gov.utah.dts.det.ccl.service.ComplaintService;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

@SuppressWarnings("serial")
public class BaseComplaintAction extends BaseFacilityAction {
	
	protected ComplaintService complaintService;
	protected ComplaintPermissionEvaluator complaintPermissionEvaluator;
	protected UnlicensedComplaintPermissionEvaluator unlicensedComplaintPermissionEvaluator;
	private List<PickListValue> conclusionTypes;
	
	private Complaint complaint;
	
	private Long complaintId;
	
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	public void setComplaintPermissionEvaluator(ComplaintPermissionEvaluator complaintPermissionEvaluator) {
		this.complaintPermissionEvaluator = complaintPermissionEvaluator;
	}
	
	public void setUnlicensedComplaintPermissionEvaluator(UnlicensedComplaintPermissionEvaluator unlicensedComplaintPermissionEvaluator) {
		this.unlicensedComplaintPermissionEvaluator = unlicensedComplaintPermissionEvaluator;
	}
	
	public Facility getFacility() {
		return super.getFacility();
	}
	
	protected Complaint getComplaint() {
		if (complaint == null && complaintId != null) {
			complaint = complaintService.loadById(complaintId);
		}
		return complaint;
	}
	
	protected List<PickListValue> getConclusionTypes() {
		if (conclusionTypes == null) {
			conclusionTypes = pickListService.getValuesForPickList("Conclusion", true);
		}
		return conclusionTypes;
	}
	
	public Long getComplaintId() {
		return complaintId;
	}
	
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	
	public boolean isUnlicensed() {
		if (getComplaint() != null && getComplaint().getClass() == UnlicensedComplaint.class) {
			return true;
		}
		return false;
	}
	
	protected boolean hasPermission(String permission) {
		if (isUnlicensed()) {
			return unlicensedComplaintPermissionEvaluator.hasPermission(SecurityContextHolder.getContext().getAuthentication(), getComplaint(), permission);
		} else {
			return complaintPermissionEvaluator.hasPermission(SecurityContextHolder.getContext().getAuthentication(), getComplaint(), permission);
		}
	}
}