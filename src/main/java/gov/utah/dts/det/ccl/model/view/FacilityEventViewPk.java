package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.enums.FacilityEventType;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Embeddable
public class FacilityEventViewPk implements Serializable {
	
	@Column(name = "EVENT_ID")
	private Long eventId;
	
	@Column(name = "EVENT_TYPE")
	@Type(type = "FacilityEventType")
	private FacilityEventType eventType;
	
	public FacilityEventViewPk() {
		
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public FacilityEventType getEventType() {
		return eventType;
	}

	public void setEventType(FacilityEventType eventType) {
		this.eventType = eventType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
		result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
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
		FacilityEventViewPk other = (FacilityEventViewPk) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		if (eventType == null) {
			if (other.eventType != null)
				return false;
		} else if (!eventType.equals(other.eventType))
			return false;
		return true;
	}
}