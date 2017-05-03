package gov.utah.dts.det.ccl.actions.trackingrecordscreening.miscomm;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMisComm;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningMisCommService;
import gov.utah.dts.det.ccl.view.YesNoList;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class for background screening MIS Committee tab.
 * 
 * @author Hnguyen
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-edit", location = "edit-miscomm", type = "redirectAction", params = { "screeningId", "${screeningId}", "misComm.id", "${misComm.id}" }),
	@Result(name = "redirect-view", location = "miscomm-list", type = "redirectAction", params = {"screeningId", "${screeningId}" }),
	@Result(name = "success", location = "miscomm_list.jsp"),
	@Result(name = "input", location = "miscomm_form.jsp") 
})
public class MisCommAction extends BaseTrackingRecordScreeningAction implements Preparable {

	private TrackingRecordScreeningMisCommService trackingRecordScreeningMisCommService;
	private TrackingRecordScreeningMisComm misComm;
	private List<PickListValue> oahDecisions;
	
	public void prepareDoForm() {
		if (misComm == null) {
			misComm = new TrackingRecordScreeningMisComm();
		}
	}

	@Action(value = "new-miscomm")
	public String doForm() {
		if (misComm.getMisCommDate() == null) {
			misComm.setMisCommDate(new Date());
		}
		return INPUT;
	}
	
	@Action(value = "edit-miscomm")
	public String doEdit() {
	    misComm = getTrackingRecordScreening().getMisComm();
	    if (misComm == null) {
	      misComm = new TrackingRecordScreeningMisComm();
	    }
		
		return INPUT;
	}
	
	public void prepareDoSave() {
	    misComm = getTrackingRecordScreening().getMisComm();
	    if (misComm == null) {
	      misComm = new TrackingRecordScreeningMisComm();
	    }
	}
	
	@Validations(conversionErrorFields = {
	    @ConversionErrorFieldValidator(fieldName = "misComm.oahRequestDate", message = "OAH request date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
		@ConversionErrorFieldValidator(fieldName = "misComm.misCommDate", message = "MIS committee decision date is not a valid date. (MM/DD/YYYY)", shortCircuit = true) 
	})
	@Action(value = "save-miscomm")
	public String doSave() {
	    if (misComm.getId() == null) {
	        misComm.setId(getTrackingRecordScreening().getId());
	    }
		trackingRecordScreeningMisCommService.save(misComm);

		return REDIRECT_EDIT;
	}
	
	public void setTrackingRecordScreeningMisCommService(TrackingRecordScreeningMisCommService trackingRecordScreeningMisCommService) {
		this.trackingRecordScreeningMisCommService = trackingRecordScreeningMisCommService;
	}

	public TrackingRecordScreeningMisComm getMisComm() {
		return misComm;
	}

	public void setMisComm(TrackingRecordScreeningMisComm misComm) {
		this.misComm = misComm;
	}
	
	@Override
	public void prepare() throws Exception {

	}
	
	public List<YesNoList> getYesNoList() {
		return Arrays.asList(YesNoList.values());
	}
	
	public List<PickListValue> getOahDecisions() {
		if (oahDecisions == null) {
			oahDecisions = pickListService.getValuesForPickList(
					"OAH Decisions", true);
		}
		return oahDecisions;
	}
	
}
