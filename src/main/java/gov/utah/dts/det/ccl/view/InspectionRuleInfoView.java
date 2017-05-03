package gov.utah.dts.det.ccl.view;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Finding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

public class InspectionRuleInfoView {

	private Long ruleId;
	private String ruleDescription;
	private String ruleNumber;
	private List<KeyValuePair> findingCategories;
	private List<KeyValuePair> noncomplianceLevels;
	private List<FindingWrapper> previousFindings;
	
	public InspectionRuleInfoView() {
		
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	
	public String getRuleNumber() {
		return ruleNumber;
	}
	
	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public List<KeyValuePair> getFindingCategories() {
		return findingCategories;
	}

	public void setFindingCategories(List<KeyValuePair> findingCategories) {
		this.findingCategories = findingCategories;
	}

	public List<KeyValuePair> getNoncomplianceLevels() {
		return noncomplianceLevels;
	}

	public void setNoncomplianceLevels(List<KeyValuePair> noncomplianceLevels) {
		this.noncomplianceLevels = noncomplianceLevels;
	}

	public List<FindingWrapper> getPreviousFindings() {
		return previousFindings;
	}

	public void setPreviousFindings(Set<Finding> previousFindings) {
		this.previousFindings = new ArrayList<FindingWrapper>();
		for (Finding f : previousFindings) {
			this.previousFindings.add(new FindingWrapper(f));
		}
		
		Collections.sort(this.previousFindings);
	}
	
	@SuppressWarnings("serial")
	public static class FindingWrapper implements Serializable, Comparable<FindingWrapper> {
		
		private Finding finding;
		
		private transient List<String> types;
		
		public FindingWrapper(Finding finding) {
			this.finding = finding;
		}
		
		public Long getInspectionId() {
			return finding.getInspection().getId();
		}
		
		@JSON(format = "MM/dd/yyyy")
		public Date getInspectionDate() {
			return finding.getInspection().getInspectionDate();
		}
		
		public String getPrimaryInspectionType() {
			return finding.getInspection().getPrimaryInspectionType().getValue();
		}
		
		public List<String> getOtherInspectionTypes() {
			if (types == null) {
				types = new ArrayList<String>();
				for (PickListValue plv : finding.getInspection().getNonPrimaryInspectionTypes()) {
					types.add(plv.getValue());
				}
			}
			return types;
		}
		
		@JSON(format = "MM/dd/yyyy")
		public Date getCorrectedDate() {
			return finding.getCorrectedOn() == null ? null : finding.getCorrectedOn().getInspectionDate();
		}
		
		public boolean isCorrectedOnSite() {
			return finding.getCorrectedOn() == null ? false : finding.getCorrectedOn().getId().equals(finding.getInspection().getId()); 
		}
		
		public String getFindingCategory() {
			return finding.getFindingCategory() == null ? null : finding.getFindingCategory().getValue();
		}
		
		public String getNoncomplianceLevel() {
			return finding.getNoncomplianceLevel() == null ? null : finding.getNoncomplianceLevel().getValue();
		}
		
		public Double getCmpAmount() {
			return finding.getCmpAmount();
		}
		
		public boolean isUnderAppeal() {
			return finding.isUnderAppeal();
		}
		
		public String getDeclarativeStatement() {
			return finding.getDeclarativeStatement();
		}
		
		public String getAdditionalInformation() {
			return finding.getAdditionalInformation();
		}
		
		@Override
		public int compareTo(FindingWrapper o) {
			int comp = getInspectionDate().compareTo(o.getInspectionDate());
			if (comp == 0) {
				comp = getInspectionId().compareTo(o.getInspectionId());
			}
			return comp;
		}
	}
}