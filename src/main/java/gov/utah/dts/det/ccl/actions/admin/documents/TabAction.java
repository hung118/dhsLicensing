package gov.utah.dts.det.ccl.actions.admin.documents;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "documents_tab.jsp")
})
public class TabAction extends ActionSupport {

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}