package gov.utah.dts.det.ccl.model.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class WorkInProgressViewPk implements Serializable {

	@Column(name = "OBJECT_ID")
	private Long objectId;
	
	@Column(name = "OBJECT_TYPE")
	private String objectType;
	
	public WorkInProgressViewPk() {
		
	}
	
	public Long getObjectId() {
		return objectId;
	}
	
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	public String getObjectType() {
		return objectType;
	}
	
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
		result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		WorkInProgressViewPk other = (WorkInProgressViewPk) obj;
		if (objectId == null) {
			if (other.objectId != null) {
				return false;
			}
		} else if (!objectId.equals(other.objectId)) {
			return false;
		}
		if (objectType == null) {
			if (other.objectType != null) {
				return false;
			}
		} else if (!objectType.equals(other.objectType)) {
			return false;
		}
		return true;
	}
}