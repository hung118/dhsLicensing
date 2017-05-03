package gov.utah.dts.det.service.impl;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.ApplicationProperty;
import gov.utah.dts.det.repository.ApplicationPropertyRepository;
import gov.utah.dts.det.repository.PickListValueRepository;
import gov.utah.dts.det.service.ApplicationService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private ApplicationPropertyRepository applicationPropertyRepository;
	
	@Autowired
	private PickListValueRepository pickListValueRepository;
	
	@Override
	public PickListValue getPickListValueForApplicationProperty(String propertyKey) throws IllegalArgumentException, NumberFormatException {
		List<Long> ids = loadAndParseProperty(propertyKey);
		if (ids.size() > 1) {
			throw new IllegalArgumentException("There is more than one pick list value for the ApplicationProperty with the key: " + propertyKey);
		}
		PickListValue plv = null;
		if (!ids.isEmpty()) {
			plv = pickListValueRepository.findOne(ids.get(0));
		}
		return plv;
	}
	
	@Override
	public List<PickListValue> getPickListValuesForApplicationProperty(String propertyKey) throws IllegalArgumentException, NumberFormatException {
		List<Long> ids = loadAndParseProperty(propertyKey);
		List<PickListValue> plvs = new ArrayList<PickListValue>();
		if (!ids.isEmpty()) {
			for (Long id : ids) {
				plvs.add(pickListValueRepository.findOne(id));
			}
		}
		
		return plvs;
	}
	
	protected List<Long> loadAndParseProperty(String propertyKey) throws NumberFormatException {
		if (propertyKey == null) {
			throw new IllegalArgumentException("Property key must not be null");
		}
		ApplicationProperty prop = applicationPropertyRepository.findOne(propertyKey);
		if (prop == null) {
			throw new IllegalArgumentException("There is no ApplicationProperty with the key: " + propertyKey);
		}
		List<Long> ids = new ArrayList<Long>();
		if (StringUtils.isNotBlank(prop.getValue())) {
			String[] idStrs = prop.getValue().split(",");
			for (String idStr : idStrs) {
				ids.add(new Long(idStr));
			}
		}
		return ids;
	}
	
	@Override
	public boolean propertyContainsPickListValue(PickListValue value, String propertyKey) throws IllegalArgumentException, NumberFormatException {
		if (value == null) {
			throw new IllegalArgumentException("Value is required.");
		}
		if (propertyKey == null) {
			throw new IllegalArgumentException("Property key is required.");
		}
		ApplicationProperty prop = applicationPropertyRepository.findOne(propertyKey);
		if (prop == null) {
			throw new IllegalArgumentException("There is no application property with the given key: " + propertyKey);
		}
		boolean contains = false;
		if (StringUtils.isNotEmpty(prop.getValue())) {
			String valuesStr = prop.getValue();
			String[] values = valuesStr.split(",");
			for (String val : values) {
				if (val.equals(value.getId().toString())) {
					contains = true;
					break;
				}
			}
		}
		return contains;
	}
	
	@Override
	public ApplicationProperty findApplicationPropertyByKey(String propertyKey) {
		return applicationPropertyRepository.findOne(propertyKey);
	}
	
	@Override
	public ApplicationProperty saveApplicationProperty(ApplicationProperty property) {
		return applicationPropertyRepository.save(property);
	}
	
	@Override
	public String getApplicationPropertyValue(String propertyKey) {
		if (propertyKey == null) {
			throw new IllegalArgumentException("Property key must not be null");
		}
		String value = null;
		ApplicationProperty prop = applicationPropertyRepository.findOne(propertyKey);
		if (prop != null) {
			value = prop.getValue();
		}
		return value;
	}
}