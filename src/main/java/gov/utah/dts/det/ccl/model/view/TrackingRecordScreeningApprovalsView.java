package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.Facility;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "SCREENING_APPROVALS_VIEW")
@SuppressWarnings("serial")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TrackingRecordScreeningApprovalsView implements Serializable {

	@Id
	@Column(name = "SCREENING_ID")
	private Long screeningId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCREENING_ID", insertable = false, updatable = false)
	private TrackingRecordScreening screening;

	@Column(name = "FACILITY_ID")
	private Long facilityId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FACILITY_ID", insertable = false, updatable = false)
	private Facility facility;

	@Column(name = "PERSON_ID")
	private Long personId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ID", insertable = false, updatable = false)
	private Person person;

	@Column(name = "FACILITY_NAME")
	private String facilityName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "BIRTHDATE")
	@Temporal(TemporalType.DATE)
	private Date birthdate;

	@Column(name = "APPROVAL_DATE")
	@Temporal(TemporalType.DATE)
	private Date approvalDate;

	@Column(name = "MONTHS_SINCE_APPROVAL")
	private Float monthsSinceApproval;

	public TrackingRecordScreeningApprovalsView() {
		
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Float getMonthsSinceApproval() {
		return monthsSinceApproval;
	}

	public void setMonthsSinceApproval(Float monthsSinceApproval) {
		this.monthsSinceApproval = monthsSinceApproval;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public TrackingRecordScreening getScreening() {
		return screening;
	}

	public void setScreening(TrackingRecordScreening screening) {
		this.screening = screening;
	}

	public Long getScreeningId() {
		return screeningId;
	}

	public void setScreeningId(Long screeningId) {
		this.screeningId = screeningId;
	}

}