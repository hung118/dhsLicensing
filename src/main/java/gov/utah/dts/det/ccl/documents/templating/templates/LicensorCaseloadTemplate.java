package gov.utah.dts.det.ccl.documents.templating.templates;

import gov.utah.dts.det.ccl.actions.caseloadmanagement.CaseloadSortBy;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.FacilityCaseloadView;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("fontLoader")
public class LicensorCaseloadTemplate extends AbstractCaseloadTemplate {
	
	@Override
	public String getTemplateKey() {
		return "licensor-caseload";
	}
	
	@Override
	protected String getFileName(Map<String, Object> context) {
		Person specialist = (Person) context.get(SPECIALIST_KEY);
		return specialist.getFirstAndLastName() + " Licensing Specialist Caseload.pdf";
	}
	
	@Override
	public List<FacilityCaseloadView> getCaseload(Long specialistId, CaseloadSortBy sortBy) {
		return facilityService.getUserCaseload(specialistId, RoleType.ROLE_LICENSOR_SPECIALIST, sortBy);
	}
	
	@Override
	public String getReportTitle() {
		return "Licensing Specialist Caseload";
	}
}