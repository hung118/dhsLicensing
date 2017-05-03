package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.model.enums.SpecialistType;

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
@Table(name = "INSPECTION_SPECIALIST")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AssociationOverrides({
	@AssociationOverride(name = "primaryKey.inspection", joinColumns = @JoinColumn(name = "INSPECTION_ID")),
	@AssociationOverride(name = "primaryKey.specialist", joinColumns = @JoinColumn(name = "SPECIALIST_ID"))
})
@SuppressWarnings("serial")
public class InspectionSpecialist implements Serializable {

	@EmbeddedId
	private InspectionSpecialistPk primaryKey = new InspectionSpecialistPk();
	
	@Column(name = "TYPE", nullable = false)
	@Type(type = "SpecialistType")
	private SpecialistType specialistType;
	
	public InspectionSpecialist() {
		
	}
	
	public InspectionSpecialist(Inspection inspection, Person specialist, SpecialistType specialistType) {
		setInspection(inspection);
		setSpecialist(specialist);
		setSpecialistType(specialistType);
	}
	
	public InspectionSpecialistPk getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(InspectionSpecialistPk primaryKey) {
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
	public Person getSpecialist() {
		return primaryKey.getSpecialist();
	}
	
	public void setSpecialist(Person specialist) {
		primaryKey.setSpecialist(specialist);
	}
	
	public SpecialistType getSpecialistType() {
		return specialistType;
	}
	
	public void setSpecialistType(SpecialistType specialistType) {
		this.specialistType = specialistType;
	}
	
	@Override
	public String toString() {
		return "Inspection Specialist: " + getSpecialist() + " Type: " + getSpecialistType();
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
		if (!(obj instanceof InspectionSpecialist)) {
			return false;
		}
		InspectionSpecialist other = (InspectionSpecialist) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null) {
				return false;
			}
		} else if (!primaryKey.equals(other.primaryKey)) {
			return false;
		}
		if (specialistType == null) {
			if (other.specialistType != null) {
				return false;
			}
		} else if (!specialistType.equals(other.specialistType)) {
			return false;
		}
		return true;
	}
}