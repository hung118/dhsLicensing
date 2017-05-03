/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;
import gov.utah.dts.det.ccl.security.SecurityUtil;
import gov.utah.dts.det.model.AbstractAuditableEntity;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Entity
@Table(name = "ACTIONLOG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActionLog extends AbstractAuditableEntity<Long> implements Serializable, Secured {
	
	//TODO: make future facility status changes uneditable and status changes only editable by admins - I was thinking of making an additional method on the Secured interface for customizations like this.
	
	private static final Logger logger = LoggerFactory.getLogger(ActionLog.class);
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ACTIONLOG_SEQ")
	@SequenceGenerator(name = "ACTIONLOG_SEQ", sequenceName = "ACTIONLOG_SEQ")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITYID")
	private Facility facility;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAKENBY")
	private Person takenBy;
	
	@Column(name = "ACTIONDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date actionDate;
	
	@ManyToOne
	@JoinColumn(name = "ACTIONTYPEID")
	private PickListValue actionType;
	
	@Column(name = "NOTE")
	private String note;
	
	@Transient
	private boolean editable = false;
	
	public ActionLog() {
		
	}
	
	@PrePersist
	@PreUpdate
	public void setActionTaker() {
		logger.debug("in updateActionTaker()");
		
		if (takenBy == null) {
			User user = SecurityUtil.getUser();
			if (user != null) {
				takenBy = user.getPerson();
			}
			if (takenBy == null) {
				//if there is no user then set as the system user
				takenBy = SecurityUtil.getSystemPerson();
			}
		}
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

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	public Person getTakenBy() {
		return takenBy;
	}
	
	public void setTakenBy(Person takenBy) {
		this.takenBy = takenBy;
	}

	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Action date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Action date is required.", shortCircuit = true)
		}
	)
	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	@RequiredFieldValidator(message = "Action is required.")
	public PickListValue getActionType() {
		return actionType;
	}

	public void setActionType(PickListValue actionType) {
		this.actionType = actionType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public Long getOwnerId() {
		if (getModifiedBy() == null) {
			return null;
		}
		return getModifiedBy().getId();
	}
	
	@Override
	public boolean isEditable() {
		return editable;
	}
	
	@Override
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public int getDaysUntilUneditable() {
		return 4;
	}

	@Override
	public Date getSecurityDate() {
		return getCreationDate();
	}
}