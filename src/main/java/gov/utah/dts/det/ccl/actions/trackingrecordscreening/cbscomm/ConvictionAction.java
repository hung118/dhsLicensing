package gov.utah.dts.det.ccl.actions.trackingrecordscreening.cbscomm;

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
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConviction;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningConvictionService;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-list", location = "list-convictions", type = "redirectAction", params = {"screeningId", "${screeningId}"}),
	@Result(name = "success", location = "conviction_list.jsp"),
	@Result(name = "input", location="conviction_form.jsp")
})
public class ConvictionAction extends BaseTrackingRecordScreeningAction implements Preparable {
	
	private final String REDIRECT_LIST = "redirect-list";

	private TrackingRecordScreeningConvictionService convictionService;
	private List<TrackingRecordScreeningConviction> convictions;
	private TrackingRecordScreeningConviction conviction;
	private Long convictionId;
	private PickListValue convictionType;
	private List<PickListValue> convictionTypes;

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Action(value="list-convictions")
	public String doList() {
		if (getScreeningId() != null) {
			convictions = convictionService.getConvictionsForScreening(getScreeningId());
		}
		if (convictions == null) {
			convictions = new ArrayList<TrackingRecordScreeningConviction>();
		}
		return SUCCESS;
	}

	@Action(value="edit-conviction")
	public String doEdit() {
		if (convictionId != null) {
			conviction = convictionService.load(convictionId);
		}
		if (conviction == null) {
			conviction = new TrackingRecordScreeningConviction();
		}
		convictionType = conviction.getConvictionType();
		return INPUT;
	}

	public void prepareDoSave() {
		if (convictionId != null) {
			conviction = convictionService.load(convictionId);
		}
		if (conviction == null) {
			conviction = new TrackingRecordScreeningConviction();
		}
	}
	
	@Action(value="save-conviction")
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "conviction.convictionDate", message = "Conviction date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "convictionType", message = "Conviction type is required.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "conviction.convictionDate", message = "Conviction date is required.", shortCircuit = true)
		}
	)
	public String doSave() {
		conviction.setTrackingRecordScreening(getTrackingRecordScreening());
		conviction.setConvictionType(convictionType);
		conviction = convictionService.save(conviction);
		convictionService.evict(conviction);
		return REDIRECT_LIST;
	}

	@Action(value="delete-conviction")
	public String doDelete() {
		if (convictionId != null) {
			conviction = convictionService.load(convictionId);
			if (conviction != null) {
				convictionService.delete(convictionId);
				convictionService.evict(conviction);
			}
		}
		return REDIRECT_LIST;
	}

	public TrackingRecordScreeningConviction getConviction() {
		return conviction;
	}

	public void setConviction(TrackingRecordScreeningConviction conviction) {
		this.conviction = conviction;
	}

	public Long getConvictionId() {
		return convictionId;
	}

	public void setConvictionId(Long id) {
		this.convictionId = id;
	}

	public List<TrackingRecordScreeningConviction> getConvictions() {
		return convictions;
	}
	
	public PickListValue getConvictionType() {
		return convictionType;
	}

	public void setConvictionType(PickListValue convictionType) {
		this.convictionType = convictionType;
	}

	public void setTrackingRecordScreeningConvictionService(TrackingRecordScreeningConvictionService service) {
		this.convictionService = service;
	}

	public List<PickListValue> getConvictionTypes() {
		if (convictionTypes == null) {
			convictionTypes = pickListService.getValuesForPickList("CBS Comm Conviction Types", true);
		}
		return convictionTypes;
	}
}
