package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "NONCOMPLIANCEPOINTS")
public class NoncompliancePoints extends AbstractBaseEntity<NoncompliancePointsPk> implements Serializable {

	@EmbeddedId
	private NoncompliancePointsPk primaryKey = new NoncompliancePointsPk();
	
	@Column(name = "POINTS", nullable = false)
	private Integer points;
	
	public NoncompliancePoints() {
		
	}
	
	@Override
	public NoncompliancePointsPk getPk() {
		return primaryKey;
	}
	
	@Override
	public void setPk(NoncompliancePointsPk pk) {
		this.primaryKey = pk;
	}
	
	public NoncompliancePointsPk getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(NoncompliancePointsPk primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@Transient
	public Long getFindingsCategoryId() {
		return primaryKey.getFindingsCategoryId();
	}
	
	public void setFindingsCategoryId(Long findingsCategoryId) {
		primaryKey.setFindingsCategoryId(findingsCategoryId);
	}
	
	@Transient
	public Long getNoncomplianceLevelId() {
		return primaryKey.getNoncomplianceLevelId();
	}
	
	public void setNoncomplianceLevelId(Long noncomplianceLevelId) {
		primaryKey.setNoncomplianceLevelId(noncomplianceLevelId);
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points = points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
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
		NoncompliancePoints other = (NoncompliancePoints) obj;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		return true;
	}
}