package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "INSPECTION_TYPES_VIEW")
@SuppressWarnings("serial")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class InspectionTypesView implements Serializable {

	@Id
	@Column(name = "INSPECTION_ID")
	private Long inspectionId;
	
	@Column(name = "PRIMARY_TYPE")
	private String primaryType;
	
	@Column(name = "OTHER_TYPES")
	private String otherTypes;
	
	public InspectionTypesView() {
		
	}
	
	public Long getInspectionId() {
		return inspectionId;
	}
	
	public void setInspectionId(Long inspectionId) {
		this.inspectionId = inspectionId;
	}
	
	public String getPrimaryType() {
		return primaryType;
	}
	
	public void setPrimaryType(String primaryType) {
		this.primaryType = primaryType;
	}
	
	public String getOtherTypes() {
		return otherTypes;
	}
	
	public void setOtherTypes(String otherTypes) {
		this.otherTypes = otherTypes;
	}
}