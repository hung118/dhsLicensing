package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.TrackingRecordScreening;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

@SuppressWarnings("serial")
@Entity()
@Immutable
@Table(name = "TRS_SEARCH_VIEW")
public class TRSSearchView implements Serializable {

	@Id
	@Column(name = "ROW_ID")
	private Long rowId;
	
	@Column(name = "PERSON_ID")
	private Long personId;

	@Column(name = "TRS_ID")
	private Long trsId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRS_ID", insertable = false, updatable = false)
	private TrackingRecordScreening trs;

	@Column(name = "FACILITY_ID")
	private Long facilityId;

	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;

	@Column(name = "SSN_LAST_FOUR")
	private String ssnLastFour;

	@Column(name = "BIRTHDAY")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Column(name = "PERSON_NAME")
	private String personName;

	@Column(name = "PERSON_ALIASES")
	private String alias;

	@Column(name = "APPROVAL_MAILED_DATE")
	@Temporal(TemporalType.DATE)
	private Date approvalDate;

	@Column(name = "FACILITY_NAME")
	private String facilityName;

	@Column(name = "PERSON_IDENTIFIER")
	private String personIdentifier;

	@Transient
	private Boolean hasData;

	public TRSSearchView() {
		
	}
	
	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getTrsId() {
		return trsId;
	}

	public void setTrsId(Long trsId) {
		this.trsId = trsId;
	}

	public TrackingRecordScreening getTrs() {
		return trs;
	}

	public void setTrs(TrackingRecordScreening trs) {
		this.trs = trs;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
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

	public String getSsnLastFour() {
		return ssnLastFour;
	}

	public void setSsnLastFour(String ssnLastFour) {
		this.ssnLastFour = ssnLastFour;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getPersonIdentifier() {
		return personIdentifier;
	}
	
	public void setPersonIdentifier(String personIdentifier) {
		this.personIdentifier = personIdentifier;
	}
	
	public Boolean getHasData() {
		hasData = Boolean.FALSE;
		if (getTrs() != null) {
			hasData = (getTrs().getTrsDpsFbi() != null || 
						!getTrs().getRequestsList().isEmpty() || 
						!getTrs().getLettersList().isEmpty() || 
						!getTrs().getLtr15List().isEmpty() || 
						getTrs().getCbsComm() != null || 
						!getTrs().getConvictionList().isEmpty() ||
						!getTrs().getConvictionLettersList().isEmpty() ||
						getTrs().getMisComm() != null || 
						!getTrs().getCaseList().isEmpty() || 
						!trs.getOscarList().isEmpty() || 
						!getTrs().getActivityList().isEmpty()); 
		}
		return hasData;
	}

}
