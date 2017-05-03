package gov.utah.dts.det.ccl.actions.facility.variances;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "variance_base.jsp"),
	@Result(name = "licensor-outcome", location = "licensor_outcome_form.jsp"),
	@Result(name = "licensor-view", location = "licensor_outcome_detail.jsp"),
	@Result(name = "supervisor-outcome", location = "supervisor_outcome_form.jsp"),
	@Result(name = "supervisor-view", location = "supervisor_outcome_detail.jsp"),
	@Result(name = "outcome", location = "outcome_form.jsp"),
	@Result(name = "outcome-view", location = "outcome_detail.jsp"),
	@Result(name = "revoke", location = "revoke_form.jsp"),
	@Result(name = "request", location = "request_form.jsp"),
	@Result(name = "request-view", location = "request_detail.jsp")
})
public class EditVarianceAction extends BaseVarianceAction {

	private final String LICENSOR_OUTCOME = "licensor-outcome";
	private final String LICENSOR_VIEW = "licensor-view";
	private final String SUPERVISOR_OUTCOME = "supervisor-outcome";
	private final String SUPERVISOR_VIEW = "supervisor-view";
	private final String OUTCOME_VIEW = "outcome-view";
	private final String OUTCOME = "outcome";
	private final String REQUEST_VIEW = "request-view";
	private final String REQUEST = "request";
	private final String REVOKE = "revoke";

	public String execute() {
		variance = reloadVariance();
		return SUCCESS;
	}

	@Action(value = "view-request")
	public String doView() {
		return REQUEST_VIEW;
	}
	
	@Action(value = "edit-request")
	public String doEdit() {
		return REQUEST;
	}

	@Action(value = "view-licensor-outcome")
	public String doViewLicensorOutcome() {
		return LICENSOR_VIEW;
	}

	@Action(value = "edit-licensor-outcome")
	public String doEditLicensorOutcome() {
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_LICENSOR_SPECIALIST) && !variance.isFinalized()) {
			return LICENSOR_OUTCOME;
		} else {
			return LICENSOR_VIEW;
		}
	}
	
	@Action(value = "view-supervisor-outcome")
	public String doViewSupervisorOutcome() {
		return SUPERVISOR_VIEW;
	}

	@Action(value = "edit-supervisor-outcome")
	public String doEditSupervisorOutcome() {
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_LICENSOR_MANAGER) && !variance.isFinalized()) {
			return SUPERVISOR_OUTCOME;
		} else {
			return SUPERVISOR_VIEW;
		}
	}

	@Action(value = "view-outcome")
	public String doViewOutcome() {
		return OUTCOME_VIEW;
	}

	@Action(value = "edit-outcome")
	public String doEditOutcome() {
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_LICENSING_DIRECTOR) && !variance.isFinalized()) {
			Calendar cal = Calendar.getInstance();
			if (variance.getReviewDate() == null) {
				variance.setReviewDate(cal.getTime());
			}
			if (variance.getStartDate() == null) {
				variance.setStartDate(variance.getRequestedStartDate());
			}
			if (variance.getEndDate() == null) {
				variance.setEndDate(variance.getRequestedEndDate());
			}
			return OUTCOME;
		} else {
			return OUTCOME_VIEW;
		}
	}

	@Action(value = "edit-revoke")
	public String doEditRevoke() {
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_LICENSING_DIRECTOR) && variance.getOutcome().equals(VarianceOutcome.APPROVED) && variance.isFinalized()) {
			return REVOKE;
		} else {
			return OUTCOME_VIEW;
		}
	}

	public List<VarianceOutcome> getOutcomes() {
		return Arrays.asList(VarianceOutcome.values());
	}

}