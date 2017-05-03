package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Complaint;

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

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT_VIEW")
@Immutable
public class ComplaintView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "DATE_RECEIVED")
	@Temporal(TemporalType.DATE)
	private Date dateReceived;
	
	@Column(name = "STATE")
	@Enumerated(EnumType.STRING)
	private Complaint.State state;
	
	@Column(name = "CONCLUSION")
	@Enumerated(EnumType.STRING)
	private String conclusion;
	
	@Column(name = "ANONYMOUS")
	@Type(type = "yes_no")
	private Boolean anonymous;
	
	@Column(name = "SUBSTANTIATED")
	@Type(type = "yes_no")
	private Boolean substantiated;
	
	public ComplaintView() {
		
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

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}
	
	public Complaint.State getState() {
		return state;
	}
	
	public void setState(Complaint.State state) {
		this.state = state;
	}
	
	public boolean getAnonymous() {
		return anonymous;
	}
	
	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public Boolean getSubstantiated() {
		return substantiated;
	}

	public void setSubstantiated(Boolean substantiated) {
		this.substantiated = substantiated;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	
}