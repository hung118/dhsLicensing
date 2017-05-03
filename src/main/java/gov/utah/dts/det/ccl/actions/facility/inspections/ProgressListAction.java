package gov.utah.dts.det.ccl.actions.facility.inspections;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.InspectionService;
import gov.utah.dts.det.ccl.sort.enums.InspectionSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "progress_list.jsp")
})
public class ProgressListAction extends BaseFacilityAction implements Preparable {
	
	private InspectionService inspectionService;
	
	private ListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new ListControls();
		lstCtrl.setSortBys(getSortBys());
		lstCtrl.setSortBy(InspectionSortBy.getDefaultSortBy().name());
	}
	
	public String execute() {
		lstCtrl.setResults(inspectionService.getInspectionsForFacility(getFacility().getId(), null,
				InspectionSortBy.valueOf(lstCtrl.getSortBy()), false));
		
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
	
	private List<SortBy> getSortBys() {
		List<SortBy> sortBys = new ArrayList<SortBy>();
		for (InspectionSortBy sortBy : InspectionSortBy.values()) {
			if (sortBy.isBasicSort()) {
				sortBys.add(sortBy);
			}
		}
		return sortBys;
	}
}