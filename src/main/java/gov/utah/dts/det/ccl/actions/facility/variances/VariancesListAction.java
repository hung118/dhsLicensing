package gov.utah.dts.det.ccl.actions.facility.variances;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.service.VarianceService;
import gov.utah.dts.det.ccl.sort.enums.VarianceSortBy;
import gov.utah.dts.det.ccl.view.CclListControls;
import gov.utah.dts.det.ccl.view.ListRange;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "variances_list.jsp")
})
public class VariancesListAction extends BaseFacilityAction implements Preparable {
	
	private VarianceService varianceService;

	private CclListControls lstCtrl = new CclListControls();
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(VarianceSortBy.values())));
		lstCtrl.setSortBy(VarianceSortBy.getDefaultSortBy().name());
		lstCtrl.setRanges(ListRange.getTwentyFourMonthOptions());
		lstCtrl.setRange(ListRange.SHOW_PAST_24_MONTHS);
	}
	
	public String execute() {
		lstCtrl.setResults(varianceService.getVariancesForFacility(getFacility().getId(), lstCtrl.getRange(),
				VarianceSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
		
	}
	
	public void setVarianceService(VarianceService varianceService) {
		this.varianceService = varianceService;
	}
	
	public CclListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(CclListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
}