package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.service.VarianceService;
import gov.utah.dts.det.ccl.sort.enums.VariancesExpiringAlertSortBy;
import gov.utah.dts.det.ccl.view.MailingLabel;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/alert/variances-expiring")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/variances_expiring_section.jsp")
})
public class VariancesExpiringAlertAction extends AbstractFacilityAlertAction implements Preparable {
	
	private VarianceService varianceService;
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(VariancesExpiringAlertSortBy.values())));
		lstCtrl.setSortBy(VariancesExpiringAlertSortBy.getDefaultSortBy().name());
	}
	
	@Action(value = "list")
	public String doList() {
		lstCtrl.setResults(varianceService.getVariancesExpiring(getUser().getPerson().getId(), false, VariancesExpiringAlertSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	@Override
	protected List<? extends MailingLabel> getMailingLabels() {
		//TODO: implement this
		return new ArrayList<MailingLabel>();
		
		//return varianceService.getVariancesExpiring(getUser().getPerson().getId(), false, VariancesExpiringAlertSortBy.valueOf(lstCtrl.getSortBy()));
	}
	
	public void setVarianceService(VarianceService varianceService) {
		this.varianceService = varianceService;
	}
}