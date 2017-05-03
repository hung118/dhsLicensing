package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.dao.GenericDao;
import gov.utah.dts.det.ccl.dao.InspectionDao;
import gov.utah.dts.det.ccl.dao.NoncompliancePointsDao;
import gov.utah.dts.det.ccl.model.Attachment;
import gov.utah.dts.det.ccl.model.FacilityPerson;
import gov.utah.dts.det.ccl.model.Finding;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.InspectionChecklist;
import gov.utah.dts.det.ccl.model.InspectionChecklistHeader;
import gov.utah.dts.det.ccl.model.InspectionChecklistResult;
import gov.utah.dts.det.ccl.model.InspectionType;
import gov.utah.dts.det.ccl.model.NoncompliancePoints;
import gov.utah.dts.det.ccl.model.Note;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.ccl.model.enums.CorrectionVerificationType;
import gov.utah.dts.det.ccl.model.enums.NoteType;
import gov.utah.dts.det.ccl.model.view.InspectionView;
import gov.utah.dts.det.ccl.model.view.NoncomplianceFindingView;
import gov.utah.dts.det.ccl.model.view.NoncomplianceView;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.InspectionService;
import gov.utah.dts.det.ccl.service.NoncompliancePointMatrix;
import gov.utah.dts.det.ccl.service.NoteService;
import gov.utah.dts.det.ccl.service.RuleService;
import gov.utah.dts.det.ccl.service.RuleViolation;
import gov.utah.dts.det.ccl.view.InspectionFollowUpView;
import gov.utah.dts.det.ccl.view.InspectionRuleInfoView;
import gov.utah.dts.det.ccl.view.KeyValuePair;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.ccl.view.RuleResultView;
import gov.utah.dts.det.query.SortBy;
import gov.utah.dts.det.service.ApplicationService;
import gov.utah.dts.det.util.EntityUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("inspectionService")
public class InspectionServiceImpl extends BasicServiceImpl implements InspectionService, BasicService {

	protected final Log log = LogFactory.getLog(getClass());
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	@Autowired
	private InspectionDao inspectionDao;
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private RuleService ruleService;
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoncompliancePointsDao noncompliancePointsDao;
	
	@Override
	public Inspection loadById(Long id) {
		return inspectionDao.load(id);
	}
	
	@Override
	public Inspection createInspection(Inspection inspection) {
		inspection.setState(Inspection.State.ENTRY, Inspection.StateChange.CREATED, null);
		updateCompliance(inspection);
		return inspectionDao.save(inspection);
	}
	
