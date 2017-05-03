package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.service.VarianceService;
import gov.utah.dts.det.ccl.sort.enums.VarianceAlertSortBy;
import gov.utah.dts.det.query.SortBy;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/manager_variances_section.jsp")
})
public class ManagerVariancesAction extends BaseAlertAction implements Preparable {
	
	private VarianceService varianceService;
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(VarianceAlertSortBy.values())));
		lstCtrl.setSortBy(VarianceAlertSortBy.getDefaultSortBy().name());
		
	}

	public String execute() throws Exception {
		lstCtrl.setResults(varianceService.getManagerVariances(VarianceAlertSortBy.valueOf(lstCtrl.getSortBy())));
		return SUCCESS;
	}
	
	public void setVarianceService(VarianceService varianceService) {
		this.varianceService = varianceService;
	}

}