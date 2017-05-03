package gov.utah.dts.det.ccl.actions.trackingrecordscreening;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningCbsComm;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningDpsFbi;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMain;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMisComm;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningOscar;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningRequests;
import gov.utah.dts.det.ccl.model.enums.ScreeningLetterType;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningCbsCommService;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningDpsFbiService;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningMainService;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningMisCommService;
import gov.utah.dts.det.ccl.view.YesNoChoice;
import gov.utah.dts.det.ccl.view.YesNoList;

import java.util.Arrays;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "input", location = "cloneTrs", type = "tiles"),
	@Result(name = "success", location = "edit-tracking-record-screening", type = "redirectAction", params = { "namespace",
			"/trackingrecordscreening", "screeningId", "${trsSource.id}", "facilityId", "${facilityId}" })

})
public class CloneTrsAction extends BaseTrackingRecordScreeningAction implements Preparable {
	private TrackingRecordScreening trsSource;
	private TrackingRecordScreeningRequests request;
	private TrackingRecordScreeningOscar oscar;
	private TrackingRecordScreeningMainService trackingRecordScreeningMainService;
	private TrackingRecordScreeningDpsFbiService trackingRecordScreeningDpsFbiService;
	private TrackingRecordScreeningCbsCommService trackingRecordScreeningCbsCommService;
    private TrackingRecordScreeningMisCommService trackingRecordScreeningMisCommService;

	private List<PickListValue> misCommTypes;
	private List<PickListValue> oscarTypes;
	private List<PickListValue> billingTypes;
	private List<PickListValue> oahDecisions;

	public void setTrackingRecordScreeningMainService(TrackingRecordScreeningMainService trackingRecordScreeningMainService) {
		this.trackingRecordScreeningMainService = trackingRecordScreeningMainService;
	}

	public void setTrackingRecordScreeningDpsFbiService(TrackingRecordScreeningDpsFbiService trackingRecordScreeningDpsFbiService) {
		this.trackingRecordScreeningDpsFbiService = trackingRecordScreeningDpsFbiService;
	}

	public void setTrackingRecordScreeningCbsCommService(TrackingRecordScreeningCbsCommService trackingRecordScreeningCbsCommService) {
		this.trackingRecordScreeningCbsCommService = trackingRecordScreeningCbsCommService;
	}

    public void setTrackingRecordScreeningMisCommService(TrackingRecordScreeningMisCommService trackingRecordScreeningMisCommService) {
        this.trackingRecordScreeningMisCommService = trackingRecordScreeningMisCommService;
    }

    @SkipValidation
	@Action(value = "copyTrs")
	public String doCloning() {
		trsSource = getTrackingRecordScreening();
		trsSource.setFacility(null);

		if (trsSource.getPerson() == null) {
			Person temp = personService.getPerson(personId);
			trsSource.setPerson(temp);
		}

		// clean out ids
		cleanEntitiesIds(trsSource);
		trsSource.setId(null);

		return INPUT;
	}

	public void prepareDoSave() {
		if (personId != null) {
			trsSource.setPerson(personService.getPerson(personId));
		}
	}
	
