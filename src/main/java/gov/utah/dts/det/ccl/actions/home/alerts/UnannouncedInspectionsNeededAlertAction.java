package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.sort.enums.UnannouncedInspectionsNeededAlertSortBy;
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

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/alert/unannounced-inspections-needed")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/unannounced_inspections_needed_section.jsp")
})
public class UnannouncedInspectionsNeededAlertAction extends AbstractFacilityAlertAction {
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(UnannouncedInspectionsNeededAlertSortBy.values())));
		lstCtrl.setSortBy(UnannouncedInspectionsNeededAlertSortBy.getDefaultSortBy().name());
	}
	
	@Action(value = "list")
	public String doList() {
		lstCtrl.setResults(facilityService.getUnannouncedInspectionsNeeded(getUser().getPerson().getId(), false, UnannouncedInspectionsNeededAlertSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	@Override
	protected List<? extends MailingLabel> getMailingLabels() {
		return facilityService.getUnannouncedInspectionsNeeded(getUser().getPerson().getId(), false, UnannouncedInspectionsNeededAlertSortBy.valueOf(lstCtrl.getSortBy()));
	}
}