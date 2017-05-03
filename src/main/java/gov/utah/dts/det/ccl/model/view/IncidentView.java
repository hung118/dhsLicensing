package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Incident;
import gov.utah.dts.det.model.OwnedEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "INCIDENT_VIEW")
@Immutable
public class IncidentView implements Serializable, OwnedEntity {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "INCIDENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name = "DEATH")
	@Type(type = "yes_no")
	private Boolean death;
	
	@Column(name = "CHILD_NAME")
	private String childName;
	
	@Column(name = "CHILD_AGE")
	private String childAge;
	
	//@Column(name = "AGE")
	@Transient
	private Integer age;
	
	@Column(name = "BODY_PARTS_INJURED")
	private String bodyPartsInjured;
	
	@Column(name = "STATE")
	@Enumerated(EnumType.STRING)
	private Incident.State state;
	
	@Column(name = "CREATED_BY_ID")
	private Long createdById;
	
	public IncidentView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getDeath() {
		return death;
	}

	public void setDeath(Boolean death) {
		this.death = death;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getChildAge() {
		return childAge;
	}

	public void setChildAge(String childAge) {
		this.childAge = childAge;
	}

	public String getBodyPartsInjured() {
		return bodyPartsInjured;
	}

	public void setBodyPartsInjured(String bodyPartsInjured) {
		this.bodyPartsInjured = bodyPartsInjured;
	}
	
	public Incident.State getState() {
		return state;
	}
	
	public void setState(Incident.State state) {
		this.state = state;
	}
	
	@Override
	public Long getCreatedById() {
		return createdById;
	}
	
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}