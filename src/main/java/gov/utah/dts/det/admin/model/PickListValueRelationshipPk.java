package gov.utah.dts.det.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Embeddable
public class PickListValueRelationshipPk implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	private PickListValue parent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PickListValue child;
	
	@Column(name = "RELATIONSHIP")
	private String type;
	
	public PickListValueRelationshipPk() {
		
	}
	
	public PickListValue getParent() {
		return parent;
	}
	
	public void setParent(PickListValue parent) {
		this.parent = parent;
	}
	
	public PickListValue getChild() {
		return child;
	}
	
	public void setChild(PickListValue child) {
		this.child = child;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		PickListValueRelationshipPk other = (PickListValueRelationshipPk) obj;
		if (child == null) {
			if (other.child != null)
				return false;
		} else if (!child.equals(other.child))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}