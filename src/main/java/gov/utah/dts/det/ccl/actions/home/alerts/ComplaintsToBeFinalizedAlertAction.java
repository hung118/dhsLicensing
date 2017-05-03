package gov.utah.dts.det.ccl.actions.home.alerts;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/alert/complaints-to-be-finalized")
@Results({
	@Result(name = "success", location = "/WEB-INF/jsps/alert/complaints_to_be_finalized_section.jsp")
})
public class ComplaintsToBeFinalizedAlertAction extends BaseAlertAction implements Preparable {

	@Override
	public void prepare() throws Exception {
		super.prepare();
	}
	
	@Action(value = "list")
	public String doList() {
//		lstCtrl.setResults(facilityService.getExpiringAccreditations(getUser().getPerson().getId()));
		
		return SUCCESS;
	}
}
