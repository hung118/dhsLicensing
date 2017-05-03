package gov.utah.dts.det.ccl.actions.trackingrecordscreening.miscomm;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCase;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningCaseService;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-list", location = "case-list", type = "redirectAction", params = {"screeningId", "${screeningId}"}),
	@Result(name = "success", location = "case_list.jsp"),
	@Result(name = "input", location="case_form.jsp")
})
public class CaseAction extends BaseTrackingRecordScreeningAction implements Preparable {
	
	private final String REDIRECT_LIST = "redirect-list";

	private TrackingRecordScreeningCaseService caseService;
	private List<TrackingRecordScreeningCase> cases;
	private TrackingRecordScreeningCase misCase;
	private Long caseId;
	private PickListValue caseType;
	private List<PickListValue> caseTypes;

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Action(value="case-list")
	public String doList() {
		if (getScreeningId() != null) {
			cases = caseService.getCasesForScreening(getScreeningId());
		}
		if (cases == null) {
			cases = new ArrayList<TrackingRecordScreeningCase>();
		}
		return SUCCESS;
	}

	@Action(value="edit-case")
	public String doEdit() {
		if (caseId != null) {
			misCase = caseService.load(caseId);
		}
		if (misCase == null) {
			misCase = new TrackingRecordScreeningCase();
		}
		caseType = misCase.getCaseType();
		return INPUT;
	}

	public void prepareDoSave() {
		if (caseId != null) {
			misCase = caseService.load(caseId);
		}
		if (misCase == null) {
			misCase = new TrackingRecordScreeningCase();
		}
	}
	
	@Action(value="save-case")
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "misCase.caseDate", message = "Case date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "caseType", message = "Case type is required.", shortCircuit = true)
		}
	)
	public String doSave() {
		misCase.setTrackingRecordScreening(getTrackingRecordScreening());
		misCase.setCaseType(caseType);
		misCase = caseService.save(misCase);
		caseService.evict(misCase);
		return REDIRECT_LIST;
	}

	@Action(value="delete-case")
	public String doDelete() {
		if (caseId != null) {
			misCase = caseService.load(caseId);
			if (misCase != null) {
				caseService.delete(caseId);
				caseService.evict(misCase);
			}
		}
		return REDIRECT_LIST;
	}

	public TrackingRecordScreeningCase getMisCase() {
		return misCase;
	}

	public void setMisCase(TrackingRecordScreeningCase misCase) {
		this.misCase = misCase;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long id) {
		this.caseId = id;
	}

	public List<TrackingRecordScreeningCase> getCases() {
		return cases;
	}
	
	public PickListValue getCaseType() {
		return caseType;
	}

	public void setCaseType(PickListValue caseType) {
		this.caseType = caseType;
	}

	public void setTrackingRecordScreeningCaseService(TrackingRecordScreeningCaseService service) {
		this.caseService = service;
	}

	public List<PickListValue> getCaseTypes() {
		if (caseTypes == null) {
			caseTypes = pickListService.getValuesForPickList("MIS Comm Case Type", true);
		}
		return caseTypes;
	}
}
