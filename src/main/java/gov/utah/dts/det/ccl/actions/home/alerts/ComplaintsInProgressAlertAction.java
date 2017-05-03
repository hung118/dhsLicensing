package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.service.ComplaintService;
import gov.utah.dts.det.ccl.sort.enums.ComplaintsInProgressAlertSortBy;
import gov.utah.dts.det.query.SortBy;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/alert/complaints-in-progress")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/complaints_in_progress_section.jsp")
})
public class ComplaintsInProgressAlertAction extends BaseAlertAction implements Preparable {
	
	private final String SPECIALIST = "SPECIALIST";

	@Autowired
	private ComplaintService complaintService;
	
	private String roleType;
	
	@Override
	public void prepare() throws Exception {
		super.prepare();
		lstCtrl.setSortBys(new ArrayList<SortBy>(Arrays.asList(ComplaintsInProgressAlertSortBy.values())));
		lstCtrl.setSortBy(ComplaintsInProgressAlertSortBy.getDefaultSortBy().name());
	}
	
	@Action(value = "list")
	public String doList() {
		Long userid = null;
		if (roleType != null && roleType.equals(SPECIALIST)) {
			userid = getUser().getPerson().getId();
		}
		lstCtrl.setResults(complaintService.getComplaintsInProgress(userid, roleType, ComplaintsInProgressAlertSortBy.valueOf(lstCtrl.getSortBy())));
		
		return SUCCESS;
	}
	
	public String getRoleType() {
		return roleType;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
}