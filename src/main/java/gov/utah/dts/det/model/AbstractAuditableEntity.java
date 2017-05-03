package gov.utah.dts.det.model;

import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.security.SecurityUtil;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractAuditableEntity<PK extends Serializable> extends AbstractBaseEntity<PK> implements Serializable, OwnedEntity {

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "CREATED_BY_ID")
	private Long createdById;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY_ID", insertable = false, updatable = false)
	private Person createdBy;
	
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Column(name = "MODIFIED_BY_ID")
	private Long modifiedById;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODIFIED_BY_ID", insertable = false, updatable = false)
	private Person modifiedBy;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Long getCreatedById() {
		return createdById;
	}
	
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
	
	public Person getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public Long getModifiedById() {
		return modifiedById;
	}
	
	public void setModifiedById(Long modifiedById) {
		this.modifiedById = modifiedById;
	}

	public Person getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Person modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public boolean isOwner() {
		return SecurityUtil.isEntityOwner(this);
	}
}