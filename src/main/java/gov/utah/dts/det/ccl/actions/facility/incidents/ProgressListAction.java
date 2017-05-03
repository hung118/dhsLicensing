package gov.utah.dts.det.ccl.actions.facility.incidents;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.IncidentService;
import gov.utah.dts.det.ccl.sort.enums.IncidentSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "progress_list.jsp")
})
public class ProgressListAction extends BaseFacilityAction implements Preparable {
	
	private IncidentService incidentService;
	
	private ListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new ListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(IncidentSortBy.values())));
		lstCtrl.setSortBy(IncidentSortBy.getDefaultSortBy().name());
	}
	
	public String execute() {
		lstCtrl.setResults(incidentService.getIncidentsForFacility(getFacility().getId(), null,
				IncidentSortBy.valueOf(lstCtrl.getSortBy()), false));
		
		return SUCCESS;
	}
	
	public void setIncidentService(IncidentService incidentService) {
		this.incidentService = incidentService;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}