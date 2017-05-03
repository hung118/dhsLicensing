package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.view.ComplaintView;
import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.ccl.sort.enums.ComplaintSortBy;
import gov.utah.dts.det.ccl.view.ListRange;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "complaints-list", type = "redirectAction", params = {"facilityId", "${facilityId}", "inspectionId", "${inspectionId}"}),
	@Result(name = "success", location = "complaints_list.jsp")
})
public class ComplaintAction extends BaseInspectionAction {

	private ComplaintService complaintService;
	
	private Long complaintId;
	
	private List<ComplaintView> complaints;
	
	@Action(value = "complaints-list")
	public String doList() {
		PickListValue complaintType = inspectionService.getComplaintInvestigationType();
		if (getInspection() == null || !getInspection().hasInspectionType(complaintType)) {
			return null;
		}
		
		complaints = complaintService.getComplaintsForFacility(getFacility().getId(), ListRange.SHOW_ALL, ComplaintSortBy.DATE_RECEIVED, false);
		for (Complaint complaint : getInspection().getComplaints()) {
			for (ComplaintView cv : complaints) {
				if (cv.getId().equals(complaint.getId())) {
					complaints.remove(cv);
					break;
				}
			}
		}
		
		return SUCCESS;
	}
	
	@Action(value = "add-complaint")
	public String doAddComplaint() {
		if (complaintId != null) {
			Complaint complaint = complaintService.loadById(complaintId);
			getInspection().getComplaints().add(complaint);
			inspectionService.saveInspection(getInspection());
		}
		
		return REDIRECT_VIEW;
	}
	
	@Action(value = "remove-complaint")
	public String doRemoveComplaint() {
		if (complaintId != null) {
			Complaint complaint = complaintService.loadById(complaintId);
			getInspection().getComplaints().remove(complaint);
			inspectionService.saveInspection(getInspection());
		}
		
		return REDIRECT_VIEW;
	}
	
	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	public Long getComplaintId() {
		return complaintId;
	}
	
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	
	@Override
	public Inspection getInspection() {
		return super.getInspection();
	}
	
	public List<ComplaintView> getComplaints() {
		return complaints;
	}
}