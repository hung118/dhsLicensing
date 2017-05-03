package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.view.ComplaintAllegationView;
import gov.utah.dts.det.ccl.model.view.ComplaintView;
import gov.utah.dts.det.ccl.model.view.ComplaintsInProgressView;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.ccl.view.facility.StatewideUnlicensedComplaintView;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.List;

public interface ComplaintDao extends AbstractBaseDao<Complaint, Long> {

	public List<ComplaintView> getComplaintsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized);
	
	public List<Complaint> getComplaintsInProgress(Long facilityId);
	
	public List<ComplaintsInProgressView> getComplaintsInProgress(Long personId, String role, SortBy sortBy);
	
	public List<ComplaintAllegationView> getAllegationViews(Long complaintId);
	
	public List<StatewideUnlicensedComplaintView> getStatewideUnlicensedComplaints(ListRange listRange, SortBy sortBy, int page, int resultsPerPage);
	
	public int getStatewideUnlicensedComplaintCount(ListRange listRange);
	
}