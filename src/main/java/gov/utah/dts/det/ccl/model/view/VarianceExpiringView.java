package gov.utah.dts.det.ccl.model.view;

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
@Table(name = "ALERT_VARIANCE_EXPIRING_VIEW")
@Immutable
public class VarianceExpiringView extends BaseFacilityAlertView {

	@Id
	@Column(name = "VARIANCE_ID")
	private Long id;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name = "RULE_NUMBER")
	private String ruleNumber;
	
	@Column(name = "DIRECTOR_RESPONSE")
	private String directorResponse;
	
	public VarianceExpiringView() {
		
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRuleNumber() {
		return ruleNumber;
	}

	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public String getDirectorResponse() {
		return directorResponse;
	}

	public void setDirectorResponse(String directorResponse) {
		this.directorResponse = directorResponse;
	}
}