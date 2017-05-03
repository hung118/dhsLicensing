/**
 * $Rev: 111 $:
 * $LastChangedDate: 2009-06-03 07:50:57 -0600 (Wed, 03 Jun 2009) $:
 * $Author: mhepworth $:
 */
package gov.utah.dts.det.admin.service;

import gov.utah.dts.det.admin.model.PickList;
import gov.utah.dts.det.admin.model.PickListGroup;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.model.PickListValueRelationship;
import gov.utah.dts.det.admin.view.KeyValuePair;
import gov.utah.dts.det.ccl.view.admin.PickListExtensionMetadata;

import java.util.List;

/**
 * @author DOLSEN
 * 
 */
public interface PickListService {

	public List<PickListGroup> getPickListGroups();
	
	public List<PickList> getPickLists(Long pickListGroupId);
	
	public PickList loadPickListById(Long id);
	
	public PickList loadPickListByName(String name);
	
	public PickList savePickList(PickList pickList);
	
	public PickListValue loadPickListValueById(Long id);

	public List<PickListValue> getValuesForPickList(String pickListName, boolean includeOnlyActive);

	public List<PickListValue> searchPickListByValue(String pickListName, String searchValue, boolean includeOnlyActive);

	public PickListValue getDefaultPickListValue(String pickListName);
	
	public PickListValue getPickListValueByValue(String pickListValueValue, String pickListName);
	
	public PickListValueRelationship savePickListValueRelationship(PickListValueRelationship relationship);
	
	public void deletePickListValueRelationship(PickListValueRelationship relationship);
	
	public List<PickListValueRelationship> getAllChildPickListValuesInRelationship(String relationshipType);
	
	public List<KeyValuePair> getChildValuesForPickListValueAsKeyValuePairs(Long pickListValueId, String relationshipType, boolean includeOnlyActive);
	
	public PickListExtensionMetadata getPickListExtensionMetadataForPickList(Long pickListId) throws Exception;
	
	public String getReportCodes(Long serviceCode, String columnName);
	
	public void updateReportCodesDependencies(Long serviceCode, String programCode, String specificServiceCode, String ageGroup);
	
	public void evict(final Object entity);
}