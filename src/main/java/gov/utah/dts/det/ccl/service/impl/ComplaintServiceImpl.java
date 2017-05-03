/**
 * 
 */
package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.ComplaintDao;
import gov.utah.dts.det.ccl.model.*;
import gov.utah.dts.det.ccl.model.enums.NameUsage;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.model.view.ComplaintAllegationView;
import gov.utah.dts.det.ccl.model.view.ComplaintView;
import gov.utah.dts.det.ccl.model.view.ComplaintsInProgressView;
import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.NoteService;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.ccl.view.facility.StatewideUnlicensedComplaintView;
import gov.utah.dts.det.query.SortBy;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author danolsen
 * 
 */
@Service("complaintService")
public class ComplaintServiceImpl implements ComplaintService {
	
	@Autowired
	private ComplaintDao complaintDao;
	
	@Autowired
	private NoteService noteService;
	
	@Override
	public Complaint loadById(Long id) {
		return complaintDao.load(id);
	}
	
	@Override
	public Complaint createComplaint(Complaint complaint) {
		complaint.setState(Complaint.State.INTAKE, Complaint.StateChange.CREATED, null);
		return complaintDao.save(complaint);
	}
	
	@Override
	public Complaint saveComplaint(Complaint complaint) {
		return complaintDao.save(complaint);
	}
	
	@Override
	public Complaint saveIntake(Complaint complaint) {
		return complaintDao.save(complaint);
	}
	
	@Override
	public Complaint completeIntake(Complaint complaint, String note) throws CclServiceException {
		//if (complaint.getComplainant().getNameUsage() != NameUsage.NAME_REFUSED) {
			List<String> errorCodes = new ArrayList<String>();
			
			if (StringUtils.isEmpty(complaint.getNarrative())) {
				errorCodes.add("error.complaint.complete-intake.narrative-required");
			}
			if (complaint.getComplainant() == null) {
				errorCodes.add("error.complaint.complete-intake.complainant-required");
			}
			
			if (!errorCodes.isEmpty()) {
				throw new CclServiceException("Unable to complete complaint intake", errorCodes);
			}
		//}
		
		complaint.setState(Complaint.State.FINALIZED, Complaint.StateChange.FINALIZED, note);
		
		return complaintDao.save(complaint);
	}
	
	@Override
	public Complaint finalizeComplaint(Complaint complaint, String note) throws CclServiceException {
		List<String> errorCodes = null;
		if (complaint.getComplainant().getNameUsage() != NameUsage.NAME_REFUSED) {
			errorCodes = validateComplaintApproval(complaint);
		}
		
		if (errorCodes != null && !errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to finalize complaint", errorCodes);
		}
		
		complaint.setState(Complaint.State.FINALIZED, Complaint.StateChange.FINALIZED, note);
		return complaintDao.save(complaint);
	}

	private List<String> validateComplaintApproval(Complaint complaint) {
		List<String> errorCodes = new ArrayList<String>();
		
		if (complaint.getScreening() == null) {
			errorCodes.add("The complaint must be screened by a manager.");
		} else if (complaint.getScreening().isProceedWithInvestigation()) {
			if (complaint instanceof UnlicensedComplaint) {
				UnlicensedComplaint uCompl = (UnlicensedComplaint) complaint;
				if (uCompl.getInvestigationCompletedDate() == null) {
					errorCodes.add("The investigation must be filled out since the investigation is proceeding.");
				}
			} else {
				if (complaint.getInspections().isEmpty()) {
					errorCodes.add("There must be at least one inspection performed since the investigation is proceeding.");
				} else {
					for (Inspection i : complaint.getInspections()) {
						if (i.getState() != Inspection.State.FINALIZED) {
							errorCodes.add("All complaint investigations must be finalized.");
							break;
						}
					}
					
					List<ComplaintAllegationView> allegations = getAllegationViews(complaint.getId());
					for (ComplaintAllegationView allegation : allegations) {
						if (allegation.getSubstantiated() && StringUtils.isBlank(allegation.getSupportingEvidence())) {
							errorCodes.add("Supporting evidence must be provided for all substantiated allegations.");
						}
					}
				}
			}
		}
		
		return errorCodes;
	}
	
	@Override
	public void deleteComplaint(Complaint complaint) {
		List<String> errorCodes = new ArrayList<String>();
		if (complaint.getState() == Complaint.State.FINALIZED) {
			errorCodes.add("error.complaint.delete.complaint-approved");
		}
		List<Note> notes = noteService.getNotesForObject(complaint.getId(), NoteType.COMPLAINT, null, null);
		if (!notes.isEmpty()) {
			errorCodes.add("error.complaint.delete.notes-not-empty");
		}
		
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to delete complaint", errorCodes);
		}
		
		complaintDao.delete(complaint);
	}
	
	@Override
	public List<ComplaintView> getComplaintsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized) {
		return complaintDao.getComplaintsForFacility(facilityId, listRange, sortBy, finalized);
	}
	
	@Override
	public List<Complaint> getComplaintsInProgress(Long facilityId) {
		return complaintDao.getComplaintsInProgress(facilityId);
	}
	
	@Override
	public List<ComplaintsInProgressView> getComplaintsInProgress(Long personId, String role, SortBy sortBy) {
		return complaintDao.getComplaintsInProgress(personId, role, sortBy);
	}
	
	@Override
	public List<ComplaintAllegationView> getAllegationViews(Long complaintId) {
		return complaintDao.getAllegationViews(complaintId);
	}
	
	@Override
	public List<StatewideUnlicensedComplaintView> getStatewideUnlicensedComplaints(ListRange listRange, SortBy sortBy, int page, int resultsPerPage) {
		return complaintDao.getStatewideUnlicensedComplaints(listRange, sortBy, page, resultsPerPage);
	}
	
	@Override
	public int getStatewideUnlicensedComplaintsCount(ListRange listRange) {
		return complaintDao.getStatewideUnlicensedComplaintCount(listRange);
	}
	
	@Override
	public void evict(Object entity) {
		complaintDao.evict(entity);
	}
}