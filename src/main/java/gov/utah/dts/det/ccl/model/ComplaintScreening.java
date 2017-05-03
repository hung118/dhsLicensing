package gov.utah.dts.det.ccl.model;

import gov.utah.dts.det.admin.model.PickListValue;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;

@SuppressWarnings("serial")
@Entity
@Table(name = "COMPLAINT_SCREENING")
public class ComplaintScreening implements Serializable {

	@Id
	@Column(name = "COMPLAINT_ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "SCREENING_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date screeningDate;
	
	@Column(name = "PROCEED_WITH_INVESTIGATION")
	@Type(type = "yes_no")
	private boolean proceedWithInvestigation;

	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "PROCEED_REASON_ID")
	@Fetch(FetchMode.JOIN)
	private PickListValue proceedReason;

	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "RESPONSE_TIME_ID")
	@Fetch(FetchMode.JOIN)
	private PickListValue responseTime;

	@Column(name = "RESPONSE_TIME_OTHER")
	private String responseTimeOther;

	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "INVESTIGATION_TYPE_ID")
	@Fetch(FetchMode.JOIN)
	private PickListValue investigationType;

	@ManyToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "CONCLUSION_TYPE_ID")
	@Fetch(FetchMode.JOIN)
	private PickListValue conclusionType;
	
	@Column(name = "INVESTIGATION_TYPE_OTHER")
	private String investigationTypeOther;

	@Column(name = "OTHER_INSTRUCTIONS")
	private String otherInstructions;
	
	@ManyToMany
	@JoinTable(name = "COMPLAINT_SPECIALIST",
		joinColumns = @JoinColumn(name = "COMPLAINT_ID", referencedColumnName = "COMPLAINT_ID"),
		inverseJoinColumns = @JoinColumn(name = "SPECIALIST_ID", referencedColumnName = "ID")
	)
	private Set<Person> assignedSpecialists = new HashSet<Person>();

	public ComplaintScreening() {

	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@ConversionErrorFieldValidator(message = "Date screened with regional manager is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
	public Date getScreeningDate() {
		return screeningDate;
	}

	public void setScreeningDate(Date screeningDate) {
		this.screeningDate = screeningDate;
	}
	
	public boolean isProceedWithInvestigation() {
		return proceedWithInvestigation;
	}
	
	public void setProceedWithInvestigation(boolean proceedWithInvestigation) {
		this.proceedWithInvestigation = proceedWithInvestigation;
	}
	
	public PickListValue getProceedReason() {
		return proceedReason;
	}
	
	public void setProceedReason(PickListValue proceedReason) {
		this.proceedReason = proceedReason;
	}

	public PickListValue getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(PickListValue responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseTimeOther() {
		return responseTimeOther;
	}

	public void setResponseTimeOther(String responseTimeOther) {
		this.responseTimeOther = responseTimeOther;
	}

	public PickListValue getInvestigationType() {
		return investigationType;
	}

	public void setInvestigationType(PickListValue investigationType) {
		this.investigationType = investigationType;
	}

	public PickListValue getConclusionType() {
		return conclusionType;
	}

	public void setConclusionType(PickListValue conclusionType) {
		this.conclusionType = conclusionType;
	}

	public String getInvestigationTypeOther() {
		return investigationTypeOther;
	}

	public void setInvestigationTypeOther(String investigationTypeOther) {
		this.investigationTypeOther = investigationTypeOther;
	}

	public String getOtherInstructions() {
		return otherInstructions;
	}

	public void setOtherInstructions(String otherInstructions) {
		this.otherInstructions = otherInstructions;
	}
	
	public Set<Person> getAssignedSpecialists() {
		return assignedSpecialists;
	}
	
	public void setAssignedSpecialists(Set<Person> assignedSpecialists) {
		this.assignedSpecialists = assignedSpecialists;
	}
}