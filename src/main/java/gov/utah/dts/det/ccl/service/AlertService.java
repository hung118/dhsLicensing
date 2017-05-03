package gov.utah.dts.det.ccl.service;

import gov.utah.dts.det.ccl.model.Alert;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.view.AlertView;

import java.util.Date;
import java.util.List;

public interface AlertService {

	public Alert loadAlert(Long id);
	
	public Alert saveAlert(Alert alert);
	
	public void deleteAlert(Long id);
	
	public void delegateAlert(Long id, Long personId);
	
	public void sendAlertToRole(List<RoleType> roles, String alertText, Date alertDate, Date dueDate);
	
	public void sendAlertToPeople(List<Long> personIds, String alertText, Date alertDate, Date dueDate);
	
	public List<AlertView> getAlerts(Long userId);
}