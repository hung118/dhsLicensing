package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Person;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT_WORK_IN_PROGRESS_VIEW")
@Immutable
public class WorkInProgressView implements Serializable {

	@EmbeddedId
	private WorkInProgressViewPk primaryKey = new WorkInProgressViewPk();

	@ManyToOne
	@JoinColumn(name = "FACILITY_ID")
	private BasicFacilityInformation facility;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private Person owner;
	
	@Column(name = "OBJECT_DATE")
	@Temporal(TemporalType.DATE)
	private Date objectDate;
	
	@Embedded
	@AttributeOverride(name = "jsonString", column = @Column(name = "METADATA"))
	private JSONString metadata;
	
	public WorkInProgressView() {
		
	}
	
	public WorkInProgressViewPk getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(WorkInProgressViewPk primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public BasicFacilityInformation getFacility() {
		return facility;
	}
	
	public void setFacility(BasicFacilityInformation facility) {
		this.facility = facility;
	}
	
	public Person getOwner() {
		return owner;
	}
	
	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Long getObjectId() {
		return primaryKey.getObjectId();
	}

	public void setObjectId(Long objectId) {
		primaryKey.setObjectId(objectId);
	}

	public String getObjectType() {
		return primaryKey.getObjectType();
	}

	public void setObjectType(String objectType) {
		primaryKey.setObjectType(objectType);
	}

	public Date getObjectDate() {
		return objectDate;
	}

	public void setObjectDate(Date objectDate) {
		this.objectDate = objectDate;
	}
	
	public JSONString getMetadata() {
		return metadata;
	}
	
	public void setMetadata(JSONString metadata) {
		this.metadata = metadata;
	}
}