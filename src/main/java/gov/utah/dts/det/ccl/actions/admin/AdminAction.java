package gov.utah.dts.det.ccl.actions.admin;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin")
public class AdminAction extends ActionSupport {
	
	@Action(value = "index", results = {
		@Result(name = "success", location = "admin", type = "tiles")
	})
	public String doIndex() {
		return SUCCESS;
	}
}