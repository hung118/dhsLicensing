package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Finding;
import gov.utah.dts.det.ccl.model.FindingCategoryPickListValue;
import gov.utah.dts.det.ccl.model.Inspection;
import gov.utah.dts.det.ccl.model.NoncomplianceLevelPickListValue;
import gov.utah.dts.det.ccl.model.enums.CorrectionVerificationType;
import gov.utah.dts.det.ccl.view.InspectionRuleInfoView;
import gov.utah.dts.det.ccl.view.YesNoChoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "finding_form.jsp"),
	@Result(name = "success", location = "findings-section", type = "redirectAction", params = {"facilityId", "${facilityId}", "inspectionId", "${inspectionId}", "refreshInspection", "${refreshInspection}"})
})
public class FindingAction extends BaseInspectionAction implements Preparable {
	
	private Finding finding;
	private boolean refreshInspection = false;
	private Long ruleId;
	private boolean correctedOnSite = false;
	
	private Date appealDate;
	private Date rescindedDate;
	private CorrectionVerificationType verificationType;

	private Double totalCmpAmount;
	private InspectionRuleInfoView insRuleView;
	private String rvJson;
	
	private List<FindingCategoryPickListValue> findingCategories;
	private List<NoncomplianceLevelPickListValue> noncomplianceLevels;
	
	@Override
	public void prepare() throws Exception {
		
	}
	
	@SkipValidation
	@Action(value = "findings-section", results = {
		@Result(name = "success", location = "findings_section.jsp")
	})
	public String doSection() {
		return SUCCESS;
	}

	@SkipValidation
	@Action(value = "edit-finding")
	public String doEdit() throws JSONException {
		loadFinding();
		if (finding != null && finding.getId() != null) {
			insRuleView = inspectionService.selectRuleForFinding(getInspection().getId(), finding.getRule().getId());
			rvJson = JSONUtil.serialize(insRuleView);
			correctedOnSite = finding.isCorrectedOnSite();
		}
		
		return INPUT;
	}

	@SkipValidation
	@Action(value = "select-rule", results = {
			@Result(name = "success", type = "json", params = {"root", "insRuleView"})
	})
	public String doSelectRule() {
		insRuleView = inspectionService.selectRuleForFinding(getInspection().getId(), ruleId);
		
		return SUCCESS;
	}
	
	public void prepareDoSave() {
		if (finding.getId() != null) {
			finding = getInspection().getFinding(finding.getId());//inspectionService.loadFindingById(finding.getId());
			finding.getFollowUps().size(); //prevent lazy init exception
		}
		inspectionService.evict(finding);
	}
	
	@Validations(
		visitorFields = {
			@VisitorFieldValidator(fieldName = "finding", message = "&zwnj;")
		}
	)
	@Action(value = "save-finding")
	public String doSave() {
		if (finding.getId() == null) {
			getInspection().addFinding(finding);
		}
		
		if (finding.getRule().isDontIssueFindings()) {
			finding.setFindingCategory(null);
			finding.setNoncomplianceLevel(null);
		}
		
		if (finding.getCmpAmount() != null && !finding.getFindingCategory().getType().isDisplayCmp()) {
			finding.setCmpAmount(null);
		}
		
		if (!correctedOnSite && finding.getCorrectedOn() != null && finding.getCorrectedOn().getId().equals(finding.getInspection().getId())) {
			finding.setCorrectedOn(null);
		} else if (correctedOnSite) {
			finding.setCorrectedOn(finding.getInspection());
			finding.setWarningCorrectionDate(null);
		}
		
		saveInspection();
		
		//set refresh inspection to true only if it is not already true and the inspection was updated
		refreshInspection = true;
		
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "delete-finding")
	public String doDelete() {
		loadFinding();
		getInspection().removeFinding(finding);
		saveInspection();
		
		refreshInspection = true;
		
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "save-finding-list")
	public String doSaveList() {
		loadFinding();
		inspectionService.updateFindingStatus(getInspection(), finding, rescindedDate, appealDate, verificationType);
		
		//set refresh inspection to true only if it is not already true and the inspection was updated
		refreshInspection = true;
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if (finding.getRule() != null && finding.getRule().getId() != null) {
			Long ruleId = finding.getRule().getId();
			//perform a check to make sure this rule has not already been cited on this inspection
			for (Finding finding : getInspection().getFindings()) {
				//only check against findings that are not the current finding or there will always be a duplicate
				if ((this.finding.getId() == null || !this.finding.getId().equals(finding.getId())) && finding.getRule().getId().equals(ruleId)) {
					addActionError("This rule has already been cited on this inspection.");
				}
			}
		
			//perform a check to make sure the finding category and noncompliance levels have been set if the rule is not don't issue findings.
			if (!finding.getRule().isDontIssueFindings()) {
				if (finding.getFindingCategory() == null) {
					addFieldError("finding.findingCategory", "Findings category is required.");
				}
				if (finding.getNoncomplianceLevel() == null) {
					addFieldError("finding.noncomplianceLevel", "Noncompliance level is required.");
				}
				if (!correctedOnSite && finding.getWarningCorrectionDate() == null) {
					addFieldError("finding.warningCorrectionDate", "Correction date is required.");
				}
			}
		}
	}
	
