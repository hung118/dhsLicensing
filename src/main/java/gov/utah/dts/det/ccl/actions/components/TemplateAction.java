package gov.utah.dts.det.ccl.actions.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Results({
	@Result(name = "success", type = "json", params = {"root", "response"})
})
public class TemplateAction extends ActionSupport {

	private String templateName;
	
	private Map<String, Object> response;
	
	public String execute() {
		response = new HashMap<String, Object>();
		response.put("template", "This is another template {{= val}}");
		
		return SUCCESS;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public Map<String, Object> getResponse() {
		return response;
	}
}