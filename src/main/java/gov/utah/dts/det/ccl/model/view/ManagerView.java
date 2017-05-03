package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "SECURITY_VIEW")
@Immutable
public class ManagerView implements Serializable {

	@EmbeddedId
	private ManagerViewPk primaryKey = new ManagerViewPk();
	
	public ManagerView() {
		
	}
	
	@Transient
	public Long getPersonId() {
		return primaryKey.getPersonId();
	}

	public void setPersonId(Long personId) {
		primaryKey.setPersonId(personId);
	}

	@Transient
	public Long getManagerId() {
		return primaryKey.getManagerId();
	}

	public void setManagerId(Long managerId) {
		primaryKey.setManagerId(managerId);
	}
	
	@Transient
	public String getRelationship() {
		return primaryKey.getRelationship();
	}
	
	public void setRelationship(String relationship) {
		primaryKey.setRelationship(relationship);
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
		ManagerView other = (ManagerView) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		return true;
	}
}