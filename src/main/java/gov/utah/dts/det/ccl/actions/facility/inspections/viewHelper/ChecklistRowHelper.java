package gov.utah.dts.det.ccl.actions.facility.inspections.viewHelper;

import java.util.List;

import gov.utah.dts.det.ccl.model.InspectionChecklistResult;
import gov.utah.dts.det.ccl.model.RuleSection;

public class ChecklistRowHelper {
	
	private RuleSection section;
	private List<InspectionChecklistResult> results;
	public RuleSection getSection() {
		return section;
	}
	public void setSection(RuleSection section) {
		this.section = section;
	}
	public List<InspectionChecklistResult> getResults() {
		return results;
	}
	public void setResults(List<InspectionChecklistResult> results) {
		this.results = results;
	}

	public boolean getSectionOnly() {
		if (results.size() == 1) 
			return true;
		return false;
	}
}
