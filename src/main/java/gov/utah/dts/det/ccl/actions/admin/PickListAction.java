package gov.utah.dts.det.ccl.actions.admin;

import gov.utah.dts.det.admin.model.PickList;
import gov.utah.dts.det.admin.model.PickListGroup;
import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.admin.service.PickListService;
import gov.utah.dts.det.ccl.view.admin.PickListExtensionMetadata;
import gov.utah.dts.det.ccl.view.admin.PickListValueExtensionField;
import gov.utah.dts.det.ccl.view.admin.PickListValueExtensionFieldType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.conversion.impl.XWorkBasicConverter;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

@SuppressWarnings("serial")
@ParentPackage("main")
@Namespace("/admin/picklists")
@Results({
	@Result(name = "input", location = "pick_list_value_form.jsp"),
	@Result(name = "success", location = "view-pick-list-value-list", type = "redirectAction", params = {"pickList.id", "${pickList.id}"})
})
public class PickListAction extends ActionSupport implements Preparable, ParameterAware, Validateable {

	private Map<String, String[]> parameterMap;
	
	private PickListService pickListService;
	
	private Long pickListGroupId;
	private PickList pickList;
	private PickListValue pickListValue;
	
	private PickListExtensionMetadata metadata;
	private List<PickListGroup> pickListGroups;
	private List<PickList> pickLists;
	
	private List<PickListValue> serviceCodes;
	private List<PickListValue> programCodes;
	private List<PickListValue> specificServiceCodes;
	private List<PickListValue> ageGroups;
	private Long serviceCode;
	private Long[] programCode;
	private Long[] specificServiceCode;
	private Long[] ageGroup;
	private Map<String, Object> response;
	
