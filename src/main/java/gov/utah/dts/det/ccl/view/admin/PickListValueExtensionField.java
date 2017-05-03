package gov.utah.dts.det.ccl.view.admin;

import gov.utah.dts.det.admin.view.KeyValuePair;

import java.util.List;

@SuppressWarnings("unchecked")
public class PickListValueExtensionField {

	private String name;
	private String label;
	private PickListValueExtensionFieldType inputType;
	private String inputSource;
	private Class typeClass;
	private boolean required = false;
	private List<KeyValuePair> list;

	private String value;
	
	public PickListValueExtensionField() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public PickListValueExtensionFieldType getInputType() {
		return inputType;
	}
	
	public void setInputType(PickListValueExtensionFieldType inputType) {
		this.inputType = inputType;
	}
	
	public String getInputSource() {
		return inputSource;
	}
	
	public void setInputSource(String inputSource) {
		this.inputSource = inputSource;
	}
	
	public Class getTypeClass() {
		return typeClass;
	}
	
	public void setTypeClass(Class typeClass) {
		this.typeClass = typeClass;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public void setRequired(boolean required) {
		this.required = required;
	}

	public List<KeyValuePair> getList() {
		return list;
	}

	public void setList(List<KeyValuePair> list) {
		this.list = list;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}