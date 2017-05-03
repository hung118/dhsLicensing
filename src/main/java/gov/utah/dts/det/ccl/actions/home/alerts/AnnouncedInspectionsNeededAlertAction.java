package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.sort.enums.AnnouncedInspectionsNeededAlertSortBy;
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
@Namespace("/alert/announced-inspections-needed")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/announced_inspections_needed_section.jsp")
})
public class AnnouncedInspectionsNeededAlertAction extends AbstractFacilityAlertAction {

	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(AnnouncedInspectionsNeededAlertSortBy.values())));
		lstCtrl.setSortBy(AnnouncedInspectionsNeededAlertSortBy.getDefaultSortBy().name());
	}
	
	@Action(value = "list")
	public String doList() {
		lstCtrl.setResults(facilityService.getAnnouncedInspectionsNeeded(getUser().getPerson().getId(), false, AnnouncedInspectionsNeededAlertSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	@Override
	protected List<? extends MailingLabel> getMailingLabels() {
		return facilityService.getAnnouncedInspectionsNeeded(getUser().getPerson().getId(), false, AnnouncedInspectionsNeededAlertSortBy.valueOf(lstCtrl.getSortBy()));
	}
}