	private void loadFinding() {
		if (getInspection() != null && finding != null && finding.getId() != null) {
			this.finding = getInspection().getFinding(finding.getId());
		}
	}
	
	public Finding getFinding() {
		return finding;
	}
	
	public void setFinding(Finding finding) {
		this.finding = finding;
	}
	
	@Override
	public Inspection getInspection() {
		return super.getInspection();
	}
	
	public boolean isRefreshInspection() {
		return refreshInspection;
	}
	
	public void setRefreshInspection(boolean refreshInspection) {
		this.refreshInspection = refreshInspection;
	}
	
	public Long getRuleId() {
		return ruleId;
	}
	
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	public boolean isCorrectedOnSite() {
		return correctedOnSite;
	}
	
	public void setCorrectedOnSite(boolean correctedOnSite) {
		this.correctedOnSite = correctedOnSite;
	}
	
	public Date getAppealDate() {
		return appealDate;
	}
	
	public void setAppealDate(Date appealDate) {
		this.appealDate = appealDate;
	}
	
	public Date getRescindedDate() {
		return rescindedDate;
	}
	
	public void setRescindedDate(Date rescindedDate) {
		this.rescindedDate = rescindedDate;
	}
	
	public CorrectionVerificationType getVerificationType() {
		return verificationType;
	}
	
	public void setVerificationType(CorrectionVerificationType verificationType) {
		this.verificationType = verificationType;
	}
	
	public Double getTotalCmpAmount() {
		if (totalCmpAmount == null) {
			double total = 0;
			for (Finding finding: getInspection().getFindings()) {
				if (finding.getRescindedDate() == null) {
					Double amt = finding.getCmpAmount();
					if (amt != null) {
						total += amt.doubleValue();
					}
				}
			}
			totalCmpAmount = new Double(total);
		}
		return totalCmpAmount;
	}
	
	public InspectionRuleInfoView getInsRuleView() {
		return insRuleView;
	}
	
	public String getRvJson() {
		return rvJson;
	}
	
	public List<YesNoChoice> getYesNoChoices() {
		return Arrays.asList(YesNoChoice.values());
	}
	
	public List<CorrectionVerificationType> getCorrectionVerificationTypes(Finding finding) {
		List<CorrectionVerificationType> types = new ArrayList<CorrectionVerificationType>();
		if (!finding.isUnderAppeal() && !finding.isRescinded() && finding.getCorrectionVerification() != CorrectionVerificationType.VERIFIED) {
			types.add(CorrectionVerificationType.VERIFICATION_PENDING);
			types.add(CorrectionVerificationType.PROVIDER_CLOSED);
			types.add(CorrectionVerificationType.UNABLE_TO_VERIFY);
		}
		return types;
	}
	
	public String getFindingCategoriesJson() throws JsonGenerationException, JsonMappingException, IOException {
		if (findingCategories == null) {
			List<PickListValue> fcs = pickListService.getValuesForPickList("Findings Categories", true);
			findingCategories = new ArrayList<FindingCategoryPickListValue>();
			for (PickListValue plv : fcs) {
				findingCategories.add((FindingCategoryPickListValue) plv);
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(findingCategories);
	}
	
	public String getNoncomplianceLevelsJson() throws JsonGenerationException, JsonMappingException, IOException {
		if (noncomplianceLevels == null) {
			List<PickListValue> ncls = pickListService.getValuesForPickList("Noncompliance Levels", true);
			noncomplianceLevels = new ArrayList<NoncomplianceLevelPickListValue>();
			for (PickListValue plv : ncls) {
				noncomplianceLevels.add((NoncomplianceLevelPickListValue) plv);
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(noncomplianceLevels);
	}
}