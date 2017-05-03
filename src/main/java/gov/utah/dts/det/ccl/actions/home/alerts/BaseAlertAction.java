package gov.utah.dts.det.ccl.actions.home.alerts;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import gov.utah.dts.det.ccl.actions.CclAction;
import gov.utah.dts.det.ccl.model.User;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.service.SecurityService;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.query.ListControls;

@SuppressWarnings("serial")
public class BaseAlertAction extends ActionSupport implements Preparable, CclAction {

	protected UserService userService;
	protected SecurityService securityService;
	protected FacilityService facilityService;
	
	protected Long personId;
	protected ListControls lstCtrl;

	protected User user;
	
	@Override
	public void prepare() throws Exception {
		lstCtrl = new ListControls();
	}
	
	protected boolean hasAccessToUser() {
		if (personId == null) {
			return true;
		}
		return securityService.isPersonAccessibleByCurrentPerson(personId);
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	
	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public ListControls getLstCtrl() {
		return lstCtrl;
	}
	
	public void setLstCtrl(ListControls lstCtrl) {
		this.lstCtrl = lstCtrl;
	}
	
	public User getUser() {
		if (user == null) {
			if (personId == null) {
				user = SecurityUtil.getUser();
			} else {
				user = userService.loadUserByPersonId(personId);
			}
		}
		return user;
	}
}