package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.sort.enums.NewApplicationPendingDeadlinesAlertSortBy;
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
@Namespace("/alert/new-application-pending-deadlines")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/new_application_pending_deadlines_section.jsp")
})
public class NewApplicationPendingDeadlinesAlertAction extends AbstractFacilityAlertAction implements Preparable {
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(NewApplicationPendingDeadlinesAlertSortBy.values())));
		lstCtrl.setSortBy(NewApplicationPendingDeadlinesAlertSortBy.getDefaultSortBy().name());
	}
	
	@Action(value = "list")
	public String doList() {
		lstCtrl.setResults(facilityService.getNewApplicationPendingDeadlines(getUser().getPerson().getId(), true, NewApplicationPendingDeadlinesAlertSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	@Override
	protected List<? extends MailingLabel> getMailingLabels() {
		return facilityService.getNewApplicationPendingDeadlines(getUser().getPerson().getId(), true, NewApplicationPendingDeadlinesAlertSortBy.valueOf(lstCtrl.getSortBy()));
	}
}