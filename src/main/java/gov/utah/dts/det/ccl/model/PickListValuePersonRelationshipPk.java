package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.PickListValuePersonRelationshipType;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Embeddable
public class PickListValuePersonRelationshipPk implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	private PickListValue pickListValue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Person person;
	
	@Column(name = "RELATIONSHIP")
	@Type(type = "PickListValuePersonRelationshipType")
	private PickListValuePersonRelationshipType type;
	
	public PickListValuePersonRelationshipPk() {
		
	}

	public PickListValue getPickListValue() {
		return pickListValue;
	}

	public void setPickListValue(PickListValue pickListValue) {
		this.pickListValue = pickListValue;
	}

	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}

	public PickListValuePersonRelationshipType getType() {
		return type;
	}

	public void setType(PickListValuePersonRelationshipType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pickListValue == null) ? 0 : pickListValue.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
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
		PickListValuePersonRelationshipPk other = (PickListValuePersonRelationshipPk) obj;
		if (pickListValue == null) {
			if (other.pickListValue != null)
				return false;
		} else if (!pickListValue.equals(other.pickListValue))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		return true;
	}
}