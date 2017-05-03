package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.service.CmpService;
import gov.utah.dts.det.ccl.sort.enums.CmpSortBy;
import gov.utah.dts.det.query.ListControls;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/license_fees_section.jsp")
})
public class LicenseFeesAction extends BaseAlertAction implements Preparable {

	private CmpService cmpService;
	
	private ListControls lstCtrl;
	
	@Override
	public void prepare() throws Exception {
		//set up the list controls
		lstCtrl = new ListControls();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(CmpSortBy.values())));
		lstCtrl.setSortBy(CmpSortBy.getDefaultSortBy().name());		
	}
	
	public String execute() {
		lstCtrl.setResults(cmpService.getCmpsForFacility(null, null,
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