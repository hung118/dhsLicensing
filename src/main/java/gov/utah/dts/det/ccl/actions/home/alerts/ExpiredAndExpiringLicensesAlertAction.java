package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.sort.enums.ExpiredAndExpiringLicensesAlertSortBy;
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
@Namespace("/alert/expired-licenses")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/expired_licenses_section.jsp")
})
public class ExpiredAndExpiringLicensesAlertAction extends AbstractFacilityAlertAction implements Preparable {
	
	private boolean showWholeRegion = false;
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ExpiredAndExpiringLicensesAlertSortBy.values())));
		lstCtrl.setSortBy(ExpiredAndExpiringLicensesAlertSortBy.getDefaultSortBy().name());
	}
	
	@Action(value = "list")
	public String doList() {
		lstCtrl.setResults(facilityService.getExpiredAndExpiringLicenses(getUser().getPerson().getId(), showWholeRegion, ExpiredAndExpiringLicensesAlertSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	@Override
	protected List<? extends MailingLabel> getMailingLabels() {
		return facilityService.getExpiredAndExpiringLicenses(getUser().getPerson().getId(), showWholeRegion, ExpiredAndExpiringLicensesAlertSortBy.valueOf(lstCtrl.getSortBy()));
	}
	
	public boolean isShowWholeRegion() {
		return showWholeRegion;
	}
	
	public void setShowWholeRegion(boolean showWholeRegion) {
		this.showWholeRegion = showWholeRegion;
	}
}