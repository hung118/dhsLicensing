package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@SuppressWarnings("serial")
@Entity
@Table(name = "INCIDENT")
@Conversion
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Incident extends StateObject implements Serializable, ModifiableStateObject<Incident.State, Incident.StateChange> {
	
	public enum State {ENTRY, APPROVAL, FINALIZED};
	public enum StateChange {CREATED, ENTRY_COMPLETED, ENTRY_REJECTED, FINALIZED, UNFINALIZED};
	
	@ManyToOne
	@JoinColumn(name = "FACILITY_ID")
	private Facility facility;
	
	@Column(name = "INCIDENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CHILD_ID")
	private Person child;
	
	@ManyToOne
	@JoinColumn(name = "CHILD_AGE_ID")
	private PickListValue childAge;
	
	@ManyToOne
	@JoinColumn(name = "INJURY_TYPE_ID")
	private PickListValue injuryType;
	
	@Column(name = "INCIDENT_TEXT")
	private String incidentText;

	//@Column(name = "CHILD_AGE")
	@Transient
	private Integer age;

	@Column(name = "INJURY_TEXT")
	private String injuryText;
	
	@Column(name = "TREATMENT_TEXT")
	private String treatmentText;
	
	@Column(name = "DEATH")
	@Type(type = "yes_no")
	private Boolean death;
	
	@Column(name = "RECEIVED_TREATMENT")
	@Type(type = "yes_no")
	private Boolean receivedTreatment;	
	
	@Column(name = "REPORTED_OVER_PHONE")
	@Type(type = "yes_no")
	private Boolean reportedOverPhone;
	
	@Column(name = "SENT_WRITTEN_REPORT")
	@Type(type = "yes_no")
	private Boolean sentWrittenReport;
	
	@ManyToMany
	@JoinTable(name = "INCIDENT_BODY_PART_INJURED",
		joinColumns = @JoinColumn(name = "INCIDENT_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "BODY_PART_ID", referencedColumnName = "ID")
	)
	private List<PickListValue> bodyPartsInjured = new ArrayList<PickListValue>();
	
	@ManyToMany
	@JoinTable(name = "INCIDENT_INJURY_LOCATION",
		joinColumns = @JoinColumn(name = "INCIDENT_ID", referencedColumnName = "ID"),
		inverseJoinColumns = @JoinColumn(name = "INJURY_LOCATION_ID", referencedColumnName = "ID")
	)
	private List<PickListValue> locationsOfInjury = new ArrayList<PickListValue>();
	
	public Incident() {
		
	}
	
	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(message = "Incident date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(message = "Incident date is required.", shortCircuit = true)
		}
	)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Person getChild() {
		return child;
	}

	public void setChild(Person child) {
		this.child = child;
	}

	public PickListValue getChildAge() {
		return childAge;
	}

	public void setChildAge(PickListValue childAge) {
		this.childAge = childAge;
	}

	public PickListValue getInjuryType() {
		return injuryType;
	}

	public void setInjuryType(PickListValue injuryType) {
		this.injuryType = injuryType;
	}

	public String getIncidentText() {
		return incidentText;
	}

	public void setIncidentText(String incidentText) {
		this.incidentText = incidentText;
	}

	public String getInjuryText() {
		return injuryText;
	}

	public void setInjuryText(String injuryText) {
		this.injuryText = injuryText;
	}

	public String getTreatmentText() {
		return treatmentText;
	}

	public void setTreatmentText(String treatmentText) {
		this.treatmentText = treatmentText;
	}
	
	public Boolean getDeath() {
		return death;
	}
	
	public void setDeath(Boolean death) {
		this.death = death;
	}
	
	public Boolean getReceivedTreatment() {
		return receivedTreatment;
	}

	public void setReceivedTreatment(Boolean receivedTreatment) {
		this.receivedTreatment = receivedTreatment;
	}

	public Boolean getReportedOverPhone() {
		return reportedOverPhone;
	}
	
	public void setReportedOverPhone(Boolean reportedOverPhone) {
		this.reportedOverPhone = reportedOverPhone;
	}
	
	public Boolean getSentWrittenReport() {
		return sentWrittenReport;
	}
	
	public void setSentWrittenReport(Boolean sentWrittenReport) {
		this.sentWrittenReport = sentWrittenReport;
	}

	public List<PickListValue> getBodyPartsInjured() {
		return bodyPartsInjured;
	}

	public void setBodyPartsInjured(List<PickListValue> bodyPartsInjured) {
		this.bodyPartsInjured = bodyPartsInjured;
	}

	public List<PickListValue> getLocationsOfInjury() {
		return locationsOfInjury;
	}

	public void setLocationsOfInjury(List<PickListValue> locationsOfInjury) {
		this.locationsOfInjury = locationsOfInjury;
	}
	
	@Override
	public State getState() {
		if (super.getInternalState() == null) {
			return null;
		}
		return Incident.State.valueOf(super.getInternalState());
	}
	
	@Override
	public void setState(State state, StateChange changeType, String note) {
		super.setState(state.name(), changeType.name(), note);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}