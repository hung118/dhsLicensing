package gov.utah.dts.det.ccl.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings("serial")
@Embeddable
public class InspectionSpecialistPk implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	private Inspection inspection;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private Person specialist;
	
	public InspectionSpecialistPk() {
		
	}
	
	public Inspection getInspection() {
		return inspection;
	}
	
	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}
	
	public Person getSpecialist() {
		return specialist;
	}
	
	public void setSpecialist(Person specialist) {
		this.specialist = specialist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inspection == null) ? 0 : inspection.hashCode());
		result = prime * result + ((specialist == null) ? 0 : specialist.hashCode());
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
		InspectionSpecialistPk other = (InspectionSpecialistPk) obj;
		if (inspection == null) {
			if (other.inspection != null)
				return false;
		} else if (!inspection.equals(other.inspection))
			return false;
		if (specialist == null) {
			if (other.specialist != null)
				return false;
		} else if (!specialist.equals(other.specialist))
			return false;
		return true;
	}
}