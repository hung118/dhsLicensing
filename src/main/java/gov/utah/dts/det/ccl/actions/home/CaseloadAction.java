package gov.utah.dts.det.ccl.actions.home;

import gov.utah.dts.det.ccl.actions.caseloadmanagement.CaseloadSortBy;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.FacilityCaseloadView;
import gov.utah.dts.det.ccl.service.FacilityService;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "caseload", type = "tiles")	
})
public class CaseloadAction extends ActionSupport {

	private FacilityService facilityService;
	
	private Person specialist;
	private RoleType roleType;
	private CaseloadSortBy sortBy;
	
	private List<FacilityCaseloadView> caseload;
	
	@Override
	public String execute() throws Exception {
		caseload = facilityService.getUserCaseload(specialist.getId(), roleType, sortBy);
		
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
	
	public RoleType getRoleType() {
		return roleType;
	}
	
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
	public CaseloadSortBy getSortBy() {
		return sortBy;
	}
	
	public void setSortBy(CaseloadSortBy sortBy) {
		this.sortBy = sortBy;
	}
	
	public List<FacilityCaseloadView> getCaseload() {
		return caseload;
	}
}