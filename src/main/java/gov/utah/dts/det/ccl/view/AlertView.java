package gov.utah.dts.det.ccl.view;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT_VIEW")
@Immutable
public class AlertView implements Serializable {

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "RECIPIENT_ID")
	private Long recipientId;
	
	@Column(name = "ALERT")
	private String alert;
	
	@Column(name = "ALERT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date alertDate;
	
	@Column(name = "ACTION_DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date actionDueDate;
	
	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "SENT_BY_ID")
	private Long sentById;
	
	@Column(name = "SENT_BY_FIRST_NAME")
	private String sentByFirstName;
	
	@Column(name = "SENT_BY_LAST_NAME")
	private String sentByLastName;
		
	public AlertView() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}

	public Date getActionDueDate() {
		return actionDueDate;
	}

	public void setActionDueDate(Date actionDueDate) {
		this.actionDueDate = actionDueDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getSentById() {
		return sentById;
	}

	public void setSentById(Long sentById) {
		this.sentById = sentById;
	}

	public String getSentByFirstName() {
		return sentByFirstName;
	}

	public void setSentByFirstName(String sentByFirstName) {
		this.sentByFirstName = sentByFirstName;
	}

	public String getSentByLastName() {
		return sentByLastName;
	}

	public void setSentByLastName(String sentByLastName) {
		this.sentByLastName = sentByLastName;
	}
}