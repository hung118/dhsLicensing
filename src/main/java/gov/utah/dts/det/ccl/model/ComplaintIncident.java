package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractBaseEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT_INCIDENT")
public class ComplaintIncident extends AbstractBaseEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "COMPLAINT_INCIDENT_SEQ")
	@SequenceGenerator(name = "COMPLAINT_INCIDENT_SEQ", sequenceName = "COMPLAINT_INCIDENT_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPLAINT_ID", nullable = false, updatable = false)
	private Complaint complaint;
	
	@Column(name = "INCIDENT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name = "DATE_DESCRIPTION")
	private String dateDescription;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CAN_INVESTIGATE")
	@Type(type = "yes_no")
	private boolean investigable = false;
	
	public ComplaintIncident() {
		
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
	
	public Complaint getComplaint() {
		return complaint;
	}
	
	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}
	
	@ConversionErrorFieldValidator(message = "Date of incident is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDateDescription() {
		return dateDescription;
	}
	
	public void setDateDescription(String dateDescription) {
		this.dateDescription = dateDescription;
	}
	
	@RequiredStringValidator(message = "Description is required.")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isInvestigable() {
		return investigable;
	}
	
	public void setInvestigable(boolean investigable) {
		this.investigable = investigable;
	}
}