	@Override
	public void setParameters(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void prepare() throws Exception {
		if (pickList != null && pickList.getId() != null && pickList.getId().longValue() != -1l) {
			pickList = pickListService.loadPickListById(pickList.getId());
			metadata = pickListService.getPickListExtensionMetadataForPickList(pickList.getId());
			
			if (pickListValue != null && pickListValue.getId() != null) {
				for (PickListValue value : pickList.getPickListValues()) {
					if (value.getId().longValue() == this.pickListValue.getId().longValue()) {
						this.pickListValue = value;
						break;
					}
				}
			}
			
			//set the current extension values so they can be displayed to the user
			if (metadata != null && pickListValue != null && metadata.getPickListValueClass().isInstance(pickListValue)) {
				for (PickListValueExtensionField field : metadata.getPickListValueExtensionFields()) {
					//build the getter method by uppercasing the first letter of the field name
					StringBuilder getter = new StringBuilder("get");
					getter.append(field.getName().substring(0, 1).toUpperCase());
					getter.append(field.getName().substring(1));
					Method getterMethod = null;
					try {
						getterMethod = metadata.getPickListValueClass().getMethod(getter.toString());
						Object value = getterMethod.invoke(pickListValue);
						field.setValue(value == null ? null : value.toString());
					} catch (NoSuchMethodException nsme) {
						//swallow this exception - the pick list should be extended but has no extended attributes set yet.
					}
				}
			}
		}
	}

	@SkipValidation
	@Action(value = "tab", results = {
		@Result(name = "success", location = "pick_list_tab.jsp")
	})
	public String doTab() {
		pickListGroups = pickListService.getPickListGroups();
		return SUCCESS;
	}

	@SkipValidation
	@Action(value = "view-pick-list-selection", results = {
		@Result(name = "success", location = "pick_list_selection.jsp")
	})
	public String doViewPickListSelection() {
		if (pickListGroupId == null || pickListGroupId.longValue() == -1l) {
			return null;
		}
		
		pickLists = pickListService.getPickLists(pickListGroupId);
		return SUCCESS;
	}

	@SkipValidation
	@Action(value = "view-pick-list-value-list", results = {
		@Result(name = "success", location = "pick_list_value_list.jsp"),
		@Result(name = "reportCode", location = "pick_list_report_code.jsp")
	})
	public String doViewPickListValueList() {
		if (pickList == null || pickList.getId().longValue() == -1l) {
			return null;
		} 
		
		if (pickList != null && pickList.getId().longValue() == 374l) {	// report codes dependencies
			return "reportCode";
		}
		
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "edit-pick-list-value")
	public String doEditPickListValue() {
		return INPUT;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "save-pick-list-value")
	public String doSavePickListValue() throws Exception {
		if (pickListValue.getPickList() == null) {
			if (metadata != null && !metadata.getPickListValueClass().isInstance(pickListValue)) {
				//need to make the pick list value an extended pick list value
				PickListValue tempPlv = pickListValue;
				pickListValue = (PickListValue) metadata.getPickListValueClass().newInstance();
				pickListValue.setValue(tempPlv.getValue());
				pickListValue.setSortOrder(tempPlv.getSortOrder());
			}
			
			pickListValue.setActive(true);
			pickListValue.setPickList(pickList);
			pickList.getPickListValues().add(pickListValue);
		}
		
		if (metadata != null) {
			//set the extended attributes
			for (PickListValueExtensionField field : metadata.getPickListValueExtensionFields()) {
				//value from the form
				String value = null;
				if (field.getInputType() == PickListValueExtensionFieldType.CHECKBOX) {
					//checkbox values will not be in the parameter map if they are unchecked
					String[] values = parameterMap.get("plv." + field.getName());
					if (values == null) {
						value = "false";
					} else {
						value = "true";
					}
				} else {
					value = parameterMap.get("plv." + field.getName())[0];
				}
			
				//find the setter method
				StringBuilder setter = new StringBuilder("set");
				setter.append(field.getName().substring(0, 1).toUpperCase());
				setter.append(field.getName().substring(1));
				
				//convert the form value to the correct type
				Object convertedValue = null;
				if (field.getTypeClass().isInstance(PickListValue.class)) {
					//convert to a pick list value
					PickListValue plv = new PickListValue();
					plv.setId(Long.parseLong(value));
					convertedValue = plv;
				} else if (field.getTypeClass().equals(Boolean.class)) {
					//convert my own booleans because struts boolean converter is lame
					convertedValue = Boolean.valueOf(value);
				} else {
					//use standard struts converters
					XWorkBasicConverter converter = new XWorkBasicConverter();
					convertedValue = converter.convertValue(value, field.getTypeClass());
				}
				try {
					Method setterMethod = metadata.getPickListValueClass().getMethod(setter.toString(), field.getTypeClass());
					setterMethod.invoke(pickListValue, convertedValue);
				} catch (NoSuchMethodException nsme) {
					//swallow this exception - the pick list should be extended but has no extended attributes set yet.
				}
			}
		}
		
		pickListService.savePickList(pickList);
		return SUCCESS;
	}
	
	/**
	 * Ajax call to get program codes, specific service codes, and age groups with specified service code.
	 * 
	 * @return
	 */
	@SkipValidation
	@Action(value = "report-codes", results = {
		@Result(name = "success", type = "json", params = {"root", "response"})
	})	
	public String doReportCodes() throws Exception {
		response = new HashMap<String, Object>();
		if (serviceCode.longValue() == -1l) {
			response.put("programCodes", new ArrayList<String>());
			response.put("specificServiceCodes", new ArrayList<String>());
			response.put("ageGroups", new ArrayList<String>());
		} else {
			List<String> pcs = getHtml(getProgramCodes(), pickListService.getReportCodes(serviceCode, "PROGRAM_CODE"));
			List<String> sscs = getHtml(getSpecificServiceCodes(), pickListService.getReportCodes(serviceCode, "SPECIFIC_SERVICE_CODE"));
			List<String> ags = getHtml(getAgeGroups(), pickListService.getReportCodes(serviceCode, "AGE_GROUP"));			
			
			response.put("programCodes", pcs);
			response.put("specificServiceCodes", sscs);
			response.put("ageGroups", ags);
		}
		
		return SUCCESS;
	}
	
	@Action(value = "save-report-code-dependencies", results = {
		@Result(name = "error", location = "pick_list_report_code.jsp")
	})
	public String saveReportCodeDependencies() {
		try {
			if (serviceCode.longValue() == -1l) {
				addFieldError("serviceCode", "Service Code is required.");
				return ERROR;
			}
			
			String programCodeStr = "";
			if (programCode != null && programCode.length > 0) {
				for (int i = 0; i < programCode.length; i++) {
					programCodeStr += programCode[i] + ",";
				}
			}
			String specificServiceCodeStr = "";
			if (specificServiceCode != null && specificServiceCode.length > 0 ) {
				for (int i = 0; i < specificServiceCode.length; i++){
					specificServiceCodeStr += specificServiceCode[i] + ",";
				}
			}
			String ageGroupStr = "";
			if (ageGroup != null && ageGroup.length > 0) {
				for (int i = 0; i < ageGroup.length; i++){
					ageGroupStr += ageGroup[i] + ",";
				}
			}
			
			pickListService.updateReportCodesDependencies(serviceCode, programCodeStr, specificServiceCodeStr, ageGroupStr);		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public Map<String, Object> getResponse() {
		return response;
	}
	
	@SkipValidation
	@Action(value = "delete-pick-list-value")
	public String doPickListDelete() {
		pickList.getPickListValues().remove(pickListValue);
		pickListService.savePickList(pickList);
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "activate-pick-list-value")
	public String doPickListActivate() {
		if (pickListValue != null) {
			pickListValue.setActive(true);
			pickListService.savePickList(pickList);
		}
		
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "deactivate-pick-list-value")
	public String doPickListDeactivate() {
		if (pickListValue != null) {
			pickListValue.setActive(false);
			pickListService.savePickList(pickList);
		}
		
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "save-pick-list-values", results = {
		@Result(name = "error", location = "pick_list_value_list.jsp")
	})
	public String doSavePickListValues() {
		for (PickListValue value : pickList.getPickListValues()) {
			String key = "order-" + value.getId();
			String[] order = (String[]) parameterMap.get(key);
			if (StringUtils.isBlank(order[0])) {
				value.setSortOrder(null);
			} else {
				try {
					value.setSortOrder(Double.parseDouble(order[0]));
				} catch (NumberFormatException nfe) {
					addFieldError(key, "The value " + order[0] + " is not a valid sort order.  Please enter a numeric value.");
				}
			}
		}
		
		if (hasFieldErrors()) {
			return ERROR;
		}
		
		pickListService.savePickList(pickList);
		return SUCCESS;
	}
	
	@SkipValidation
	@Action(value = "alphabetize-pick-list-values")
	public String doAlpahbetizePickListValues() {
		Collections.sort(pickList.getPickListValues(), new Comparator<PickListValue>() {
			public int compare(PickListValue a, PickListValue b) {
				return a.getValue().compareToIgnoreCase(b.getValue());
			}
		});
		for (int i = 0; i < pickList.getPickListValues().size(); i++) {
			PickListValue value = pickList.getPickListValues().get(i);
			value.setSortOrder(new Double(i));
		}
		
		pickListService.savePickList(pickList);
		return SUCCESS;
	}
	
	@Override
	public void validate() {
		if (metadata != null) {
			for (PickListValueExtensionField field : metadata.getPickListValueExtensionFields()) {
				if (field.isRequired()) {
					String fieldKey = "plv." + field.getName();
					String value = parameterMap.get(fieldKey)[0];
					
					if (StringUtils.isEmpty(value) || value.equals("-1")) {
						addFieldError(field.getName(), field.getLabel() + " is required.");
					}
				}
			}
		}
	}
	
	public void setPickListService(PickListService pickListService) {
		this.pickListService = pickListService;
	}
	
	public Long getPickListGroupId() {
		return pickListGroupId;
	}
	
	public void setPickListGroupId(Long pickListGroupId) {
		this.pickListGroupId = pickListGroupId;
	}
	
	public PickList getPickList() {
		return pickList;
	}
	
	public void setPickList(PickList pickList) {
		this.pickList = pickList;
	}
	
	@VisitorFieldValidator(message = "Pick List Value: ")
	public PickListValue getPickListValue() {
		return pickListValue;
	}
	
	public void setPickListValue(PickListValue pickListValue) {
		this.pickListValue = pickListValue;
	}
	
	public PickListExtensionMetadata getMetadata() {
		return metadata;
	}
	
	public List<PickListGroup> getPickListGroups() {
		return pickListGroups;
	}
	
	public List<PickList> getPickLists() {
		return pickLists;
	}
	
	public List<PickListValue> getServiceCodes() {
		if (serviceCodes == null) {
			serviceCodes = pickListService.getValuesForPickList("Service Code", true);
		}
		return serviceCodes;
	}
	
	public List<PickListValue> getProgramCodes() {
		if (programCodes == null) {
			programCodes = pickListService.getValuesForPickList("Program Code", true);
		}
		return programCodes;
	}
	
	public List<PickListValue> getSpecificServiceCodes() {
		if (specificServiceCodes == null) {
			specificServiceCodes = pickListService.getValuesForPickList("Specific Service Code", true);
		}
		return specificServiceCodes;
	}
	
	public List<PickListValue> getAgeGroups() {
		if (ageGroups == null) {
			ageGroups = pickListService.getValuesForPickList("Age Group", true);
		}
		return ageGroups;
	}

	public Long getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(Long serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Long[] getProgramCode() {
		return programCode;
	}

	public void setProgramCode(Long[] programCode) {
		this.programCode = programCode;
	}

	public Long[] getSpecificServiceCode() {
		return specificServiceCode;
	}

	public void setSpecificServiceCode(Long[] specificServiceCode) {
		this.specificServiceCode = specificServiceCode;
	}

	public Long[] getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(Long[] ageGroup) {
		this.ageGroup = ageGroup;
	}
	
	private List<String> getHtml(List<PickListValue> list, String codes) {
		List<String> retList = new ArrayList<String>();
		retList.add("<option value=\"-1\"></option> ");	// add blank
		
		for (PickListValue pkv : list) {
			StringBuffer sb = new StringBuffer("<option value=\"");
			if (codes == null) {
				sb.append(pkv.getId().toString() + "\">")
				  .append(pkv.getValue() + "</option>");								
			} else if (codes.contains(pkv.getId().toString())) {
				sb.append(pkv.getId().toString() + "\" selected=\"true\">")
				  .append(pkv.getValue() + "</option>");
			} else {
				sb.append(pkv.getId().toString() + "\">")
				  .append(pkv.getValue() + "</option>");				
			}
			
			retList.add(sb.toString());
		}
		
		return retList;
	}
	
}