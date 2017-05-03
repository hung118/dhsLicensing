package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.FindingCategoryType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "PLVFINDINGCATEGORY")
public class FindingCategoryPickListValue extends PickListValue {
	
	@Column(name = "TYPE")
	@Type(type = "FindingCategoryType")
	private FindingCategoryType type;
	
	public FindingCategoryPickListValue() {
		
	}
	
	@JsonIgnore
	public FindingCategoryType getType() {
		return type;
	}
	
	public void setType(FindingCategoryType type) {
		this.type = type;
	}
	
	public boolean isDisplayCmp() {
		return type.isDisplayCmp();
	}
	
	public boolean isDisplayWarning() {
		return type.isDisplayWarning();
	}
}