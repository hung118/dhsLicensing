package gov.utah.dts.det.ccl.actions.facility.complianceandhistory;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.InspectionService;
import gov.utah.dts.det.ccl.sort.enums.ComplianceSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "noncompliance_list.jsp")
})
public class NoncomplianceAction extends BaseFacilityAction implements Preparable {
	
	private InspectionService inspectionService;
	
	private ListControls lstCtrl = new CclListControls();
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ComplianceSortBy.values())));
		lstCtrl.setSortBy(ComplianceSortBy.getDefaultSortBy().name());
	}
	
	public String execute() {
		lstCtrl.setResults(inspectionService.getNonComplianceHistory(getFacility().getId(), ComplianceSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	public void setInspectionService(InspectionService inspectionService) {
		this.inspectionService = inspectionService;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}