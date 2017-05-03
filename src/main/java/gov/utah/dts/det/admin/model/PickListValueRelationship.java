package gov.utah.dts.det.admin.model;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PLVTOPLV")
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.parent", joinColumns = @JoinColumn(name = "PARENTPLVID")),
	@AssociationOverride(name = "primaryKey.child", joinColumns = @JoinColumn(name = "CHILDPLVID"))
})
@SuppressWarnings("serial")
public class PickListValueRelationship extends AbstractBaseEntity<PickListValueRelationshipPk> implements Serializable {
	
	@EmbeddedId
	private PickListValueRelationshipPk primaryKey = new PickListValueRelationshipPk();
	
	public PickListValueRelationship() {
		
	}
	
	@Override
	public PickListValueRelationshipPk getPk() {
		return primaryKey;
	}
	
	@Override
	public void setPk(PickListValueRelationshipPk pk) {
		this.primaryKey = pk;
	}
	
	public PickListValueRelationshipPk getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(PickListValueRelationshipPk primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@Transient
	public PickListValue getParent() {
		return primaryKey.getParent();
	}
	
	public void setParent(PickListValue parent) {
		primaryKey.setParent(parent);
	}
	
	@Transient
	public PickListValue getChild() {
		return primaryKey.getChild();
	}
	
	public void setChild(PickListValue child) {
		primaryKey.setChild(child);
	}
	
	@Transient
	public String getType() {
		return primaryKey.getType();
	}
	
	public void setType(String type) {
		primaryKey.setType(type);
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PickListValueRelationship other = (PickListValueRelationship) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		return true;
	}
}