	@Validations(
		visitorFields = { 
			@VisitorFieldValidator(fieldName = "trsSource", message = "&zwnj;") 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "trsSource.person.birthday", message = "Birthdate is required"),
			@RequiredFieldValidator(fieldName = "trsSource.facility.name", message = "Facility is required") 
		}, 
		requiredStrings = {
			@RequiredStringValidator(fieldName = "trsSource.ssnLastFour", message = "SSN last four is required"),
			@RequiredStringValidator(fieldName = "trsSource.person.firstName", message = "First name is required"),
			@RequiredStringValidator(fieldName = "trsSource.person.lastName", message = "Last name is required") 
		}, 
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "trsSource.trsDpsFbi.searchFee", message = "Search fee must be currency."),
			@ConversionErrorFieldValidator(fieldName = "trsSource.trsDpsFbi.searchFee", message = "scanFee fee must be currency.") 
		}
	)
	@Action(value = "saveCloneTrs")
	public String doSave() {
		facilityId = trsSource.getFacility().getId();

		// prepare and save all entities
		saveAllEntities(trsSource);

		return SUCCESS;
	}

	public List<PickListValue> getMisCommTypes() {
		if (misCommTypes == null) {
			misCommTypes = pickListService.getValuesForPickList("MIS Committee Type", true);
		}
		return misCommTypes;
	}

	public List<PickListValue> getBillingTypes() {
		if (billingTypes == null) {
			billingTypes = pickListService.getValuesForPickList("DPS/FBI Billing Codes", true);
		}
		return billingTypes;
	}

	public List<YesNoChoice> getYesNoChoices() {
		return Arrays.asList(YesNoChoice.values());
	}

	public List<YesNoList> getYesNoList() {
		return Arrays.asList(YesNoList.values());
	}

	public List<PickListValue> getOscarTypes() {
		if (oscarTypes == null) {
			oscarTypes = pickListService.getValuesForPickList("OSCAR Type", true);
		}
		return oscarTypes;
	}

	public List<PickListValue> getOahDecisions() {
		if (oahDecisions == null) {
			oahDecisions = pickListService.getValuesForPickList("OAH Decisions", true);
		}
		return oahDecisions;
	}

	public List<ScreeningLetterType> getLetterTypes() {
		return Arrays.asList(ScreeningLetterType.values());
	}

	public TrackingRecordScreening getTrsSource() {
		return trsSource;
	}

	public void setTrsSource(TrackingRecordScreening trsSource) {
		this.trsSource = trsSource;
	}

	public TrackingRecordScreeningOscar getOscar() {
		return oscar;
	}

	public void setOscar(TrackingRecordScreeningOscar oscar) {
		this.oscar = oscar;
	}

	@Override
	public void validate() {
		super.validate();

		if (request != null) {
			if (request.getCountry() == null) {
				addFieldError("request.country", "Country is required");
			}
			if (request.getFromDate() == null) {
				addFieldError("request.fromDate", "From Date is required");
			}
			if (request.getToDate() == null) {
				addFieldError("request.toDate", "To Date is required");
			}

		}
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public TrackingRecordScreeningRequests getRequest() {
		return request;
	}

	public void setRequest(TrackingRecordScreeningRequests request) {
		this.request = request;
	}

	private void cleanEntitiesIds(TrackingRecordScreening trsSource) {
		if (trsSource.getTrsMain() != null) {
			trsSource.getTrsMain().setId(null);
			trsSource.getTrsMain().setApprovalMailedDate(null);
		}

		if (trsSource.getTrsDpsFbi() != null) {
			trsSource.getTrsDpsFbi().setId(null);
		}

		if (trsSource.getCbsComm() != null) {
			trsSource.getCbsComm().setId(null);
		}

		if (!trsSource.getRequestsList().isEmpty()) {
			request = trsSource.getRequestsList().get(0);
			request.setId(null);
			request.setTrackingRecordScreening(null);
		}

		if (trsSource.getMisComm() != null) {
			trsSource.getMisComm().setId(null);
		}

		if (!trsSource.getOscarList().isEmpty()) {
			oscar = trsSource.getOscarList().get(0);
			oscar.setId(null);
			oscar.setTrackingRecordScreening(null);
		}

	}

	private void saveAllEntities(TrackingRecordScreening trsSource) {
		TrackingRecordScreeningMain targetMain = null;
		TrackingRecordScreeningDpsFbi targetDpsFbi = null;
		TrackingRecordScreeningCbsComm targetCbsComm = null;
		TrackingRecordScreeningMisComm targetMisComm = null;
		trsSource.setAdultInHome(Boolean.FALSE);
		trsSource.setAw(Boolean.FALSE);
		trsSource.setCpf(Boolean.FALSE);
		trsSource.setClosed(false);
		trsSource.setDspdSas(Boolean.FALSE);
		trsSource.setExpedited(Boolean.FALSE);
		trsSource.setNsc(Boolean.FALSE);
		trsSource.setRelative(Boolean.FALSE);

	    if (trsSource.getTrsMain() != null) {
	        if (trsSource.getTrsMain().hasData()) {
		        targetMain = trsSource.getTrsMain();
		    }
            trsSource.setTrsMain(null);
		}

		if (trsSource.getTrsDpsFbi() != null) {
		    if (trsSource.getTrsDpsFbi().hasData()) {
		        targetDpsFbi = trsSource.getTrsDpsFbi();
		    }
			trsSource.setTrsDpsFbi(null);
		}

		if (trsSource.getCbsComm() != null) {
		    if (trsSource.getCbsComm().hasData()) {
		        targetCbsComm = trsSource.getCbsComm();
		    }
			trsSource.setCbsComm(null);
		}

		if (request != null && request.hasData()) {
			trsSource.addTRSRequests(request);
		}

		if (trsSource.getMisComm() != null) {
		    if (trsSource.getMisComm().hasData()) {
		        targetMisComm = trsSource.getMisComm();
		    }
			trsSource.setMisComm(null);
		}

		if (oscar != null && oscar.hasData()) {
			trsSource.addTRSOscar(oscar);
		}

		trackingRecordScreeningService.save(trsSource);
		
		if (targetMain != null) {
			targetMain.setId(trsSource.getId());
			trackingRecordScreeningMainService.save(targetMain);
		}
		if (targetDpsFbi != null) {
			targetDpsFbi.setId(trsSource.getId());
			trackingRecordScreeningDpsFbiService.save(targetDpsFbi);
		}

		if (targetCbsComm != null) {
			targetCbsComm.setId(trsSource.getId());
			trackingRecordScreeningCbsCommService.save(targetCbsComm);
		}

		if (targetMisComm != null) {
		    targetMisComm.setId(trsSource.getId());
		    trackingRecordScreeningMisCommService.save(targetMisComm);
		}
	}
}
