package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings("serial")
@Embeddable
public class InspectionTypePk implements Serializable {
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Inspection inspection;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private PickListValue inspectionType;
	
	public InspectionTypePk() {
		
	}
	
	public Inspection getInspection() {
		return inspection;
	}
	
	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}
	
	public PickListValue getInspectionType() {
		return inspectionType;
	}
	
	public void setInspectionType(PickListValue inspectionType) {
		this.inspectionType = inspectionType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inspection == null) ? 0 : inspection.hashCode());
		result = prime * result + ((inspectionType == null) ? 0 : inspectionType.hashCode());
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
		InspectionTypePk other = (InspectionTypePk) obj;
		if (inspection == null) {
			if (other.inspection != null)
				return false;
		} else if (!inspection.equals(other.inspection))
			return false;
		if (inspectionType == null) {
			if (other.inspectionType != null)
				return false;
		} else if (!inspectionType.equals(other.inspectionType))
			return false;
		return true;
	}
}