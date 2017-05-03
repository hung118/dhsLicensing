package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Complaint;
import gov.utah.dts.det.ccl.model.Person;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT_COMPL_IN_PROG_VIEW")
@Immutable
public class ComplaintsInProgressView implements Serializable {

	@Id
	@Column(name = "COMPLAINT_ID")
	private Long complaintId;
	
	@ManyToOne
	@JoinColumn(name = "FACILITY_ID")
	private BasicFacilityInformation facility;
	
	@Column(name = "LICENSING_SPECIALIST_ID")
	private Long licensingSpecialistId;
	
	@Column(name = "DATE_RECEIVED")
	@Temporal(TemporalType.DATE)
	private Date dateReceived;
	
	@Column(name = "STATE")
	@Enumerated(EnumType.STRING)
	private Complaint.State state;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "LAST_STATE_CHANGE_TYPE")
	@Enumerated(EnumType.STRING)
	private Complaint.StateChange lastStateChangeType;
	
	@Column(name = "LAST_STATE_CHANGE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastStateChangeDate;
	
	@ManyToOne
	@JoinColumn(name = "LAST_STATE_CHANGER")
	private Person lastStateChanger;
	
	@Column(name = "LAST_STATE_CHANGE_NOTE")
	private String lastStateChangeNote;
	
	public ComplaintsInProgressView() {
		
	}

	public Long getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}

	public BasicFacilityInformation getFacility() {
		return facility;
	}

	public void setFacility(BasicFacilityInformation facility) {
		this.facility = facility;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Complaint.StateChange getLastStateChangeType() {
		return lastStateChangeType;
	}

	public void setLastStateChangeType(Complaint.StateChange lastStateChangeType) {
		this.lastStateChangeType = lastStateChangeType;
	}

	public Date getLastStateChangeDate() {
		return lastStateChangeDate;
	}

	public void setLastStateChangeDate(Date lastStateChangeDate) {
		this.lastStateChangeDate = lastStateChangeDate;
	}

	public Person getLastStateChanger() {
		return lastStateChanger;
	}

	public void setLastStateChanger(Person lastStateChanger) {
		this.lastStateChanger = lastStateChanger;
	}
	
	public String getLastStateChangeNote() {
		return lastStateChangeNote;
	}
	
	public void setLastStateChangeNote(String lastStateChangeNote) {
		this.lastStateChangeNote = lastStateChangeNote;
	}

	public Long getLicensingSpecialistId() {
		return licensingSpecialistId;
	}

	public void setLicensingSpecialistId(Long licensingSpecialistId) {
		this.licensingSpecialistId = licensingSpecialistId;
	}

}