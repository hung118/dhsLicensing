package gov.utah.dts.det.admin.model.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "PICK_LIST_VALUE_VIEW")
@SuppressWarnings("serial")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PickListValueView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PICK_LIST_ID")
	private Long pickListId;
	
	@Column(name = "PICK_LIST_NAME")
	private String pickListName;
	
	@Column(name = "PLV_VALUE")
	private String value;
	
	@Column(name = "DEFAULT_VALUE")
	@Type(type = "yes_no")
	private boolean defaultValue;
	
	@Column(name = "SORT_ORDER")
	private Double sortOrder;
	
	@Column(name = "IS_ACTIVE")
	@Type(type = "yes_no")
	private boolean active;
	
	public PickListValueView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPickListId() {
		return pickListId;
	}

	public void setPickListId(Long pickListId) {
		this.pickListId = pickListId;
	}

	public String getPickListName() {
		return pickListName;
	}

	public void setPickListName(String pickListName) {
		this.pickListName = pickListName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Double getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}