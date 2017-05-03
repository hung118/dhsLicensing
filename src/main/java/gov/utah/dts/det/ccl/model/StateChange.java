package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.struts2.result.PersonSerializer;
import gov.utah.dts.det.model.AbstractBaseEntity;
import gov.utah.dts.det.util.CompareUtils;
import gov.utah.dts.det.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("serial")
@Entity
@Table(name = "STATE_CHANGE")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StateChange extends AbstractBaseEntity<Long> implements Serializable, Comparable<StateChange> {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "STATE_CHANGE_SEQ")
	@SequenceGenerator(name = "STATE_CHANGE_SEQ", sequenceName = "STATE_CHANGE_SEQ")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "STATE_OBJECT_ID")
	private StateObject stateObject;
	
	@Column(name = "CHANGE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date changeDate;
	
	@Column(name = "CHANGE_TYPE")
	@Enumerated(EnumType.STRING)
	private String changeType;
	
	@ManyToOne
	@JoinColumn(name = "CHANGED_BY_ID")
	private Person changedBy;
	
	@Column(name = "NOTE")
	private String note;
	
	public StateChange() {
		
	}
	
	@JsonIgnore
	@Override
	public Long getPk() {
		return id;
	}
	
	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}
	
	@JsonIgnore
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonIgnore
	public StateObject getStateObject() {
		return stateObject;
	}
	
	public void setStateObject(StateObject stateObject) {
		this.stateObject = stateObject;
	}
	
	public Date getChangeDate() {
		return changeDate;
	}
	
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	
	public String getChangeType() {
		return changeType;
	}
	
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	@JsonSerialize(using = PersonSerializer.class)
	public Person getChangedBy() {
		return changedBy;
	}
	
	public void setChangedBy(Person changedBy) {
		this.changedBy = changedBy;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public int compareTo(StateChange o) {
		if (this == o) {
			return 0;
		}
		
		int comp = CompareUtils.nullSafeComparableCompare(getChangeDate(), o.getChangeDate(), false);
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getChangedBy(), o.getChangedBy(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getChangeType(), o.getChangeType(), false);
		}
		if (comp == 0) {
			comp = CompareUtils.nullSafeComparableCompare(getId(), o.getId(), false);
		}
		
		return comp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getStateObject() == null) ? 0 : (getStateObject().getId() == null ? getStateObject().hashCode() : getStateObject().getId().hashCode()));
		result = prime * result + ((getChangeDate() == null) ? 0 : getChangeDate().hashCode());
		result = prime * result + ((getChangeType() == null) ? 0 : getChangeType().hashCode());
		result = prime * result + ((getChangedBy() == null) ? 0 : getChangedBy().hashCode());
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
		if (!(obj instanceof StateChange)) {
			return false;
		}
		StateChange other = (StateChange) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		if (getStateObject() == null) {
			if (other.getStateObject() != null) {
				return false;
			}
		} else if (!getStateObject().equals(other.getStateObject())) {
			return false;
		}
		if (getChangeDate() == null) {
			if (other.getChangeDate() != null) {
				return false;
			}
		} else if (!getChangeDate().equals(other.getChangeDate())) {
			return false;
		}
		if (getChangeType() == null) {
			if (other.getChangeType() != null) {
				return false;
			}
		} else if (!getChangeType().equals(other.getChangeType())) {
			return false;
		}
		if (getChangedBy() == null) {
			if (other.getChangedBy() != null) {
				return false;
			}
		} else if (!getChangedBy().equals(other.getChangedBy())) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Status change on " + getStateObject().getId() + " - " + DateUtils.formatDateDefault(getChangeDate()) + " " + getChangeType() + " by " + getChangedBy().getFirstAndLastName() + (StringUtils.isNotBlank(getNote()) ? " " + getNote() : "");
	}
}