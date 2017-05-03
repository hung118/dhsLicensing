package gov.utah.dts.det.ccl.actions.trackingrecordscreening;

import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({ @Result(name = "success", location = "trackingRecordScreeningEdit", type = "tiles") })
public class EditTrackingRecordScreeningAction extends BaseTrackingRecordScreeningAction {
	private boolean trsMain;
	private boolean trsDpsFbi;
	private boolean trsRequest;
	private boolean trsLetter;
	private boolean trsCbsComm;
	private boolean trsMisComm;
	private boolean trsOscar;
	private boolean trsActivity;
	private boolean viewOnly = false;

	public String execute() {
		TrackingRecordScreening trs = getTrackingRecordScreening();

		if (trs.getTrsMain() != null && trs.getTrsMain().hasData()) {
			trsMain = true;
		}
		if (trs.getTrsDpsFbi() != null && trs.getTrsDpsFbi().hasData()) {
			trsDpsFbi = true;
		}
		if (!trs.getRequestsList().isEmpty()) {
			trsRequest = true;
		}
		if (!trs.getLettersList().isEmpty() || !trs.getLtr15List().isEmpty()) {
			trsLetter = true;
		}
		if ((trs.getCbsComm() != null && trs.getCbsComm().hasData()) || !trs.getConvictionList().isEmpty()) {
			trsCbsComm = true;
		}
		if ((trs.getMisComm() != null && trs.getMisComm().hasData()) || !trs.getCaseList().isEmpty()) {
			trsMisComm = true;
		}
		if (!trs.getOscarList().isEmpty()) {
			trsOscar = true;
		}
		if (!trs.getActivityList().isEmpty()) {
			trsActivity = true;
		}

		return SUCCESS;
	}


	public boolean isTrsMain() {
		return trsMain;
	}

	public void setTrsMain(boolean trsMain) {
		this.trsMain = trsMain;
	}

	public boolean isTrsDpsFbi() {
		return trsDpsFbi;
	}

	public void setTrsDpsFbi(boolean trsDpsFbi) {
		this.trsDpsFbi = trsDpsFbi;
	}

	public boolean isTrsRequest() {
		return trsRequest;
	}

	public void setTrsRequest(boolean trsRequest) {
		this.trsRequest = trsRequest;
	}

	public boolean isTrsLetter() {
		return trsLetter;
	}

	public void setTrsLetter(boolean trsLetter) {
		this.trsLetter = trsLetter;
	}

	public boolean isTrsCbsComm() {
		return trsCbsComm;
	}

	public void setTrsCbsComm(boolean trsCbsComm) {
		this.trsCbsComm = trsCbsComm;
	}

	public boolean isTrsMisComm() {
		return trsMisComm;
	}

	public void setTrsMisComm(boolean trsMisComm) {
		this.trsMisComm = trsMisComm;
	}

	public boolean isTrsOscar() {
		return trsOscar;
	}

	public void setTrsOscar(boolean trsOscar) {
		this.trsOscar = trsOscar;
	}

	public boolean isTrsActivity() {
		return trsActivity;
	}

	public void setTrsActivity(boolean trsActivity) {
		this.trsActivity = trsActivity;
	}

	public boolean isViewOnly() {
		if (SecurityUtil.hasAnyRole(RoleType.ROLE_ACCESS_PROFILE_VIEW)) {
			viewOnly = true;
		}
		return viewOnly;
	}

	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}
}
