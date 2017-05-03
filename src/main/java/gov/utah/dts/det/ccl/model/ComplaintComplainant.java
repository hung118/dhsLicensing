package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.model.enums.NameUsage;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT_COMPLAINANT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ComplaintComplainant implements Serializable {

	@Id
	@Column(name = "COMPLAINT_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINANT_ID")
	private Person person;

	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "COMPLAINANT_RELATIONSHIP_ID")
	@Fetch(FetchMode.JOIN)
	private PickListValue complainantRelationship;
	
	@Column(name = "NAME_USAGE")
	@Type(type = "NameUsage")
	private NameUsage nameUsage;

	@Column(name = "BEST_TIME_TO_CALL")
	private String bestTimeToCall;

	@Column(name = "ANONYMOUS_STATEMENT_READ")
	@Type(type = "yes_no")
	private Boolean anonymousStatementRead = false;

	@Column(name = "CONFIDENTIAL_STATEMENT_READ")
	@Type(type = "yes_no")
	private Boolean confidentialStatementRead = false;
	
	public ComplaintComplainant() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public PickListValue getComplainantRelationship() {
		return complainantRelationship;
	}
	
	public void setComplainantRelationship(PickListValue complainantRelationship) {
		this.complainantRelationship = complainantRelationship;
	}
	
	public NameUsage getNameUsage() {
		return nameUsage;
	}
	
	public void setNameUsage(NameUsage nameUsage) {
		this.nameUsage = nameUsage;
	}
	
	public String getBestTimeToCall() {
		return bestTimeToCall;
	}
	
	public void setBestTimeToCall(String bestTimeToCall) {
		this.bestTimeToCall = bestTimeToCall;
	}
	
	public Boolean getAnonymousStatementRead() {
		return anonymousStatementRead;
	}
	
	public void setAnonymousStatementRead(Boolean anonymousStatementRead) {
		this.anonymousStatementRead = anonymousStatementRead;
	}
	
	public Boolean getConfidentialStatementRead() {
		return confidentialStatementRead;
	}
	
	public void setConfidentialStatementRead(Boolean confidentialStatementRead) {
		this.confidentialStatementRead = confidentialStatementRead;
	}
}