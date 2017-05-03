package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@SuppressWarnings("serial")
@Entity
@Table(name = "STATE_OBJECT")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class StateObject extends AbstractAuditableEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "STATE_OBJECT_SEQ")
	@SequenceGenerator(name = "STATE_OBJECT_SEQ", sequenceName = "STATE_OBJECT_SEQ")
	private Long id;	
	
	@Column(name = "STATE")
	private String internalState;
	
	@OneToMany(mappedBy = "stateObject", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	@Sort(type = SortType.NATURAL)
	private SortedSet<StateChange> stateChanges = new TreeSet<StateChange>();
	
	public StateObject() {
		
	}
	
	@Override
	public Long getPk() {
		return id;
	}
	
	@Override
	public void setPk(Long pk) {
		this.id = pk;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public SortedSet<StateChange> getStateChanges() {
		return Collections.unmodifiableSortedSet(stateChanges);
	}
	
	protected String getInternalState() {
		return internalState;
	}
	
	protected void setState(String state, String changeType, String note) {
		this.internalState = state;
		
		StateChange change = new StateChange();
		change.setStateObject(this);
		change.setChangeDate(new Date());
		change.setChangeType(changeType);
		change.setChangedBy(SecurityUtil.getUser().getPerson());
		change.setNote(note);
		
		stateChanges.add(change);
	}
}