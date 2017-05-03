package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.model.AbstractAuditableEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.conversion.annotations.Conversion;

@SuppressWarnings("serial")
@Entity
@Table(name = "ALERT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Conversion
public class Alert extends AbstractAuditableEntity<Long> implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ALERT_SEQ")
	@SequenceGenerator(name = "ALERT_SEQ", sequenceName = "ALERT_SEQ")
	private Long id;
	
	@Column(name = "OBJECT_ID")
	private Long objectId;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "ALERT")
	private String alert;
	
	@Column(name = "ALERT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date alertDate;
	
	@Column(name = "ACTION_DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date actionDueDate;
	
	public Alert() {
		
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
	
	public Long getObjectId() {
		return objectId;
	}
	
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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
}