package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Finding;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.InspectionChecklist;
import gov.utah.dts.det.ccl.model.InspectionChecklistResult;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.CorrectionVerificationType;
import gov.utah.dts.det.ccl.model.view.InspectionView;
import gov.utah.dts.det.ccl.model.view.NoncomplianceFindingView;
import gov.utah.dts.det.ccl.model.view.NoncomplianceView;
import gov.utah.dts.det.ccl.service.impl.BasicService;
import gov.utah.dts.det.ccl.view.InspectionFollowUpView;
import gov.utah.dts.det.ccl.view.InspectionRuleInfoView;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

public interface InspectionService extends BasicService {
	
	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN','ROLE_OFFICE_SPECIALIST','ROLE_LICENSOR_SPECIALIST','ROLE_LICENSING_DIRECTOR','ROLE_LICENSOR_MANAGER')")
	public Inspection loadById(Long id);
	
	@PreAuthorize(value = "hasPermission(#inspection, 'create')")
	public Inspection createInspection(Inspection inspection);
	
	@PreAuthorize(value = "hasPermission(#inspection, 'save-entry')")
	public Inspection saveInspection(Inspection inspection) throws CclServiceException;
	
	@PreAuthorize(value = "hasPermission(#inspection, 'complete-entry')")
	public Inspection completeEntry(Inspection inspection, Person approver, String note) throws CclServiceException;
	
	@PreAuthorize(value = "hasPermission(#inspection, 'reject-entry')")
	public Inspection rejectEntry(Inspection inspection, String note) throws CclServiceException;
	
	@PreAuthorize(value = "hasPermission(#inspection, 'finalize')")
	public Inspection finalizeInspection(Inspection inspection, String note) throws CclServiceException;
	
	@PreAuthorize(value = "hasPermission(#inspection, 'unfinalize')")
	public Inspection unfinalizeInspection(Inspection inspection, String note);
	
	@PreAuthorize(value = "hasPermission(#inspection, 'delete')")
	public void deleteInspection(Inspection inspection);
	
	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
	public void updateFindingStatus(Inspection inspection, Finding finding, Date rescindedDate, Date appealDate, CorrectionVerificationType verificationType);
	
	/*@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
	public void setRescindedDate(Inspection inspection, Finding finding, Date rescindedDate);

	@PreAuthorize(value = "hasAnyRole('ROLE_SUPER_ADMIN')")
	public void setUnderAppeal(Inspection inspection, Finding finding, Boolean underAppeal);*/
	
	public InspectionView loadInspectionViewById(Long inspectionId);
	
	public void refreshInspectionView(InspectionView inspectionView);
	
	public List<InspectionView> getInspectionsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized);
	
	public List<Inspection> getInspectionsForFacility(Long facilityId, Date startDate, Date endDate);
	
	public InspectionRuleInfoView selectRuleForFinding(Long inspectionId, Long ruleId);
	
	public List<RuleViolation> getRuleViolations(Long facilityId, Long ruleId);
	
	public Date getNoncomplianceDate(Long facilityId);
	
	public List<NoncomplianceView> getNonComplianceHistory(Long facilityId, SortBy sortBy);
	
	public List<NoncomplianceFindingView> getNoncomplianceRuleViews(Long facilityId);
	
	public List<InspectionFollowUpView> getInspectionFollowUpMatrix(Long inspectionId);
	
	public List<InspectionFollowUpView> getEditableInspectionFollowUpMatrix(Long inspectionId);
	
	public void saveInspectionFollowUpMatrix(Long inspectionId, List<Finding> followUps, List<Finding> corrections);

	public PickListValue getComplaintInvestigationType();
	
	public NoncompliancePointMatrix getNoncompliancePointMatrix();
	
	public void evict(final Object entity);
	
	public boolean saveCheckList(InspectionChecklist checkList);
	
	public List<String[]> getChecklistHeader(Inspection Inspection);
	
	public boolean deleteChecklist(InspectionChecklist checklist);
	
	public void refresh(final Inspection entity);
	
}