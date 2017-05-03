package gov.utah.dts.det.ccl.view.admin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class PickListExtensionMetadata {

	private Class pickListValueClass;
	private List<PickListValueExtensionField> pickListValueExtensionFields = new ArrayList<PickListValueExtensionField>();
	
	public PickListExtensionMetadata() {
		
	}
	
	public Class getPickListValueClass() {
		return pickListValueClass;
	}
	
	public void setPickListValueClass(Class pickListValueClass) {
		this.pickListValueClass = pickListValueClass;
	}
	
	public List<PickListValueExtensionField> getPickListValueExtensionFields() {
		return pickListValueExtensionFields;
	}
	
	public void setPickListValueExtensionFields(List<PickListValueExtensionField> pickListValueExtensionFields) {
		this.pickListValueExtensionFields = pickListValueExtensionFields;
	}
}