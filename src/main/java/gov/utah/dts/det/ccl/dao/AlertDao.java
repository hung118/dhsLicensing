package gov.utah.dts.det.ccl.dao;

import gov.utah.dts.det.ccl.model.Alert;
import gov.utah.dts.det.ccl.view.AlertView;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.List;

public interface AlertDao extends AbstractBaseDao<Alert, Long> {
	
	public List<AlertView> getAlerts(Long userId);
}