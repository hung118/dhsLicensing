package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.sort.enums.FollowUpInspectionsNeededAlertSortBy;
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
@Namespace("/alert/follow-up-inspections-needed")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/follow_up_inspections_needed_section.jsp")
})
public class FollowUpInspectionsNeededAction extends AbstractFacilityAlertAction implements Preparable {

	private String roleType;
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(FollowUpInspectionsNeededAlertSortBy.values())));
		lstCtrl.setSortBy(FollowUpInspectionsNeededAlertSortBy.getDefaultSortBy().name());
	}
	
	@Action(value = "list")
	public String doList() {
		lstCtrl.setResults(facilityService.getFollowUpInspectionsNeeded(getUser().getPerson().getId(), roleType, FollowUpInspectionsNeededAlertSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	@Override
	protected List<? extends MailingLabel> getMailingLabels() {
		return facilityService.getFollowUpInspectionsNeeded(getUser().getPerson().getId(), roleType, FollowUpInspectionsNeededAlertSortBy.valueOf(lstCtrl.getSortBy()));
	}
	
	public String getRoleType() {
		return roleType;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
}