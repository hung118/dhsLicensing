package gov.utah.dts.det.ccl.actions.trackingrecordscreening.requests;

import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningRequests;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningRequestService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-list", location = "list-requests", type = "redirectAction", params = {"screeningId", "${screeningId}"}),
	@Result(name = "success", location = "requests_list.jsp"),
	@Result(name = "input", location = "requests_form.jsp")
})
public class RequestsAction extends BaseTrackingRecordScreeningAction implements Preparable {
	
	private final String REDIRECT_LIST = "redirect-list";

	private TrackingRecordScreeningRequestService trackingRecordScreeningRequestService;
	private TrackingRecordScreeningRequests screeningRequest;
	private Long requestId;

	@Action(value="edit-request")
	public String doEditRequest() {
		if (requestId != null) {
			screeningRequest = trackingRecordScreeningRequestService.load(requestId);
		}
		if (screeningRequest == null) {
			screeningRequest = new TrackingRecordScreeningRequests();
		}
		return INPUT;
	}

	public void prepareDoSaveRequest() {
		if (requestId != null) {
			screeningRequest = trackingRecordScreeningRequestService.load(requestId);
		}
		if (screeningRequest == null) {
			screeningRequest = new TrackingRecordScreeningRequests();
		}
	}
	
	@Action(value="save-request")
	@Validations(
		requiredStrings = {
				@RequiredStringValidator(fieldName = "screeningRequest.country", message = "Country is required.", shortCircuit = true)
		},

		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "screeningRequest.fromDate", message = "From Date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "screeningRequest.toDate", message = "To Date is not a valid date. (MM/DD/YYYY)", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "screeningRequest.receivedDate", message = "Received date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "screeningRequest.receivedDate", message = "Received date is required.", shortCircuit = true)
		}
	)
	public String doSaveRequest() {
		screeningRequest.setTrackingRecordScreening(getTrackingRecordScreening());
		screeningRequest = trackingRecordScreeningRequestService.save(screeningRequest);
		trackingRecordScreeningRequestService.evict(screeningRequest);

		return REDIRECT_LIST;
	}

	@Action(value="delete-request")
	public String doDeleteRequest() {
		if (requestId != null) {
			screeningRequest = trackingRecordScreeningRequestService.load(requestId);
			if (screeningRequest != null) {
				trackingRecordScreeningRequestService.delete(requestId);
				trackingRecordScreeningRequestService.evict(screeningRequest);
			}
		}
		return REDIRECT_LIST;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public void setTrackingRecordScreeningRequestService(TrackingRecordScreeningRequestService trackingRecordScreeningRequestService) {
		this.trackingRecordScreeningRequestService = trackingRecordScreeningRequestService;
	}

	public TrackingRecordScreeningRequests getScreeningRequest() {
		return screeningRequest;
	}

	public void setScreeningRequest(TrackingRecordScreeningRequests screeningRequest) {
		this.screeningRequest = screeningRequest;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
	}

}
