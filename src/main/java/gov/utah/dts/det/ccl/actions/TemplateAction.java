package gov.utah.dts.det.ccl.actions;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", location = "jquerytemplates/_${group}.tmpl.html")
})
public class TemplateAction extends ActionSupport {

	private String group;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
}