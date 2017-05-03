package gov.utah.dts.det.ccl.actions.admin;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.det.ccl.actions.CclAction;
import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.ccl.model.enums.ApplicationPropertyKey;
import gov.utah.dts.det.service.ApplicationService;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.*;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/welcome")
@Results({
	@Result(name = "redirect-edit", location = "edit-welcome", type = "redirectAction"),
	@Result(name = "redirect-view", location = "view-welcome", type = "redirectAction"),
	@Result(name = "input", location = "welcome_form.jsp"),
	@Result(name = "view", location = "welcome_detail.jsp")
})
public class WelcomeAction extends ActionSupport implements Preparable, CclAction {

	private ApplicationService applicationService;
	
	private ApplicationProperty welcomeProperty;
	private String welcomeMessage;
	
	@Override
	public void prepare() throws Exception {
		welcomeProperty = applicationService.findApplicationPropertyByKey(ApplicationPropertyKey.WELCOME_MSG.getKey());
		if (welcomeProperty != null) {
			welcomeMessage = welcomeProperty.getValue();
		}
	}
	
	@Action(value = "tab", results = {
		@Result(name = "success", location = "welcome_tab.jsp")
	})
	public String doTab() {
		return SUCCESS;
	}
	
	@Action(value = "welcome-section")
	public String doSection() {
		if (StringUtils.isBlank(welcomeMessage)) {
			return INPUT;
		}
		
		return VIEW;
	}
	
	@Action(value = "view-welcome")
	public String doView() {
		if (welcomeMessage == null) {
			return REDIRECT_EDIT;
		}
		
		return VIEW;
	}
	
	@Action(value = "edit-welcome")
	public String doEdit() {
		return INPUT;
	}
	
	@Action(value = "save-welcome")
	public String doSave() {
		if (welcomeProperty == null) {
			welcomeProperty = new ApplicationProperty();
			welcomeProperty.setName(ApplicationPropertyKey.WELCOME_MSG.getKey());
		}
		welcomeProperty.setValue(welcomeMessage);
		
		applicationService.saveApplicationProperty(welcomeProperty);
		return REDIRECT_VIEW;
	}
	
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	public String getWelcomeMessage() {
		return welcomeMessage;
	}
	
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}
}