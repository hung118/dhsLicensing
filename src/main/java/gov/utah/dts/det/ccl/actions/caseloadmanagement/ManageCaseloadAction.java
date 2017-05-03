package gov.utah.dts.det.ccl.actions.caseloadmanagement;

import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.service.UserCaseloadCount;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.codehaus.jackson.map.ObjectMapper;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "caseload-management.home", type = "tiles")
})
public class ManageCaseloadAction extends ActionSupport {

	private FacilityService facilityService;
	
	private String caseloadData;
	
	@Override
	public String execute() throws Exception {
		List<UserCaseloadCount> caseloads = facilityService.getUserCaseloadCounts();
		ObjectMapper mapper = new ObjectMapper();
		caseloadData = mapper.writeValueAsString(caseloads);

		return SUCCESS;
	}
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public String getCaseloadData() {
		return caseloadData;
	}
	
	public CaseloadSortBy[] getSortBys() {
		return CaseloadSortBy.values();
	}
}