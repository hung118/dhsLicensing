package gov.utah.dts.det.ccl.service.impl;

import gov.utah.dts.det.ccl.dao.AlertDao;
import gov.utah.dts.det.ccl.model.Alert;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.service.AlertService;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.ccl.view.AlertView;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("alertService")
public class AlertServiceImpl implements AlertService {

	@Autowired
	private AlertDao alertDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Alert loadAlert(Long id) {
		return alertDao.load(id);
	}
	
	@Override
	public Alert saveAlert(Alert alert) {
		return alertDao.save(alert);
	}
	
	@Override
	public void deleteAlert(Long id) {
		alertDao.delete(id);
	}
	
	@Override
	public void delegateAlert(Long id, Long personId) {
		Alert alert = alertDao.load(id);
		alert.setObjectId(personId);
		alert.setType("PERSON");
		alertDao.save(alert);
	}
	
	@Override
	public void sendAlertToRole(List<RoleType> roles, String alertText, Date alertDate, Date dueDate) {
		for (RoleType role : roles) {
			Set<Person> people = userService.getPeople(role, true, false, false);
			for (Person p : people) {
				sendAlert(p.getId(), "PERSON", alertText, alertDate, dueDate);
			}
		}
	}
	
	@Override
	public void sendAlertToPeople(List<Long> personIds, String alertText, Date alertDate, Date dueDate) {
		for (Long id : personIds) {
			sendAlert(id, "PERSON", alertText, alertDate, dueDate);
		}
	}
	
	private void sendAlert(Long objectId, String type, String alert, Date alertDate, Date dueDate) {
		Alert al = new Alert();
		al.setObjectId(objectId);
		al.setType(type);
		al.setAlert(alert);
		al.setAlertDate(alertDate);
		al.setActionDueDate(dueDate);
		alertDao.save(al);
	}
	
	@Override
	public List<AlertView> getAlerts(Long userId) {
		return alertDao.getAlerts(userId);
	}
}