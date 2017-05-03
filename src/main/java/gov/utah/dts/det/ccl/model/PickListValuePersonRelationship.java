package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.PickListValuePersonRelationshipType;
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
@Table(name = "PLV_TO_PERSON")
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.pickListValue", joinColumns = @JoinColumn(name = "PLVID")),
	@AssociationOverride(name = "primaryKey.person", joinColumns = @JoinColumn(name = "PERSONID"))
})
@SuppressWarnings("serial")
public class PickListValuePersonRelationship extends AbstractBaseEntity<PickListValuePersonRelationshipPk> implements Serializable {
	
	@EmbeddedId
	private PickListValuePersonRelationshipPk primaryKey = new PickListValuePersonRelationshipPk();
	
	public PickListValuePersonRelationship() {
		
	}
	
	@Override
	public PickListValuePersonRelationshipPk getPk() {
		return primaryKey;
	}
	
	@Override
	public void setPk(PickListValuePersonRelationshipPk pk) {
		this.primaryKey = pk;
	}
	
	public PickListValuePersonRelationshipPk getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(PickListValuePersonRelationshipPk primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@Transient
	public PickListValue getPickListValue() {
		return primaryKey.getPickListValue();
	}
	
	public void setPickListValue(PickListValue pickListValue) {
		primaryKey.setPickListValue(pickListValue);
	}
	
	@Transient
	public Person getPerson() {
		return primaryKey.getPerson();
	}
	
	public void setPerson(Person person) {
		primaryKey.setPerson(person);
	}
	
	@Transient
	public PickListValuePersonRelationshipType getType() {
		return primaryKey.getType();
	}
	
	public void setType(PickListValuePersonRelationshipType type) {
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
		PickListValuePersonRelationship other = (PickListValuePersonRelationship) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		return true;
	}
}