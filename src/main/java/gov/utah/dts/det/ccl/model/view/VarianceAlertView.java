package gov.utah.dts.det.ccl.model.view;

import gov.utah.dts.det.ccl.model.enums.VarianceOutcome;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

@SuppressWarnings("serial")
@Entity
@Table(name = "VARIANCE_ALERT_VIEW")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class VarianceAlertView implements Serializable {

	@Id
	@Column(name = "VARIANCE_ID")
	private Long id;
	
	@Column(name = "FACILITY_ID")
	private Long facilityId;
	
	@Column(name = "REQUEST_DATE")
	private Date requestDate;
	
	@Column(name = "RULE_ID")
	private Long ruleId;
	
	@Column(name = "LICENSOR_OUTCOME")
	@Type(type = "VarianceOutcome")
	private VarianceOutcome licensorOutcome;

	@Column(name = "SUPERVISOR_OUTCOME")
	@Type(type = "VarianceOutcome")
	private VarianceOutcome supervisorOutcome;

	@Column(name = "OUTCOME")
	@Type(type = "VarianceOutcome")
	private VarianceOutcome outcome;
	
	@Column(name = "FINALIZED")
	@Type(type = "yes_no")
	private Boolean finalized;

	@Column(name = "FACILITY_NAME")
	private String facilityName;
	
	@Column(name = "RULE_NUMBER")
	private String ruleNumber;
	
	@Column(name = "RULE_NAME")
	private String ruleName;

	public VarianceAlertView() {
		
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

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public VarianceOutcome getLicensorOutcome() {
		return licensorOutcome;
	}

	public void setLicensorOutcome(VarianceOutcome licensorOutcome) {
		this.licensorOutcome = licensorOutcome;
	}

	public VarianceOutcome getSupervisorOutcome() {
		return supervisorOutcome;
	}

	public void setSupervisorOutcome(VarianceOutcome supervisorOutcome) {
		this.supervisorOutcome = supervisorOutcome;
	}

	public VarianceOutcome getOutcome() {
		return outcome;
	}

	public void setOutcome(VarianceOutcome outcome) {
		this.outcome = outcome;
	}

	public boolean isFinalized() {
		return finalized;
	}

	public void setFinalized(Boolean finalized) {
		this.finalized = finalized;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getRuleNumber() {
		return ruleNumber;
	}

	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

}