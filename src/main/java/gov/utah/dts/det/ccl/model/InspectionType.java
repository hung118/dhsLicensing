package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "INSPECTION_TYPE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.inspection", joinColumns = @JoinColumn(name = "INSPECTION_ID")),
	@AssociationOverride(name = "primaryKey.inspectionType", joinColumns = @JoinColumn(name = "INSPECTION_TYPE_ID"))
})
@SuppressWarnings("serial")
public class InspectionType implements Serializable {

	@EmbeddedId
	private InspectionTypePk primaryKey = new InspectionTypePk();
	
	@Column(name = "IS_PRIMARY")
	@Type(type = "yes_no")
	private boolean primary = false;
	
	public InspectionType() {
		
	}
	
	public InspectionType(Inspection inspection, PickListValue inspectionType, boolean primary) {
		setInspection(inspection);
		setInspectionType(inspectionType);
		setPrimary(primary);
	}
	
	public InspectionTypePk getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(InspectionTypePk primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@Transient
	public Inspection getInspection() {
		return primaryKey.getInspection();
	}
	
	public void setInspection(Inspection inspection) {
		primaryKey.setInspection(inspection);
	}
	
	@Transient
	public PickListValue getInspectionType() {
		return primaryKey.getInspectionType();
	}
	
	public void setInspectionType(PickListValue inspectionType) {
		primaryKey.setInspectionType(inspectionType);
	}
	
	public boolean isPrimary() {
		return primary;
	}
	
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	@Override
	public String toString() {
		return "Inspection Type: " + getInspectionType() + (isPrimary() ? "Primary" : "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof InspectionType)) {
			return false;
		}
		InspectionType other = (InspectionType) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null) {
				return false;
			}
		} else if (!primaryKey.equals(other.primaryKey)) {
			return false;
		}
		if (primary != other.primary) {
			return false;
		}
		return true;
	}
	
	
}