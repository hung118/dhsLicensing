package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.ComplaintScreening;
import gov.utah.dts.det.ccl.model.view.ComplaintAllegationView;
import gov.utah.dts.det.ccl.model.view.ComplaintView;
import gov.utah.dts.det.ccl.model.view.ComplaintsInProgressView;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.ccl.view.facility.StatewideUnlicensedComplaintView;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface ComplaintService {
	
	public Complaint loadById(Long id);
	
	@PreAuthorize(value = "hasPermission(#complaint, 'create')")
	public Complaint createComplaint(Complaint complaint);
	
	//TODO: remove this method after substitues are added
	@PreAuthorize(value = "hasPermission(#complaint, 'save-intake')")
	public Complaint saveComplaint(Complaint complaint);
	
	@PreAuthorize(value = "hasPermission(#complaint, 'save-intake')")
	public Complaint saveIntake(Complaint complaint);

	@PreAuthorize(value = "hasPermission(#complaint, 'complete-intake')")
	public Complaint completeIntake(Complaint complaint, String note) throws CclServiceException;
	
	@PreAuthorize(value = "hasPermission(#complaint, 'finalize')")
	public Complaint finalizeComplaint(Complaint complaint, String note) throws CclServiceException;
	
	@PreAuthorize(value = "hasPermission(#complaint, 'delete')")
	public void deleteComplaint(Complaint complaint);
	
	public List<ComplaintView> getComplaintsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized);
	
	public List<Complaint> getComplaintsInProgress(Long facilityId);

	public List<ComplaintsInProgressView> getComplaintsInProgress(Long personId, String role, SortBy sortBy);
	
	public List<ComplaintAllegationView> getAllegationViews(Long complaintId);
	
	public List<StatewideUnlicensedComplaintView> getStatewideUnlicensedComplaints(ListRange listRange, SortBy sortBy, int page, int resultsPerPage);
	
	public int getStatewideUnlicensedComplaintsCount(ListRange listRange);
	
	public void evict(final Object entity);
}