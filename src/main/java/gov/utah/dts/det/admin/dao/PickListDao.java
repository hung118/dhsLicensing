/**
 * $Rev: 76 $:
 * $LastChangedDate: 2009-04-30 08:50:19 -0600 (Thu, 30 Apr 2009) $:
 * $Author: danolsen $:
 */
package gov.utah.dts.det.admin.dao;

import gov.utah.dts.det.admin.model.PickList;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.List;

/**
 * @author DOLSEN
 * 
 */
public interface PickListDao extends AbstractBaseDao<PickList, Long> {
	
	public List<PickList> getPickLists(Long pickListGroupId);
	
	public PickList loadByName(String name);
	
	public String getReportCodes(Long serviceCode, String columnName);
	
	public void updateReportCodesDependencies(Long serviceCode, String programCode, String specificServiceCode, String ageGroup);
	
}