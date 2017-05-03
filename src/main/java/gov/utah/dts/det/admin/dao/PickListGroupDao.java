/**
 * $Rev: 53 $:
 * $LastChangedDate: 2009-03-31 11:05:36 -0600 (Tue, 31 Mar 2009) $:
 * $Author: mhepworth $:
 */
package gov.utah.dts.det.admin.dao;

import gov.utah.dts.det.admin.model.PickListGroup;
import gov.utah.dts.det.dao.AbstractBaseDao;

import java.util.List;

/**
 * @author DOLSEN
 * 
 */
public interface PickListGroupDao extends AbstractBaseDao<PickListGroup, Long> {
	
	public List<PickListGroup> getPickListGroups();
}