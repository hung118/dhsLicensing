package gov.utah.dts.det.ccl.actions.caseloadmanagement;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.view.BasicFacilityInformation;
import gov.utah.dts.det.ccl.service.CclServiceException;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.view.JsonResponse;
import gov.utah.dts.det.ccl.view.ViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.security.access.AccessDeniedException;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "jacksonJson", params = {"status", "${response.status}", "objectName", "response.responseObject"})
})
public class UserCaseloadAction extends ActionSupport {
	
	private FacilityService facilityService;
	
	private Person specialist;
	private CaseloadSortBy sortBy;
	
	private JsonResponse response;
	
	@Override
	public String execute() throws Exception {
		try {
			List<BasicFacilityInformation> facilities = facilityService.getCaseload(specialist);
			List<FacilityCaseloadView> caseload = new ArrayList<FacilityCaseloadView>();
			for (BasicFacilityInformation fac : facilities) {
				caseload.add(new FacilityCaseloadView(fac.getId(), fac.getName(), fac.getLicenseDescriptor(), fac.getLocationAddress().getCity(), fac.getLocationAddress().getZipCode(), fac.getStatus().name()));
			}
			CaseloadComparator comp = new CaseloadComparator(sortBy);
			Collections.sort(caseload, comp);
			response = new JsonResponse(200, caseload);
		} catch (CclServiceException cse) {
			ViewUtils.addActionErrors(this, this, cse.getErrors());
			response = new JsonResponse(400, getActionErrors());
		} catch (AccessDeniedException ade) {
			response = new JsonResponse(401, ade.getMessage());
		}
		
		return SUCCESS;
	}
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public Person getSpecialist() {
		return specialist;
	}
	
	public void setSpecialist(Person specialist) {
		this.specialist = specialist;
	}
	
	public CaseloadSortBy getSortBy() {
		return sortBy;
	}
	
	public void setSortBy(CaseloadSortBy sortBy) {
		this.sortBy = sortBy;
	}
	
	public JsonResponse getResponse() {
		return response;
	}
}