package gov.utah.dts.det.ccl.actions.admin;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/noncompliance")
public class NoncomplianceTabAction extends ActionSupport {

	@Action(value = "tab", results = {
		@Result(name = "success", location = "noncompliance_tab.jsp")
	})
	public String doTab() {
		return SUCCESS;
	}
}