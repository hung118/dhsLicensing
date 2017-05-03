package gov.utah.dts.det.ccl.actions.home.alerts;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.AlertService;
import gov.utah.dts.det.ccl.view.AlertView;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.struts2.convention.annotation.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/alert/alerts")
@Results({
	@Result(name = "redirect-view", location = "list", type = "redirectAction", params = {"personId", "${user.person.id}"}),
	@Result(name = "success", location = "/WEB-INF/jsps/alert/alerts_section.jsp")
})
public class AlertAction extends BaseAlertAction {

	private AlertService alertService;
	
	private Long alertId;
	private Long recipientId;
	
	private List<AlertView> alerts;
	private Set<Person> recipients;
	
	@Action(value = "list")
	public String doList() {
		alerts = alertService.getAlerts(getUser().getPerson().getId());
		
		return SUCCESS;
	}
	
	@Action(value = "delete")
	public String doDelete() {
		alertService.deleteAlert(alertId);
		
		return REDIRECT_VIEW;
	}
	
	@Action(value = "delegate")
	public String doDelegate() {
		if (alertId != null && recipientId != null) {
			//TODO: implement error handling here.
			alertService.delegateAlert(alertId, recipientId);
		}
		
		return REDIRECT_VIEW;
	}
	
	public void setAlertService(AlertService alertService) {
		this.alertService = alertService;
	}
	
	public Long getAlertId() {
		return alertId;
	}
	
	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}
	
	public Long getRecipientId() {
		return recipientId;
	}
	
	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}
	
	public List<AlertView> getAlerts() {
		return alerts;
	}
	
	public Set<Person> getRecipients() {
		if (recipients == null) {
			List<RoleType> roles = null;
			if (SecurityUtil.isUserPartner()) {
				User u = SecurityUtil.getUser();
				roles = new ArrayList<RoleType>(u.getRoles());
			}
			recipients = userService.getPeople(roles, true, true, false);
		}
		return recipients;
	}
	
	public String getRecipientsJson() throws JSONException {
		Set<Person> recips = getRecipients();
		JSONArray arr = new JSONArray();
		for (Person p : recips) {
			JSONObject obj = new JSONObject();
			obj.put("id", p.getId());
			obj.put("name", p.getFirstAndLastName());
			arr.put(obj);
		}
		return arr.toString();
	}
}