package gov.utah.dts.det.ccl.actions.facility.cmps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.CmpService;
import gov.utah.dts.det.ccl.sort.enums.CmpSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "waiting_list.jsp")
})
public class WaitingListAction extends BaseFacilityAction implements Preparable {
	
	private CmpService cmpService;
	
	private ListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new ListControls();
		
		// remove Facility Name, don't need it here
		List<SortBy> temp = new ArrayList<SortBy>();
		for (SortBy s : Arrays.asList(CmpSortBy.values())) {
			if (!"Facility Name".equals(s.getLabel())) {
				temp.add(s);
			}
		}
		lstCtrl.setSortBys(temp);
		
		lstCtrl.setSortBy(CmpSortBy.getDefaultSortBy().name());		
	}
	
	public String execute() {
		lstCtrl.setResults(cmpService.getCmpsForFacility(getFacility().getId(), null,
				CmpSortBy.valueOf(lstCtrl.getSortBy()), false));
		
		return SUCCESS;
	}
	
	public void setCmpService(CmpService cmpService) {
		this.cmpService = cmpService;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
}