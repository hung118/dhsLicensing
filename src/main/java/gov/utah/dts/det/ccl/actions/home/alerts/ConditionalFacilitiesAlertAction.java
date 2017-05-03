package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.sort.enums.ConditionalFacilitiesAlertSortBy;
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
@Namespace("/alert/conditional-facilities")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/conditional_facilities_section.jsp")
})
public class ConditionalFacilitiesAlertAction extends AbstractFacilityAlertAction implements Preparable {

	private RoleType roleType;
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ConditionalFacilitiesAlertSortBy.values())));
		lstCtrl.setSortBy(ConditionalFacilitiesAlertSortBy.getDefaultSortBy().name());
	}
	
	@Action(value = "list")
	public String doList() {
		boolean showWholeRegion = false;
		if (roleType == RoleType.ROLE_OFFICE_SPECIALIST) {
			showWholeRegion = true;
		}
		lstCtrl.setResults(facilityService.getFacilitiesOnConditionalLicenses(getUser().getPerson().getId(), showWholeRegion, ConditionalFacilitiesAlertSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	@Override
	protected List<? extends MailingLabel> getMailingLabels() {
		boolean showWholeRegion = false;
		if (roleType == RoleType.ROLE_OFFICE_SPECIALIST) {
			showWholeRegion = true;
		}
		return facilityService.getFacilitiesOnConditionalLicenses(getUser().getPerson().getId(), showWholeRegion, ConditionalFacilitiesAlertSortBy.valueOf(lstCtrl.getSortBy()));
	}
	
	public RoleType getRoleType() {
		return roleType;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = RoleType.valueOf(roleType);
	}
}