package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.User;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "PEOPLE_BY_REGION_VIEW")
@Immutable
public class UserByRegionView implements Serializable {

	@EmbeddedId
	private UserByRegionViewPk primaryKey = new UserByRegionViewPk();
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "REGION_ID")
	private PickListValue region;
	
	@Column(name = "HIGH_PRIORITY")
	@Type(type = "yes_no")
	private boolean highPriority;
	
	public UserByRegionView() {
		
	}
	
	@Transient
	public Long getPersonId() {
		return primaryKey.getPersonId();
	}

	public void setPersonId(Long personId) {
		primaryKey.setPersonId(personId);
	}
	
	@Transient
	public String getRelationship() {
		return primaryKey.getRelationship();
	}
	
	public void setRelationship(String relationship) {
		primaryKey.setRelationship(relationship);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PickListValue getRegion() {
		return region;
	}

	public void setRegion(PickListValue region) {
		this.region = region;
	}

	public boolean isHighPriority() {
		return highPriority;
	}

	public void setHighPriority(boolean highPriority) {
		this.highPriority = highPriority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (highPriority ? 1231 : 1237);
		result = prime * result + ((primaryKey == null) ? 0 : primaryKey.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserByRegionView other = (UserByRegionView) obj;
		if (highPriority != other.highPriority)
			return false;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}