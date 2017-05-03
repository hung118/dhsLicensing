/**
 * $Rev: 154 $:
 * $LastChangedDate: 2009-07-02 07:23:30 -0600 (Thu, 02 Jul 2009) $:
 * $Author: dunnjo $:
 */
package gov.utah.dts.det.admin.service.impl;

import gov.utah.dts.det.admin.dao.PickListDao;
import gov.utah.dts.det.admin.dao.PickListGroupDao;
import gov.utah.dts.det.admin.dao.PickListValueRelationshipDao;
import gov.utah.dts.det.admin.model.PickList;
import gov.utah.dts.det.admin.model.PickListGroup;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.model.PickListValueRelationship;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.admin.view.KeyValuePair;
import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.ccl.model.view.JSONString;
import gov.utah.dts.det.ccl.view.admin.PickListExtensionMetadata;
import gov.utah.dts.det.ccl.view.admin.PickListValueExtensionField;
import gov.utah.dts.det.ccl.view.admin.PickListValueExtensionFieldType;
import gov.utah.dts.det.repository.PickListValueRepository;
import gov.utah.dts.det.service.ApplicationService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

/**
 * @author DOLSEN
 * 
 */
@SuppressWarnings("unchecked")
@Service("pickListService")
public class PickListServiceImpl implements PickListService {
	
	private static final Sort DEFAULT_PLV_SORT;
	
	static {
		DEFAULT_PLV_SORT = new Sort(new Order(Direction.ASC, "sortOrder"), new Order(Direction.DESC, "active"));
	}
	
	@Autowired
	private PickListGroupDao pickListGroupDao;
	
	@Autowired
	private PickListDao pickListDao;
	
	@Autowired
	private PickListValueRepository pickListValueRepository;
	
	@Autowired
	private PickListValueRelationshipDao pickListValueRelationshipDao;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Override
	public List<PickListGroup> getPickListGroups() {
		return pickListGroupDao.getPickListGroups();
	}
	
	@Override
	public List<PickList> getPickLists(Long pickListGroupId) {
		return pickListDao.getPickLists(pickListGroupId);
	}
	
	@Override
	public PickList loadPickListById(Long id) {
		return pickListDao.load(id);
	}
	
	@Override
	public PickList loadPickListByName(String name) {
		return pickListDao.loadByName(name);
	}
	
	@Override
	public PickList savePickList(PickList pickList) {
		return pickListDao.save(pickList);
	}
	
	@Override
	public PickListValue loadPickListValueById(Long id) {
		return pickListValueRepository.findOne(id);
	}
	
	@Override
	public List<PickListValue> getValuesForPickList(String pickListName, boolean includeOnlyActive) {
		List<PickListValue> values = pickListValueRepository.findByPickListName(pickListName, DEFAULT_PLV_SORT);
		if (includeOnlyActive) {
			for (Iterator<PickListValue> itr = values.iterator(); itr.hasNext();) {
				PickListValue value = itr.next();
				if (!value.isActive()) {
					itr.remove();
				}
			}
		}
		return values;
	}

	@Override
	public List<PickListValue> searchPickListByValue(String pickListName, String searchValue, boolean includeOnlyActive) {
		List<PickListValue> values = pickListValueRepository.searchPickListByName(pickListName, searchValue);
		if (includeOnlyActive) {
			for (Iterator<PickListValue> itr = values.iterator(); itr.hasNext();) {
				PickListValue value = itr.next();
				if (!value.isActive()) {
					itr.remove();
				}
			}
		}
		return values;
	}

	@Override
	public PickListValue getDefaultPickListValue(String pickListName) {
		return pickListValueRepository.findDefaultPickListValue(pickListName);
	}
	
	@Override
	public PickListValue getPickListValueByValue(String pickListValueValue, String pickListName) {
		return pickListValueRepository.findByPickListNameAndValue(pickListName, pickListValueValue, DEFAULT_PLV_SORT);
	}
	
	@Override
	public PickListValueRelationship savePickListValueRelationship(PickListValueRelationship relationship) {
		return pickListValueRelationshipDao.save(relationship);
	}
	
	@Override
	public void deletePickListValueRelationship(PickListValueRelationship relationship) {
		pickListValueRelationshipDao.delete(relationship);
	}
	
	@Override
	public List<PickListValueRelationship> getAllChildPickListValuesInRelationship(String relationshipType) {
		return pickListValueRelationshipDao.getAllChildPickListValuesInRelationship(relationshipType);
	}
	
	@Override
	public List<KeyValuePair> getChildValuesForPickListValueAsKeyValuePairs(Long pickListValueId, String relationshipType, boolean includeOnlyActive) {
		return pickListValueRelationshipDao.getChildValuesForPickListValueAsKeyValuePairs(pickListValueId, relationshipType, includeOnlyActive);
	}
	
	@Override
	public PickListExtensionMetadata getPickListExtensionMetadataForPickList(Long pickListId) throws Exception {
		ApplicationProperty prop = applicationService.findApplicationPropertyByKey("plv.extensionmetadata-" + pickListId);
		if (prop != null) {
			JSONString json = new JSONString(prop.getValue());
			
			PickListExtensionMetadata metadata = new PickListExtensionMetadata();
			metadata.setPickListValueClass(Class.forName((String) json.getValue("model")));
			
			JSONArray jAttribs = (JSONArray) json.getValue("attributes");
			for (int i = 0; i < jAttribs.length(); i++) {
				JSONObject jField = jAttribs.getJSONObject(i);
				PickListValueExtensionField field = new PickListValueExtensionField();
				field.setName(jField.getString("name"));
				field.setLabel(jField.getString("label"));
				field.setInputType(PickListValueExtensionFieldType.valueOf(jField.getString("inputType").toUpperCase()));
				field.setTypeClass(Class.forName(jField.getString("typeClass")));
				if (field.getTypeClass().isEnum()) {
					List<KeyValuePair> enumList =  new ArrayList<KeyValuePair>();
					Object[] enums = field.getTypeClass().getEnumConstants();
					Method method = field.getTypeClass().getMethod("name");
					for (Object e : enums) {
						String name = (String) method.invoke(e);
						KeyValuePair kvp = new KeyValuePair(name, name);
						enumList.add(kvp);
					}
					field.setList(enumList);
				} else if (field.getTypeClass().isAssignableFrom(PickListValue.class)) {
					List<KeyValuePair> valuesKvp = new ArrayList<KeyValuePair>();
					List<PickListValue> valuesPlv = getValuesForPickList(field.getInputSource(), true);
					for (PickListValue plv : valuesPlv) {
						KeyValuePair kvp = new KeyValuePair(plv.getId(), plv.getValue());
						valuesKvp.add(kvp);
					}
					
					field.setList(valuesKvp);
				}
				
				if (jField.has("required")) {
					field.setRequired(jField.getBoolean("required"));
				}
				
				metadata.getPickListValueExtensionFields().add(field);
			}
			return metadata;
		}
		
		return null;
	}
	
	@Override
	public String getReportCodes(Long serviceCode, String columnName) {
		return pickListDao.getReportCodes(serviceCode, columnName);
	}

	@Override
	public void updateReportCodesDependencies(Long serviceCode, String programCode, String specificServiceCode, String ageGroup) {
		pickListDao.updateReportCodesDependencies(serviceCode, programCode, specificServiceCode, ageGroup);
	}

	@Override
	public void evict(Object entity) {
		pickListGroupDao.evict(entity);
	}
	
}