	@Override
	public Inspection saveInspection(Inspection inspection) throws CclServiceException {
		List<String> errorCodes = new ArrayList<String>();
		if (inspection.getState() == Inspection.State.FINALIZED) {
			errorCodes.add("error.inspection.save.finalized");
		}
		
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to save inspection", errorCodes);
		}
		
//		updateFollowUpFlags(inspection);
		updateCmpDueDate(inspection);
		updateCompliance(inspection);
		return inspectionDao.save(inspection);
	}
	
	private Inspection saveWithoutFinalizationCheck(Inspection inspection) {
		updateCmpDueDate(inspection);
		updateCompliance(inspection);
		return inspectionDao.save(inspection);
	}
	
	@Override
	public Inspection completeEntry(Inspection inspection, Person approver, String note) throws CclServiceException {
		
		/* TODO [CKS:Jun 5, 2013] Redmine 20001
		if (approver == null) {
			throw new CclServiceException("Unable to complete entry", "Approver is required.");
		}
		*/
		approver = SecurityUtil.getSystemPerson();

		List<String> errorCodes = validateInspection(inspection);
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to complete entry", errorCodes);
		}
		
		inspection.setApprover(approver);
		inspection.setState(Inspection.State.APPROVAL, Inspection.StateChange.ENTRY_COMPLETED, "Inspection sent to " + approver.getFirstAndLastName() + " for approval." + (StringUtils.isNotBlank(note) ? "  " + note : ""));
		
		return inspectionDao.save(inspection);
	}
	
	@Override
	public Inspection rejectEntry(Inspection inspection, String note) throws CclServiceException {
		if (StringUtils.isBlank(note)) {
			throw new CclServiceException("Unable to reject inspection", "Note is required.");
		}
		
		inspection.setState(Inspection.State.ENTRY, Inspection.StateChange.ENTRY_REJECTED, note);
		return inspectionDao.save(inspection);
	}
	
	@Override
	public Inspection finalizeInspection(Inspection inspection, String note) {
		List<String> errorCodes = validateInspection(inspection);
		
		if (inspection.isCmpDueDateRequired() && inspection.getCmpDueDate() == null) {
			errorCodes.add("error.inspection.finalize.cmp-due-date-required");
		}
		
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to finalize inspection", errorCodes);
		}
		
		inspection.setState(Inspection.State.FINALIZED, Inspection.StateChange.FINALIZED, note);
		return inspectionDao.save(inspection);
	}
	
	@Override
	public Inspection unfinalizeInspection(Inspection inspection, String note) {
		inspection.setState(Inspection.State.APPROVAL, Inspection.StateChange.UNFINALIZED, note);
		return inspectionDao.save(inspection);
	}
	
	private List<String> validateInspection(Inspection inspection) {
		List<String> errorCodes = new ArrayList<String>();
		if (inspection.getHasFindings() && inspection.getFindings().isEmpty()) {
			errorCodes.add("error.inspection.finalize.findings-required");
		}
		
		boolean followUpTypeSelected = false;
		for (InspectionType type : inspection.getTypes()) {
			if (applicationService.propertyContainsPickListValue(type.getInspectionType(), ApplicationPropertyKey.FOLLOW_UP_TYPES.getKey())) {
				followUpTypeSelected = true;
				break;
			}
		}
		if (followUpTypeSelected && inspection.getFollowUps().isEmpty()) {
			errorCodes.add("error.inspection.finalize.follow-up-required");
		}
		
		return errorCodes;
	}
	
	@Override
	public void deleteInspection(Inspection inspection) {
		List<String> errorCodes = new ArrayList<String>();
		if (inspection.getState() == Inspection.State.FINALIZED) {
			errorCodes.add("error.inspection.delete.inspection-finalized");
		}
		if (!inspection.getFindings().isEmpty()) {
			errorCodes.add("error.inspection.delete.findings-not-empty");
		}
		if (!inspection.getComplaints().isEmpty()) {
			errorCodes.add("error.inspection.delete.complaints-not-empty");
		}
		if (!inspection.getFollowUps().isEmpty()) {
			errorCodes.add("error.inspection.delete.follow-ups-not-empty");
		}
		List<Note> notes = noteService.getNotesForObject(inspection.getId(), NoteType.INSPECTION, null, null);
		if (!notes.isEmpty()) {
			errorCodes.add("error.inspection.delete.notes-not-empty");
		}
		
		if (!errorCodes.isEmpty()) {
			throw new CclServiceException("Unable to delete inspection", errorCodes);
		}
		
		inspectionDao.delete(inspection);
	}
	
	@Override
	public void updateFindingStatus(Inspection inspection, Finding finding, Date rescindedDate, Date appealDate, CorrectionVerificationType verificationType) {
		if (!finding.isRescinded() && rescindedDate != null) {
			finding.rescind(rescindedDate);
		} else if (finding.isRescinded() && rescindedDate == null) {
			finding.unrescind();
		} else if (!finding.isUnderAppeal() && appealDate != null) {
			finding.appeal(appealDate);
		} else if (finding.isUnderAppeal() && appealDate == null) {
			finding.unappeal();
		} else {
			finding.setCorrectionVerification(verificationType);
		}
		updateCmpDueDate(inspection);
		inspectionDao.save(inspection);
	}
	
	private void updateCmpDueDate(Inspection inspection) {
		if (!inspection.isCmpDueDateRequired()) {
			inspection.setCmpDueDate(null);
		}
	}
	
	@Override
	public InspectionView loadInspectionViewById(Long inspectionId) {
		return inspectionDao.loadInspectionViewById(inspectionId);
	}
	
	@Override
	public void refreshInspectionView(InspectionView inspectionView) {
		inspectionDao.refreshInspectionView(inspectionView);
	}
	
	@Override
	public List<InspectionView> getInspectionsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized) {
		return inspectionDao.getInspectionsForFacility(facilityId, listRange, sortBy, finalized);
	}
	
	@Override
	public List<Inspection> getInspectionsForFacility(Long facilityId, Date startDate, Date endDate) {
		return inspectionDao.getInspectionsForFacility(facilityId, startDate, endDate);
	}
	
	@Override
	public InspectionRuleInfoView selectRuleForFinding(Long inspectionId, Long ruleId) {
		InspectionRuleInfoView iriv = new InspectionRuleInfoView();
		if (ruleId != null || inspectionId != null) {
			RuleResultView rv = ruleService.getRuleView(ruleId);
			iriv.setRuleId(ruleId);
			iriv.setRuleNumber(rv.getRuleNumber());
			iriv.setRuleDescription(rv.getDescription());
			
			List<FindingCategoryPickListValue> cats = ruleService.getFindingCategoriesForRule(ruleId);
			List<KeyValuePair> catsKvp = new ArrayList<KeyValuePair>();
			for (FindingCategoryPickListValue plv : cats) {
				catsKvp.add(new KeyValuePair(plv.getId().toString(), plv.getValue()));
			}
			
			List<PickListValue> ncLevels = ruleService.getNoncomplianceLevelsForRule(ruleId);
			List<KeyValuePair> nclKvp = new ArrayList<KeyValuePair>();
			for (PickListValue plv : ncLevels) {
				nclKvp.add(new KeyValuePair(plv.getId().toString(), plv.getValue()));
			}
			
			iriv.setFindingCategories(catsKvp);
			iriv.setNoncomplianceLevels(nclKvp);
			
			Inspection ins = loadById(inspectionId);
			
			Date startDate = inspectionDao.getComplianceDate(ins.getFacility().getId(), ins.getInspectionDate());
			Set<Finding> findings = inspectionDao.getFindingsForRule(ins.getFacility().getId(), ruleId, startDate, ins.getInspectionDate());
			for (Iterator<Finding> itr = findings.iterator(); itr.hasNext();) {
				Finding f = itr.next();
				if (f.getInspection().getId().equals(inspectionId)) {
					itr.remove();
				}
			}
			
			iriv.setPreviousFindings(findings);
		}
		
		return iriv;
	}
	
	public List<RuleViolation> getRuleViolations(Long facilityId, Long ruleId) {
		return inspectionDao.getRuleViolations(facilityId, ruleId);
	}
	
	@Override
	public Date getNoncomplianceDate(Long facilityId) {
		return inspectionDao.getComplianceDate(facilityId, new Date());
	}
	
	@Override
	public List<NoncomplianceView> getNonComplianceHistory(Long facilityId, SortBy sortBy) {
		return inspectionDao.getNonComplianceHistory(facilityId, sortBy);
	}
	
	@Override
	public List<NoncomplianceFindingView> getNoncomplianceRuleViews(Long facilityId) {
		return inspectionDao.getNoncomplianceFindingViews(facilityId);
	}
	
	@Override
	public List<InspectionFollowUpView> getInspectionFollowUpMatrix(Long inspectionId) {
		List<InspectionFollowUpView> matrix = new ArrayList<InspectionFollowUpView>();
		Inspection inspection = inspectionDao.load(inspectionId);
		
		for (Finding f : inspection.getFollowUps()) {
			InspectionFollowUpView iv = new InspectionFollowUpView();
			iv.setInspectionId(f.getInspection().getId());
			iv.setFindingId(f.getId());
			iv.setInspectionDate(f.getInspection().getInspectionDate());
			iv.setPrimaryInspectionType(f.getInspection().getPrimaryInspectionType());
			iv.setOtherInspectionTypes(f.getInspection().getNonPrimaryInspectionTypes());
			iv.setRuleId(f.getRule().getId());
			iv.setCorrected(f.getCorrectedOn() != null);
			iv.setCorrectedByThis(f.getCorrectedOn() != null && f.getCorrectedOn().getId().equals(inspection.getId()));
			iv.setFollowedUpByThis(true);
			matrix.add(iv);
		}
		
		InspectionFollowUpViewComparator comp = new InspectionFollowUpViewComparator(ruleService);
		
		Collections.sort(matrix, comp);
		
		return matrix;
	}
	
	@Override
	public List<InspectionFollowUpView> getEditableInspectionFollowUpMatrix(Long inspectionId) {
		//get the matrix of existing follow ups and corrections
		List<InspectionFollowUpView> matrix = getInspectionFollowUpMatrix(inspectionId);
		
		//add the findings that still need follow ups and corrections
		List<Finding> findings = inspectionDao.getPreviousFindingsNeedingFollowups(inspectionId);
		for (Finding f : findings) {
			InspectionFollowUpView iv = new InspectionFollowUpView();
			iv.setInspectionId(f.getInspection().getId());
			iv.setFindingId(f.getId());
			iv.setInspectionDate(f.getInspection().getInspectionDate());
			iv.setPrimaryInspectionType(f.getInspection().getPrimaryInspectionType());
			iv.setOtherInspectionTypes(f.getInspection().getNonPrimaryInspectionTypes());
			iv.setRuleId(f.getRule().getId());
			iv.setCorrected(f.getCorrectedOn() != null);
			if (!matrix.contains(iv)) {
				matrix.add(iv);	
			}
		}
		
		InspectionFollowUpViewComparator comp = new InspectionFollowUpViewComparator(ruleService);
		
		Collections.sort(matrix, comp);
		
		return matrix;
	}
	
	@Override
	public void saveInspectionFollowUpMatrix(Long inspectionId, List<Finding> followUps, List<Finding> corrections) {
		if (followUps == null) {
			followUps = new ArrayList<Finding>();
		}
		if (corrections == null) {
			corrections = new ArrayList<Finding>();
		}
		
		//remove corrections that are not also in the follow ups list
		for (Iterator<Finding> itr = corrections.iterator(); itr.hasNext();) {
			Finding f = itr.next();
			if (!EntityUtil.collectionContains(f, followUps)) {
				itr.remove();
			}
		}
		
		//inspection doing the follow ups
		Inspection ins = inspectionDao.load(inspectionId);
		
		//remove all follow ups and corrections that are no longer present
		for (Iterator<Finding> itr = ins.getCorrections().iterator(); itr.hasNext();) {
			Finding corr = itr.next();
			if (!corr.getInspection().equals(corr.getCorrectedOn()) && !EntityUtil.collectionContains(corr, corrections)) {
				itr.remove(); //remove the item from this inspection
				corr.setCorrectedOn(null); //set the correction to null
				if (corr.getRescindedDate() == null && !corr.isUnderAppeal()) {
					corr.setCorrectionVerification(CorrectionVerificationType.VERIFICATION_PENDING);
				}
				saveWithoutFinalizationCheck(corr.getInspection());
			}
		}
		for (Iterator<Finding> itr = ins.getFollowUps().iterator(); itr.hasNext();) {
			Finding foll = itr.next();
			if (!EntityUtil.collectionContains(foll, followUps)) {
				//remove this inspection from the follow up's inspection list
				for (Iterator<Inspection> insItr = foll.getFollowUps().iterator(); insItr.hasNext();) {
					Inspection ins1 = insItr.next();
					if (ins1.getId().equals(ins.getId())) {
						insItr.remove();
						saveWithoutFinalizationCheck(foll.getInspection());
					}
				}
				//remove the follow up from this inspection
//				itr.remove();
//				saveWithoutFinalizationCheck(foll.getInspection());
			}
		}
//		inspectionDao.save(ins);
		
		//add the findings that are new
		for (Finding f : followUps) {
			if (!EntityUtil.collectionContains(ins, f.getFollowUps())) {
				f.addFollowUp(ins);
				saveWithoutFinalizationCheck(f.getInspection());
			}
			
		}
		
		//add the corrections that are new
		for (Finding f : corrections) {
			if (!EntityUtil.collectionContains(f, ins.getCorrections())) {
				f.setCorrectedOn(ins);

				saveWithoutFinalizationCheck(f.getInspection());
			}
		}
		inspectionDao.refresh(ins);
	}
	
	private void updateCompliance(Inspection inspection) {
		List<PickListValue> insTypes = applicationService.getPickListValuesForApplicationProperty(ApplicationPropertyKey.COMPLIANCE_RESET_TYPES.getKey());
		boolean resets = false;
		for (InspectionType type : inspection.getTypes()) {
			if (insTypes.contains(type.getInspectionType())) {
				resets = true;
				break;
			}
		}
		inspection.setResetsCompliance(resets);
	}
	
	@Override
	public PickListValue getComplaintInvestigationType() {
		return applicationService.getPickListValueForApplicationProperty(ApplicationPropertyKey.COMPLAINT_INVESTIGATION_TYPE.getKey());
	}
	
	@Override
	public NoncompliancePointMatrix getNoncompliancePointMatrix() {
		List<NoncompliancePoints> points = noncompliancePointsDao.getAllPoints();
		NoncompliancePointMatrix matrix = new NoncompliancePointMatrix(points);
		return matrix;
	}
	
	@Override
	public void evict(final Object entity) {
		inspectionDao.evict(entity);
	}

	public void refresh(final Inspection entity) {
		inspectionDao.refresh(entity);
	}

	private static class InspectionFollowUpViewComparator implements Comparator<InspectionFollowUpView> {
		
		private RuleService ruleService;
		
		public InspectionFollowUpViewComparator(RuleService ruleService) {
			this.ruleService = ruleService;
		}
		
		@Override
		public int compare(InspectionFollowUpView o1, InspectionFollowUpView o2) {
			int comp = o1.getInspectionDate().compareTo(o2.getInspectionDate());
			if (comp == 0) {
				comp = o1.getInspectionId().compareTo(o2.getInspectionId());
			}
			if (comp == 0) {
				RuleSubSection o1r = ruleService.loadRuleSubSectionById(o1.getRuleId());
				RuleSubSection o2r = ruleService.loadRuleSubSectionById(o2.getRuleId());
				
				comp = o1r.compareTo(o2r);
			}
				
			return comp;
		}
	}

	/**
	 * 
	 * @param checkListReponses 0 = ID, 1 = response, 2 = comments
	 */
	@Transactional
	public boolean saveCheckList(InspectionChecklist checkList) {

		if (checkList.getId() == null) { 
			if (checkList.getAttachment() != null && checkList.getAttachment().getId() == null) {
				genericDao.insert(checkList.getAttachment());
			} 

			genericDao.insert(checkList);
			
			if (checkList.getHeaders() != null && checkList.getHeaders().size() > 0) {
				for (InspectionChecklistHeader header : checkList.getHeaders()) {
					header.setInspectionChecklist(checkList);
					genericDao.insert(header);
				}
			}

			if (checkList.getResults() != null && checkList.getResults().size() > 0) {
				for (InspectionChecklistResult result : checkList.getResults()) {
					result.setInspectionChecklist(checkList);
					genericDao.insert(result);
				}
			}

			log.debug("inserted: InspectionChecklistResult.id = "+checkList.getId());
		} else {
			if (checkList.getAttachment() != null && checkList.getAttachment().getId() == null) {
				genericDao.insert(checkList.getAttachment());
			} else if (checkList.getAttachment() != null && checkList.getAttachment().getId() != null) {
				genericDao.update(checkList.getAttachment());
			}
			
			if (checkList.getHeaders() != null && checkList.getHeaders().size() > 0) {
				for (InspectionChecklistHeader header : checkList.getHeaders()) {
					genericDao.update(header);
				}
			}

			if (checkList.getResults() != null && checkList.getResults().size() > 0) {
				for (InspectionChecklistResult result : checkList.getResults()) {
					genericDao.update(result);
				}
			}
			
			genericDao.update(checkList);

			log.debug("updated: InspectionChecklistResult.id = "+checkList.getId());
		}
		
		return true;
	}
	
	@Override
	public List<String[]> getChecklistHeader(Inspection inspection) {
		
		// Add the facility contact information
        String contactName = "";
        if (inspection.getFacility().getPrimaryContacts() != null && !inspection.getFacility().getPrimaryContacts().isEmpty()) {
			List<String> names = new ArrayList<String>();
			for(FacilityPerson p : inspection.getFacility().getPrimaryContacts()) {
				names.add(p.getPerson().getFirstAndLastName());
				break; // CKS: I just want one (for now)
			}
			String nameString = org.apache.commons.lang.StringUtils.join(names, "/");
			contactName = (nameString);
		} else {
			contactName = inspection.getFacility().getName();
		}
        
        String adultCapacity = "";
        if (inspection.getLicense() != null && inspection.getLicense().getAdultTotalSlots() != null) {
        	adultCapacity = inspection.getLicense().getAdultTotalSlots()+"";
        }

        String youthCapacity = "";
        if (inspection.getLicense() != null && inspection.getLicense().getYouthTotalSlots() != null) {
        	youthCapacity = inspection.getLicense().getYouthTotalSlots()+"";
        }

        // TODO CKS[Jan 30, 2013] not sure where to get this ...
        String enrolled = "";
//        if (inspection.getFacility() != null && inspection.getFacility().getCurrentLicense() != null && inspection.getFacility().getCurrentLicense().get != null) {
//        	enrolled = inspection.getFacility().getCurrentLicense().getTotalSlots()+"";
//        }

		ArrayList<String[]> list = new ArrayList<String[]>();
		
		list.add(new String[] {InspectionChecklistHeader.STAFFER, "Licensing Staffer", SecurityUtil.getUser().getPerson().getFullName()});
		list.add(new String[] {InspectionChecklistHeader.DATE, "Date", sdf.format(new java.util.Date())});
		list.add(new String[] {InspectionChecklistHeader.PROGRAM, "Program", ""});
		list.add(new String[] {InspectionChecklistHeader.DIRECTOR, "Director", contactName});
		list.add(new String[] {InspectionChecklistHeader.SIGNATURE, "Provider Signature", ""});
		list.add(new String[] {InspectionChecklistHeader.LICENCE_ADULT_CAPACITY, "License Adult Capacity", adultCapacity});
		list.add(new String[] {InspectionChecklistHeader.LICENCE_YOUTH_CAPACITY, "License Youth Capacity", youthCapacity});
		list.add(new String[] {InspectionChecklistHeader.CONSUMERS_ENROLLED, "Number of Consumers Enrolled", enrolled});
		list.add(new String[] {InspectionChecklistHeader.FEE, "Fee", ""});
		if (inspection != null && inspection.getFacility() != null && inspection.getFacility().getName() != null) { 
			list.add(new String[] {InspectionChecklistHeader.FACILITY, "Facility", inspection.getFacility().getName()});
		} else {
			list.add(new String[] {InspectionChecklistHeader.FACILITY, "Facility", ""});
		}
		if (inspection != null && inspection.getFacility() != null && inspection.getFacility().getLocationAddress() != null && inspection.getFacility().getLocationAddress().getAddressOne() != null) { 
			list.add(new String[] {InspectionChecklistHeader.ADDRESS_1, "Address 1", inspection.getFacility().getLocationAddress().getAddressOne()});
		} else {
			list.add(new String[] {InspectionChecklistHeader.ADDRESS_1, "Address 1", ""});
		}
		if (inspection != null && inspection.getFacility() != null && inspection.getFacility().getLocationAddress() != null && inspection.getFacility().getLocationAddress().getAddressTwo() != null) { 
			list.add(new String[] {InspectionChecklistHeader.ADDRESS_2, "Address 2", inspection.getFacility().getLocationAddress().getAddressTwo()});
		} else {
			list.add(new String[] {InspectionChecklistHeader.ADDRESS_2, "Address 2", ""});
		}
		if (inspection != null && inspection.getFacility() != null && inspection.getFacility().getLocationAddress() != null && inspection.getFacility().getLocationAddress().getCity() != null) { 
			list.add(new String[] {InspectionChecklistHeader.CITY, "City", inspection.getFacility().getLocationAddress().getCity()});
		} else {
			list.add(new String[] {InspectionChecklistHeader.CITY, "City", ""});
		}
		if (inspection != null && inspection.getFacility() != null && inspection.getFacility().getLocationAddress() != null && inspection.getFacility().getLocationAddress().getState() != null) { 
			list.add(new String[] {InspectionChecklistHeader.STATE, "State", inspection.getFacility().getLocationAddress().getState()});
		} else {
			list.add(new String[] {InspectionChecklistHeader.STATE, "State", ""});
		}
		if (inspection != null && inspection.getFacility() != null && inspection.getFacility().getLocationAddress() != null && inspection.getFacility().getLocationAddress().getZipCode() != null) { 
			list.add(new String[] {InspectionChecklistHeader.ZIPCODE, "Zip Code", inspection.getFacility().getLocationAddress().getZipCode()});
		} else {
			list.add(new String[] {InspectionChecklistHeader.ZIPCODE, "Zip Code", ""});
		}
		
		return list;
	}
	
	public Map<String, String[]> getChecklistHeaderAsMap(Inspection inspection) {
		
		// Add the facility contact information
        String contactName = "";
        if (inspection.getFacility().getPrimaryContacts() != null && 
        		!inspection.getFacility().getPrimaryContacts().isEmpty()) 
        {
			List<String> names = new ArrayList<String>();
			for(FacilityPerson p : inspection.getFacility().getPrimaryContacts()) {
				names.add(p.getPerson().getFirstAndLastName());
				break; // CKS: I just want one (for now)
			}
			String nameString = org.apache.commons.lang.StringUtils.join(names, "/");
			contactName = (nameString);
		} else {
			contactName = inspection.getFacility().getName();
		}
		Map<String, String[]> list = new HashMap<String, String[]>();
		
		list.put(InspectionChecklistHeader.STAFFER, new String[] { "Licensing Staffer", SecurityUtil.getUser().getPerson().getFullName()});
		list.put(InspectionChecklistHeader.DATE, new String[] {"Date", sdf.format(new java.util.Date())});
		list.put(InspectionChecklistHeader.PROGRAM, new String[] {"Program", ""});
		list.put(InspectionChecklistHeader.DIRECTOR, new String[] {"Director", contactName});
		list.put(InspectionChecklistHeader.SIGNATURE, new String[] {"Provider Signature", ""});
		list.put(InspectionChecklistHeader.LICENCE_ADULT_CAPACITY, new String[] {"License Adult Capacity", ""});
		list.put(InspectionChecklistHeader.LICENCE_YOUTH_CAPACITY, new String[] {"License Youth Capacity", ""});
		list.put(InspectionChecklistHeader.CONSUMERS_ENROLLED, new String[] {"Number of Consumers Enrolled", ""});
		list.put(InspectionChecklistHeader.FEE, new String[] {"Fee", ""});
		list.put(InspectionChecklistHeader.FACILITY, new String[] {"Facility", inspection.getFacility().getName()});
		list.put(InspectionChecklistHeader.ADDRESS_1, new String[] {"Address 1", inspection.getFacility().getLocationAddress().getAddressOne()});
		list.put(InspectionChecklistHeader.ADDRESS_2, new String[] {"Address 2", inspection.getFacility().getLocationAddress().getAddressTwo()});
		list.put(InspectionChecklistHeader.CITY, new String[] {"City", inspection.getFacility().getLocationAddress().getCity()});
		list.put(InspectionChecklistHeader.STATE, new String[] {"State", inspection.getFacility().getLocationAddress().getState()});
		list.put(InspectionChecklistHeader.ZIPCODE, new String[] {"Zip Code", inspection.getFacility().getLocationAddress().getZipCode()});
		
		return list;
		
	}

	@Transactional
	public boolean deleteChecklist(InspectionChecklist checklist) {
		
		int d = genericDao.delete(InspectionChecklistResult.class, "inspectionChecklist", checklist.getId());
		log.debug("deleted "+d+" InspectionChecklistResult objects");
		
		d = genericDao.delete(InspectionChecklistHeader.class, "inspectionChecklist", checklist.getId());
		log.debug("deleted "+d+" InspectionChecklistHeader objects");
		
		if (checklist.getAttachment() != null && checklist.getAttachment().getId() != null) {
			boolean r = genericDao.delete(Attachment.class, checklist.getAttachment().getId());
			log.debug("deleted Attachment object ? "+r);
		}
		
		boolean r = genericDao.delete(InspectionChecklist.class, checklist.getId());
		
		log.debug("deleteChecklist: InspectionChecklistResult.id = "+checklist.getId()+" ? "+r);
		
		return r;
	}
	
}