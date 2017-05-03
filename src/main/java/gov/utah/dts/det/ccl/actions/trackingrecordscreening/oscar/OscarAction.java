package gov.utah.dts.det.ccl.actions.trackingrecordscreening.oscar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningOscar;
import gov.utah.dts.det.ccl.model.enums.USState;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningOscarService;
import gov.utah.dts.det.ccl.sort.enums.TrackingRecordScreeningOscarSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.query.SortBy;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action class for background screening OSCAR tab.
 * 
 * @author Hnguyen
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "oscar-list", type = "redirectAction", params = {"screeningId", "${screeningId}" }),
	@Result(name = "success", location = "oscar_list.jsp"),
	@Result(name = "input", location = "oscar_form.jsp") 
})
public class OscarAction extends BaseTrackingRecordScreeningAction implements Preparable {

	private TrackingRecordScreeningOscarService trackingRecordScreeningOscarService;
	private CclListControls lstCtrl = new CclListControls();
	private TrackingRecordScreeningOscar oscar;
	private List<PickListValue> oscarTypes;

	public void prepareDoList() {
		// set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(TrackingRecordScreeningOscarSortBy.values())));
		lstCtrl.setSortBy(TrackingRecordScreeningOscarSortBy.getDefaultSortBy().name());
	}

	@Action(value = "oscar-list")
	public String doList() {
		if (getScreeningId() != null) {
			lstCtrl.setResults(trackingRecordScreeningOscarService.getOscarForScreening(getScreeningId(),
							TrackingRecordScreeningOscarSortBy.valueOf(lstCtrl.getSortBy())));
		}
		return SUCCESS;
	}

	@Action(value = "new-oscar")
	public String doForm() {
		return INPUT;
	}
	
	@Action(value = "edit-oscar")
	public String doEdit() {
		loadOscar();
		
		return INPUT;
	}
	
	public void prepareDoSave() {
		if (oscar.getId() != null) { // update
			loadOscar();
		} else {	// insert
			oscar.setTrackingRecordScreening(getTrackingRecordScreening());
		}
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "oscar.oscarDate", message = "OSCAR Date is required.")
		}
	)
	@Action(value = "save-oscar")
	public String doSave() {
		trackingRecordScreeningOscarService.save(oscar);
		return REDIRECT_VIEW;
	}
	
	@Action(value = "delete-oscar")
	public String doDelete() {
		trackingRecordScreeningOscarService.delete(oscar.getId());
		return REDIRECT_VIEW;
	}

	public void setTrackingRecordScreeningOscarService(TrackingRecordScreeningOscarService trackingRecordScreeningOscarService) {
		this.trackingRecordScreeningOscarService = trackingRecordScreeningOscarService;
	}

	public CclListControls getLstCtrl() {
		return lstCtrl;
	}

	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}

	public TrackingRecordScreeningOscar getOscar() {
		return oscar;
	}

	public void setOscar(TrackingRecordScreeningOscar oscar) {
		this.oscar = oscar;
	}

	public List<PickListValue> getOscarTypes() {
		if (oscarTypes == null) {
			oscarTypes = pickListService.getValuesForPickList("OSCAR Type", true);
		}
		return oscarTypes;
	}
	
	@Override
	public void prepare() throws Exception {

	}
	
	private void loadOscar(){
		if (oscar != null && oscar.getId() != null) {
			oscar = trackingRecordScreeningOscarService.load(oscar.getId());
		}
	}
	
	public List<USState> getStates() {
		return Arrays.asList(USState.values());
	}
		
}
