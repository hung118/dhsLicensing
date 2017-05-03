package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.enums.FacilityEventType;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "FACILITY_HISTORY_VIEW")
@SuppressWarnings("serial")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class FacilityEventView implements Serializable {
	
	@EmbeddedId
	private FacilityEventViewPk primaryKey = new FacilityEventViewPk();
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "EVENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date eventDate;
	
	@Column(name = "EVENT")
	private String event;
	
	public FacilityEventView() {
		
	}
	
	@Transient
	public Long getEventId() {
		return primaryKey.getEventId();
	}
	
	public void setEventId(Long eventId) {
		primaryKey.setEventId(eventId);
	}
	
	@Transient
	public FacilityEventType getEventType() {
		return primaryKey.getEventType();
	}
	
	public void setEventType(FacilityEventType eventType) {
		primaryKey.setEventType(eventType);
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((facilityId == null) ? 0 : facilityId.hashCode());
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
		FacilityEventView other = (FacilityEventView) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (facilityId == null) {
			if (other.facilityId != null)
				return false;
		} else if (!facilityId.equals(other.facilityId))
			return false;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		return true;
	}
}