package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Finding;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.view.InspectionView;
import gov.utah.dts.det.ccl.model.view.NoncomplianceFindingView;
import gov.utah.dts.det.ccl.model.view.NoncomplianceView;
import gov.utah.dts.det.ccl.service.RuleViolation;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.dao.AbstractBaseDao;
import gov.utah.dts.det.query.SortBy;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface InspectionDao extends AbstractBaseDao<Inspection, Long> {
	
	public InspectionView loadInspectionViewById(Long inspectionId);
	
	public void refreshInspectionView(InspectionView inspectionView);

	public List<InspectionView> getInspectionsForFacility(Long facilityId, ListRange listRange, SortBy sortBy, boolean finalized);
	
	public List<Inspection> getInspectionsForFacility(Long facilityId, Date startDate, Date endDate);
	
	public Set<Finding> getFindingsForRule(Long facilityId, Long ruleId, Date startDate, Date endDate);
	
	public List<RuleViolation> getRuleViolations(Long facilityId, Long ruleId);
	
	public Date getComplianceDate(Long facilityId, Date date);
	
	public List<NoncomplianceView> getNonComplianceHistory(Long facilityId, SortBy sortBy);
	
	public List<NoncomplianceFindingView> getNoncomplianceFindingViews(Long facilityId);
	
	public List<Finding> getPreviousFindingsNeedingFollowups(Long inspectionId);
}